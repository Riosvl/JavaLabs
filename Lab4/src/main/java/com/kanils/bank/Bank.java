package com.kanils.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Bank {
    private String name;
    private ArrayList<Proposition> propositions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + propositions;
    }

    public ArrayList<Proposition> getPropositions() {
        return propositions;
    }

    public void addPropositions(Proposition proposition) {
        this.propositions.add(proposition);

    }

    public static Comparator<Bank> BankNameComparator = new Comparator<Bank>() {
        @Override
        public int compare(Bank bank, Bank bank2) {
            String bankName = bank.getName().toUpperCase();
            String bank2Name = bank2.getName().toUpperCase();
            return bankName.compareTo(bank2Name);
        }
    };

    public static Comparator<Bank> BankPercentComparator = new Comparator<Bank>() {
        @Override
        public int compare(Bank bank1, Bank bank2) {

            Collections.sort(bank1.getPropositions(), Proposition.PropopositionPercentComparator);
            Collections.sort(bank2.getPropositions(), Proposition.PropopositionPercentComparator);

            return (int) (bank1.getPropositions().get(0).getPercenyear() - bank2.getPropositions().get(0).getPercenyear());
        }
    };

    public static Comparator<Bank> BankSumdepositComparator = new Comparator<Bank>() {
        @Override
        public int compare(Bank bank1, Bank bank2) {

            Collections.sort(bank1.getPropositions(), Proposition.PropositionDepossumComparator);
            Collections.sort(bank2.getPropositions(), Proposition.PropositionDepossumComparator);

            return (int) (bank1.getPropositions().get(0).getDepossum() - bank2.getPropositions().get(0).getDepossum());
        }
    };
}
