package com.kanils.client;

import com.kanils.bank.Bank;
import com.kanils.bank.Proposition;
import com.kanils.database.Connector;
import com.kanils.main.Main;
import com.kanils.user.User;

import java.sql.Date;
import java.util.*;

public class Terminal {
    User user;
    ArrayList<Bank> banks;
    Scanner scanner = new Scanner(System.in);
    // Connector connector;
    ArrayList<Proposition> userPropositions;
    ArrayList<Proposition> bankPropositions;

    public Terminal(User user, ArrayList<Bank> banks) {
        this.user = user;
        this.banks = banks;
        //this.connector = connector;

    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    private void printDeposits(ArrayList<Proposition> deposits, ArrayList<Bank> banks) {
        System.out.println("№\tБанк\t\tСума\t\tВалюта\t\tПроцент\t\tДата положення\t\tТеперішня дата\t\tДата кінця депозиту");

        for (Proposition deposit : deposits) {
            String bankname = depositBank(deposit, banks);
            if (bankname != null)
                printDeposit(deposit, bankname);
        }
    }

    private String depositBank(Proposition proposition, ArrayList<Bank> banks) {
        for (Bank bank : banks) {
            for (Proposition prop : bank.getPropositions()) {
                if (prop.getIdDepos() == proposition.getIdDepos()) {
                    return bank.getName();
                }
            }
        }
        return null;
    }

    private void printDeposit(Proposition proposition, String name) {
        System.out.printf("%-5d %-10s  %-10s  %-10s  %-10s  %-20s %-20s %-20s\n", proposition.getIdDepos(), name,
                proposition.getDepossum(),
                proposition.getCurrency(),
                proposition.getPercenyear(),
                proposition.getPuttimeS(),
                proposition.getCurrenttimeS(),
                proposition.getPlannedtimeS());
    }

    private void printUserDeposists() {
        System.out.println("----------------------");
        System.out.println("Показ ваших депозитів");
        printDeposits(userPropositions, user.getDeposits());
        System.out.println("----------------------");

    }

    public void showDeposits() {
        boolean key = true;
        do {
            if (userPropositions == null ) {
                // userPropositions = combineDeposits(user.getDeposits());
                System.out.println("Нажаль ви не маєте депозитів");
                break;
            }

            printUserDeposists();
            System.out.println("Відсортувати за певним параметром - 1");
            System.out.println("Повернутись назад - 2");
            System.out.print("Вибирайте: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    userPropositions = sortDeposits(user.getDeposits());
                    break;
                }
                case 2: {
                    key = false;
                    break;
                }
                default: {
                    System.out.println("Невідоме значення");
                    break;
                }
            }
        } while (key);
        System.out.println("----------------------");
    }

    public void withdrawDeposit() {
        System.out.println("----------------------");
        System.out.println("Зняття депозиту достроково");
        if (userPropositions==null)
        {
            System.out.println("Нажаль ви не маєете депозитів.");
            return;
        }
        int idbank = chooseBank(user.getDeposits());
        int iddeposit = chooseDeposit(idbank, user.getDeposits());

        double newbalance = computeSimplepercen(user.getDeposits().get(idbank).getPropositions().get(iddeposit)) + user.getBalance();
        user.setBalance(newbalance);
        try {
            //  connector.closeDeposit(user.getDeposits().get(idbank).getPropositions().get(iddeposit));
            //  connector.updatebalance(user);
        } catch (Exception ex) {
            Main.log.fatal("Не вдалось закрити депозит - " + ex);
            System.out.println("Не вдалось закрити депозит  - " + ex.getMessage());
        }
        user.getDeposits().get(idbank).getPropositions().remove(iddeposit);
        System.out.println("----------------------");
    }

    private double computeSimplepercen(Proposition proposition) {
        int days = computeDays(proposition);
        double res = proposition.getDepossum() + ((proposition.getDepossum() * days * proposition.getPercenyear()) / (100 * 365));
        return res;
    }

    private int computeDays(Proposition proposition) {
        Calendar startcal = Calendar.getInstance();
        Calendar endcal = Calendar.getInstance();
        startcal.setTime(proposition.getPuttime());
        endcal.setTime(proposition.getCurrenttime());
        return (endcal.get(Calendar.YEAR) - startcal.get(Calendar.YEAR)) * 365 + (endcal.get(Calendar.MONTH) - startcal.get(Calendar.MONTH)) * 30 + endcal.get(Calendar.DATE) - startcal.get(Calendar.DATE);
    }

    private void findInfoMenu() {
        System.out.println("Пошук інформації");
        System.out.println("У пропозиціях банків - 1");
        System.out.println("У ваших депозитах - 2");
        System.out.println("Повернутись назад - 3");
    }

