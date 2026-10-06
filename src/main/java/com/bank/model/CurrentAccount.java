package com.bank.model;

public class CurrentAccount extends BankAccount {

    private double interestRate;

    public CurrentAccount(
            String accountNumber,
            Customer customer,
            double balance,
            double interestRate) {

        super(accountNumber, customer, balance);

        if (interestRate < 0) {
            throw new IllegalArgumentException(
                    "Interest rate cannot be negative"
            );
        }

        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }
}