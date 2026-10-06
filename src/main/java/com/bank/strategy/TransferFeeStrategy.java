package com.bank.strategy;

public class TransferFeeStrategy implements FeeStrategy {

    @Override
    public double calculateFee(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        return amount * 0.005;
    }
}