    public void findInfo() {
        boolean key = true;

        do {
            System.out.println("----------------------");
            findInfoMenu();
            int switch_on = scanner.nextInt();
            int parametr;
            switch (switch_on) {
                case 1: {
                    parametr = chooseParameter();
                    findBankInfo(banks, parametr);
                    break;
                }
                case 2: {
                    parametr = chooseParameter();
                    findBankInfo(user.getDeposits(), parametr);
                    break;
                }
                case 3: {
                    key = false;
                    break;
                }
                default: {
                    break;
                }
            }

        } while (key);
        System.out.println("----------------------");

    }

    private int chooseParameter() {

        int choice_param = 0;
        System.out.println("Доступні параметри");
        System.out.println("Ім`я банку - 1");
        System.out.println("Сумма депозиту - 2");
        System.out.println("Процент - 3");
        System.out.println("Виберіть параметр:");
        try {
            choice_param = scanner.nextInt();
        } catch (Exception ex) {
            Main.log.error(ex);
        }

        return choice_param;
    }

    public void findBankInfo(ArrayList<Bank> banks, int parameter) {

        switch (parameter) {
            case 1: {
                System.out.println("Який банк шукати?");
                System.out.print("Введіть ім`я: ");
                String enter = scanner.nextLine();
                String value = scanner.nextLine();
                for (Bank bank : banks)
                    for (int j = 0, num = 1; j < bank.getPropositions().size(); j++, num++) {
                        if (bank.getName().equals(value)) {
                            printDeposit(bank.getPropositions().get(j), bank.getName());
                        }
                    }

                break;
            }
            case 2: {
                System.out.println("Яку суму депозиту шукати?");
                System.out.print("Введіть значення: ");
                double value = scanner.nextDouble();

                for (Bank bank : banks)
                    for (int j = 0, num = 1; j < bank.getPropositions().size(); j++, num++) {
                        if (bank.getPropositions().get(j).getDepossum() == value)
                            printDeposit(bank.getPropositions().get(j), bank.getName());
                    }
                break;
            }
            case 3: {
                System.out.println("Який процент шукати?");
                System.out.print("Введіть значення: ");
                double value = scanner.nextDouble();

                for (Bank bank : banks)
                    for (int j = 0, num = 1; j < bank.getPropositions().size(); j++, num++) {
                        if (bank.getPropositions().get(j).getPercenyear() == value)
                            printDeposit(bank.getPropositions().get(j), bank.getName());
                    }
                break;
            }
            default: {

                break;
            }
        }
    }

    public void showPropositions() {
        boolean key = true;
        do {
            if (bankPropositions == null)
                bankPropositions = combineDeposits(banks);

            printBankPropositions();
            System.out.println("Зробити вклад - 1");
            System.out.println("Відсортувати за певним параметром - 2");
            System.out.println("Повернутись назад - 3");
            System.out.print("Вибирайте: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    int idbank = chooseBank(banks);
                    if (idbank == -1) {
                        System.out.println("Нема такого банку");
                        break;
                    }
                    int iddepos = chooseDeposit(idbank, banks);
                    if (iddepos == -1) {
                        System.out.println("Нема такої пропозиції");
                        break;
                    }
                    makeDeposit(idbank, iddepos);
                    userPropositions = combineDeposits(user.getDeposits());
                    break;
                }
                case 2: {
                    bankPropositions = sortDeposits(banks);
                    break;
                }
                case 3: {
                    key = false;
                    break;
                }
                default: {
                    System.out.println("Невідоме значення");
                    break;
                }
            }
        } while (key);

