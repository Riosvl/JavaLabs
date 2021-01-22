package com.kanils.client;

import com.kanils.bank.Bank;
import com.kanils.database.Connector;
import com.kanils.commands.*;
import com.kanils.main.Main;
import com.kanils.user.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    static User user = new User("vladm@gmail.com","Vlad Hlushchenko",100000,"qwerty",1);
    static ArrayList<Bank> banks = new ArrayList<Bank>();
    // static Connector connector = new Connector();
    static Terminal terminal;

    public static void start() {
        Main.log.info("Програма стартувала!");
        while (userStart() == true) {
            try {
                //connector.setBankInfo(banks);
                terminal = new Terminal(user, banks);
            } catch (Exception ex) {
                Main.log.fatal(ex);
                //  System.out.println("Невдалось встановити інформацію про банки-" + ex.getMessage());
                continue;
            }
            do {
                menuList();
            } while (switchMenu());
        }

        // connector.closeConnection();
        Main.log.info("Програма закрилась");
    }

    private static boolean userStart() {

        System.out.println("Залогінитись - 1");
        System.out.println("Зареєструватись - 2");
        System.out.println("Вийти - 3");
        System.out.print("Вибирайте: ");

        int switch_on = 0;
        Scanner scanner = new Scanner(System.in);
        Main.log.info("Стартове меню");
        try {
            switch_on = scanner.nextInt();
        } catch (Exception ex) {
            Main.log.error(ex);
            System.out.println("Невірне значення вводу");
        }

        for (int i = 1; i <= 3; i++) {
            switch (switch_on) {
                case 1: {
                    if (LoginUser.doLogin(user, null,user))
                        return true;
                    else {
                        System.out.println("У вас  " + (3 - i) + " спроби!");
                        continue;
                    }
                }
                case 2: {
                    User user1= RegisterUser.doRegistration(user, null);

                    switch_on = 1;
                    if (LoginUser.doLogin(user1, null,user1))
                        return true;
                    else {
                        System.out.println("У вас  " + (3 - i) + " спроби!");
                        continue;
                    }

                }
                case 3: {
                    break;
                }
                default: {
                    System.out.println("Нема такого вибору!");
                    break;
                }
            }
        }
        return false;
    }

    private static void menuList() {
        System.out.printf("Ваш баланс: %.2f $ \n", user.getBalance());
        System.out.println("Подивитись пропозиції банків - 1");
        System.out.println("Подивитись мої депозити - 2");
        System.out.println("Дострокове зняття депозиту - 3");
        System.out.println("Поповнення депозиту - 4");
        System.out.println("Поповнення балансу- 5");
        System.out.println("Пошук інформації за параметром - 6 ");
        System.out.println("Повернутись назад - 7 ");
    }

    private static boolean switchMenu() {
        System.out.print("Вибирайте: ");
        Scanner scanner = new Scanner(System.in);

        int switch_on;
        try {
            switch_on = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Невірне значення");
            return true;
        }
        Main.log.info("Виконання команд");
        Command command;
        switch (switch_on) {

            case 1: {
                command = new ShowPropositionsCommand(terminal);
                doLogic(command);
                break;
            }
            case 2: {
                command = new ShowDepositsCommand(terminal);
                doLogic(command);
                break;
            }
            case 3: {
                command = new WithdrawDepositCommand(terminal);
                doLogic(command);
                break;
            }
            case 4: {
                command = new DepositReplenishmentCommand(terminal);
                doLogic(command);
                break;
            }
            case 5: {
                command = new BalanceReplenishmentCommand(terminal);
                doLogic(command);
                break;
            }
            case 6: {
                command = new FindInfoCommand(terminal);
                doLogic(command);
                break;
            }

            case 7: {
                //show_history_commands(commandHistory);
                return false;
            }
            default: {
                System.out.println("Такого вибору нема");
                break;
            }
        }

        return true;
    }

    private static void doLogic(Command command) {
        command.execute();
    }


}
