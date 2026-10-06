package com.bank.strategy;

public class FeeCalculator {

    private FeeStrategy feeStrategy;

    public FeeCalculator(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public double calculateFee(double amount) {

        if (feeStrategy == null) {
            throw new IllegalStateException(
                    "Fee strategy is not configured"
            );
        }

        return feeStrategy.calculateFee(amount);
    }
}