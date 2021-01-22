package com.kanils.database;

import com.kanils.bank.Bank;
import com.kanils.bank.Proposition;
import com.kanils.main.Main;
import com.kanils.user.User;

import java.sql.*;
import java.util.ArrayList;

public class Connector {
    String url;
    Connection connection;
    ResultSet rs;
    PreparedStatement preparedStatement;

    public Connector() {
        try {
            Main.log.info("Підключення до бази даних");
            url = "jdbc:sqlserver://(localdb)\\MSSQLLocalDB:1433;databasename=Terminal;user=vladm;password=qwerty;";
           // DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           // connection = DriverManager.getConnection(url, "vladm", "qwerty");
            connection = DriverManager.getConnection(url);
        } catch (Exception ex) {
            Main.log.fatal("Не вдалось підключитись до бази даних");
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnection()  {
        try {
            connection.close();
        }catch (Exception ex)
        {
            Main.log.error("Не вдалось закрити зв`язок");
        }
    }

    public boolean checkUser(String email, String password, User user) throws SQLException {
        String sql = "Select * FROM Users WHERE Email='" + email + "' AND Password='" + password + "'";
        resultsetSqlStatement(sql);

        if (checkIdUser()) {
            setUserInfo(user, rs.getInt("IdUser"));
            return true;
        }

        closesetSqlStatement();
        return false;
    }


    public void updateDepossum(Proposition proposition, User user) throws SQLException {
        String sql = "Update Deposits set Depossum=" + proposition.getDepossum() + " where IdDeposit=" + proposition.getIdDepos();
        executeupdateSqlStatement(sql);
    }

    public void setUserInfo(User user, int id) throws SQLException {//setting all users information
        ArrayList<Bank> banks = new ArrayList<>();
        setUserPersonalInfo(user, id);

        String sql = "SELECT * FROM  Deposits INNER JOIN Banks B on Deposits.IdBank = B.IdBank where IdUser=" + id;
        resultsetSqlStatement(sql);

        setBankPropositions(banks);

        user.setDeposits(banks);
        closesetSqlStatement();
    }

    public void closeDeposit(Proposition proposition) throws SQLException {
        String sql = "UPDATE Deposits set IdUser=NULL, Depossum=NULL ,Puttime=NULL ,Currenttime=NULL ,Plannedtime=NULL where IdDeposit=" + proposition.getIdDepos();
        executeupdateSqlStatement(sql);
        closeSqlStatement();
    }

    public void updatebalance(User user) throws SQLException {
        String sql = "UPDATE Users set Balance=" + user.getBalance() + " where IdUser=" + user.getIdUser();
        try {
            executeupdateSqlStatement(sql);
        } catch (Exception ex) {
            Main.log.fatal("Не вдалось оновити баланс");
        } finally {
            closeSqlStatement();
        }
    }

    public void changeProposition(User user, Proposition proposition, double startsumdep) throws SQLException {
        String sql;
        if (startsumdep == 0) {
            sql = "UPDATE Deposits set IdUser=" + user.getIdUser() + ", Depossum=" +
                    proposition.getDepossum() + ",  Puttime='" + proposition.getPuttime() + "'" +
                    ",  Currenttime='" + proposition.getCurrenttime() + "' where IdDeposit=" + proposition.getIdDepos();
        } else {
            sql = "UPDATE Deposits set IdUser=" + user.getIdUser() + ",  Puttime='" + proposition.getPuttime() + "'" +
                    ",  Currenttime='" + proposition.getCurrenttime() + "' where IdDeposit=" + proposition.getIdDepos();
        }
        executeupdateSqlStatement(sql);
        closeSqlStatement();
    }

    public void addNewUser(String email, String password, String name) throws SQLException {//inserting new user in database
        String sqlcheck = "SELECT IdUser FROM  Users Where Email='" + email + "'";
        resultsetSqlStatement(sqlcheck);

        if (checkIdUser()) {
            System.out.println("Даний Email вже зареєстрований іншим користувачем!");
            return;
        }

        String sql = "Insert into Users VALUES( '" + email + "', '" + password + "', '" + name + "', '" + 0 + "')";
        executeupdateSqlStatement(sql);
        closeSqlStatement();
    }

    public void setBankInfo(ArrayList<Bank> banks) throws SQLException {
        String sql = "SELECT * FROM  Deposits INNER JOIN Banks B on " +
                "Deposits.IdBank = B.IdBank where IdUser is NULL and Plannedtime is not NULL";
        resultsetSqlStatement(sql);
        setBankPropositions(banks);
        closesetSqlStatement();
    }

    private void resultsetSqlStatement(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
    }

    private void executeupdateSqlStatement(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void closeSqlStatement() throws SQLException {
        preparedStatement.close();
    }

    private void closesetSqlStatement() throws SQLException {
        rs.close();
        closeSqlStatement();
    }

    private Bank addBankProposition(Bank bank, Proposition proposition) throws SQLException {
        bank.setName(rs.getString("Name"));
        bank.addPropositions(proposition);
        return bank;
    }

    private Proposition setPropositionInfo(Proposition proposition) throws SQLException {
        proposition.setIdDepos(rs.getInt("IdDeposit"));
        proposition.setDepossum(rs.getFloat("Depossum"));
        proposition.setPercenyear(rs.getFloat("Percentyear"));
        proposition.setPlannedtime(rs.getDate("Plannedtime"));
        proposition.setCurrenttime(rs.getDate("Currenttime"));
        proposition.setPuttime(rs.getDate("Puttime"));
        proposition.setCurrency(rs.getString("Currency"));
        return proposition;
    }

    private int isBankHere(ArrayList<Bank> banks) throws SQLException {
        for (int i = 0; i < banks.size(); i++) {
            if (banks.get(i).getName().equals(rs.getString("Name")))
                return i;
        }
        return -1;
    }

    private boolean checkIdUser() throws SQLException {
        while (rs.next()) {
            if (rs.getInt("IdUser") != 0) {
                return true;
            }
        }
        return false;
    }

    private void setUserPersonalInfo(User user, int id) throws SQLException {
        user.setIdUser(id);
        user.setName(rs.getString("Name"));
        user.setEmail(rs.getString("Email"));
        user.setBalance(rs.getInt("Balance"));

    }

    private void setBankPropositions(ArrayList<Bank> banks) throws SQLException {
        Proposition deposit;
        while (rs.next()) {
            deposit = setPropositionInfo(new Proposition());

            int index = isBankHere(banks);
            if (index == -1) {
                banks.add(addBankProposition(new Bank(), deposit));
            } else {
                banks.get(index).addPropositions(deposit);
            }
        }
    }
}
