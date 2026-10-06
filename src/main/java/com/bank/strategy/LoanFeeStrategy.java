package com.bank.strategy;

public class LoanFeeStrategy implements FeeStrategy {

    @Override
    public double calculateFee(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        return amount * 0.01;
    }
}