package com.kanils.user;

import com.kanils.bank.Bank;

import java.util.ArrayList;

public class User {
    private String email;
    private String name;
    private double balance;
    private String password;
    private ArrayList<Bank> deposits;
    private int idUser;

    public User(){}
    public User(String email,String name,double balance, String password,int idUser){
        this.email=email;
        this.name = name;
        this.balance=balance;
        this.password=password;
        this.idUser=idUser;
    }
    public int getIdUser() { return idUser;}

    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Bank> getDeposits() {
        return deposits;
    }

    public void addBank(Bank bank){
        deposits.add(bank);
    }

    public void setDeposits(ArrayList<Bank> deposits) {
        this.deposits = deposits;
    }
}