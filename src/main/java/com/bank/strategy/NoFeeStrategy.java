package com.bank.strategy;

public class NoFeeStrategy implements FeeStrategy {

    @Override
    public double calculateFee(double amount) {
        return 0;
    }
}