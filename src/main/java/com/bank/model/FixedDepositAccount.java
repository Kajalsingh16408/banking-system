package com.bank.model;

public class FixedDepositAccount extends BankAccount {

    private double interestRate;
    private int termMonths;

    public FixedDepositAccount(
            String accountNumber,
            Customer customer,
            double balance,
            double interestRate,
            int termMonths) {

        super(accountNumber, customer, balance);

        if (interestRate < 0) {
            throw new IllegalArgumentException(
                    "Interest rate cannot be negative"
            );
        }

        if (termMonths <= 0) {
            throw new IllegalArgumentException(
                    "Term must be greater than zero"
            );
        }

        this.interestRate = interestRate;
        this.termMonths = termMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }
}