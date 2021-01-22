package com.kanils.client;

import com.kanils.bank.Bank;
import com.kanils.bank.Proposition;
import com.kanils.database.Connector;
import com.kanils.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TerminalTest {
    Terminal terminal;
    User user;
    ArrayList<Bank> banks;
    Connector connector;
    ArrayList <Proposition> propositions;
    Proposition proposition;

    @Before
    public void setUp() throws Exception {
        proposition=Mockito.mock(Proposition.class);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void sortDepositsTes1t() {
        Scanner scannerMock = new Scanner(System.in);
        terminal.setScanner(scannerMock);
        Mockito.when(scannerMock.nextInt()).thenReturn(1);
    }

    @Test
    public void withdrawDeposit() {
        Mockito.when(proposition.getPuttime()).thenReturn(Date.valueOf("2019-2-10"));
        Mockito.when(proposition.getCurrenttime()).thenReturn(Date.valueOf("2019-9-20"));
        Mockito.when(proposition.getDepossum()).thenReturn(1000.0);
        Mockito.when(proposition.getPercenyear()).thenReturn(10.0);
        terminal.withdrawDeposit();

        double sum = 0;
        double expcsum = 1027.39;

        assertEquals(expcsum,sum,2);
    }

    @Test
    public void findInfo() {


    }

    @Test
    public void balanceReplenishment() {
    }

    @Test
    public void depositReplenishment() {
    }

    @Test
    public void sortDeposits() {
    }


}