        System.out.println("----------------------");
    }

    private void printBankPropositions() {
        System.out.println("----------------------");
        System.out.println("Показ пропозицій");
        printDeposits(bankPropositions, banks);
        System.out.println("----------------------");
    }

    private void makeDeposit(int idbank, int iddeposit) {
        Proposition deposit = banks.get(idbank).getPropositions().get(iddeposit);
        double startsumdep = deposit.getDepossum();

        if (startsumdep == 0) {
            System.out.print("Введіть кошти на депозит: ");

            float sumdepos = scanner.nextFloat();
            if (sumdepos > user.getBalance() || sumdepos <= 0) {
                System.out.println("Не вистача коштів або ви вели від`ємну суму");
                return;
            }

            double balance = user.getBalance();
            user.setBalance(balance - sumdepos);
            deposit.setDepossum(sumdepos);

        } else {
            if (startsumdep > user.getBalance()) {
                System.out.println("Не вистача коштів");
                return;
            }

            double balance = user.getBalance();
            user.setBalance(balance - startsumdep);

        }

        Date sqlDate = new Date(Calendar.getInstance().getTime().getTime());
        deposit.setPuttime(sqlDate);
        deposit.setCurrenttime(sqlDate);

        int nesbank = findBank(idbank);
        user.getDeposits().get(nesbank).addPropositions(deposit);
        banks.get(idbank).getPropositions().remove(iddeposit);
        try {
            //connector.changeProposition(user, deposit, startsumdep);
            //connector.updatebalance(user);
        } catch (Exception ex) {
            Main.log.fatal("Не вдалось зробити депозит");
            System.out.println("Помилка з зміною пропозиції - " + ex.getMessage());
        }
    }

    private int findBank(int idbank) {
        for (int i = 0; i < user.getDeposits().size(); i++) {
            if (user.getDeposits().get(i).getName().equals(banks.get(idbank).getName())) {
                return i;
            }
        }
        user.addBank(banks.get(idbank));
        return user.getDeposits().size() - 1;
    }

    public void balanceReplenishment() {
        System.out.println("----------------------");
        System.out.print("Поповнити баланс на : ");
        int getmoney = scanner.nextInt();
        getmoney += user.getBalance();
        user.setBalance(getmoney);
        try {
            // connector.updatebalance(user);
        } catch (Exception ex) {
            Main.log.fatal("Поповнити баланс не вдалось -");
            System.out.println("Поповнити баланс не вдалось -" + ex.getMessage());
        }
        System.out.println("----------------------");
    }

    private int chooseBank(ArrayList<Bank> banks) {
        System.out.print("Напишить ім`я банку:");
        String enter = scanner.nextLine();
        String name = scanner.nextLine();

        for (int i = 0; i < banks.size(); i++) {
            if (banks.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    private int chooseDeposit(int idbank, ArrayList<Bank> banks) {
        System.out.print("Виберіть пропозицію номер: ");
        int number = scanner.nextInt();

        for (int i = 0; i < banks.get(idbank).getPropositions().size(); i++)
            if (number == banks.get(idbank).getPropositions().get(i).getIdDepos())
                return i;

        return -1;
    }

    public void depositReplenishment() {
        System.out.println("----------------------");
        System.out.println("Поповнити депозит");
        int idbank = chooseBank(user.getDeposits());
        int iddeposit = chooseDeposit(idbank, user.getDeposits());
        if (makeDepositRelpenishment(idbank, iddeposit))
            System.out.println("Поповнення пройшло успішно");
        System.out.println("----------------------");
    }

    private boolean makeDepositRelpenishment(int idbank, int iddeposit) {
        System.out.print("Поповнити на: ");
        float getmoney = scanner.nextFloat();
        if (getmoney > user.getBalance() || getmoney <= 0) {
            System.out.println("Ви не можете поповнити депозит на 0, на від`ємне число або на суму більшу за баланс!");
            return false;
        } else {
            double newdepossum = user.getDeposits().get(idbank).getPropositions().get(iddeposit).getDepossum() + getmoney;
            double newbalance = user.getBalance() - getmoney;
            user.setBalance(newbalance);
            user.getDeposits().get(idbank).getPropositions().get(iddeposit).setDepossum(newdepossum);
            try {
                //   connector.updateDepossum(user.getDeposits().get(idbank).getPropositions().get(iddeposit), user);
                //connector.updatebalance(user);
            } catch (Exception ex) {
                Main.log.fatal("Не вдалось оновити депозит");
                System.out.println("Не вдалось обновити суму - " + ex.getMessage());
            }
        }
        return true;
    }

    private ArrayList<Proposition> combineDeposits(ArrayList<Bank> banks) {
        ArrayList<Proposition> newpropositions = new ArrayList<>();

        for (Bank bank : banks) {
            for (int j = 0; j < bank.getPropositions().size(); j++) {
                newpropositions.add(bank.getPropositions().get(j));
            }
        }

        return newpropositions;
    }

    public ArrayList<Proposition> sortDeposits(ArrayList<Bank> banks) {
        System.out.println("----------------------");
        System.out.println("Відсотрування депозитів за зростанням ");
        int switch_on = chooseParameter();

        switch (switch_on) {
            case 1: {
                banks.sort(Bank.BankNameComparator);
                return combineDeposits(banks);
            }
            case 2: {
                ArrayList<Proposition> combinedpropositions = combineDeposits(banks);
                Collections.sort(combinedpropositions, Proposition.PropositionDepossumComparator);
                return combinedpropositions;

            }
            case 3: {
                ArrayList<Proposition> combinedpropositions = combineDeposits(banks);
                Collections.sort(combinedpropositions, Proposition.PropopositionPercentComparator);
                return combinedpropositions;
            }
        }
        return null;
    }
}