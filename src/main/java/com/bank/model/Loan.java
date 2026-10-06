package com.bank.model;

public class Loan {

    private final String loanId;
    private final String accountNumber;
    private final double amount;
    private final double interestRate;
    private final int termMonths;
    private final String loanType;

    private Loan(Builder builder) {

        this.loanId = builder.loanId;
        this.accountNumber = builder.accountNumber;
        this.amount = builder.amount;
        this.interestRate = builder.interestRate;
        this.termMonths = builder.termMonths;
        this.loanType = builder.loanType;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public String getLoanType() {
        return loanType;
    }

    @Override
    public String toString() {

        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", termMonths=" + termMonths +
                ", loanType='" + loanType + '\'' +
                '}';
    }

    public static class Builder {

        private String loanId;
        private String accountNumber;
        private double amount;
        private double interestRate;
        private int termMonths;
        private String loanType;

        public Builder loanId(String loanId) {
            this.loanId = loanId;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder termMonths(int termMonths) {
            this.termMonths = termMonths;
            return this;
        }

        public Builder loanType(String loanType) {
            this.loanType = loanType;
            return this;
        }

        public Loan build() {

            if (loanId == null || loanId.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Loan ID is required"
                );
            }

            if (accountNumber == null ||
                    accountNumber.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Account number is required"
                );
            }

            if (amount <= 0) {
                throw new IllegalArgumentException(
                        "Loan amount must be greater than zero"
                );
            }

            if (interestRate < 0) {
                throw new IllegalArgumentException(
                        "Interest rate cannot be negative"
                );
            }

            if (termMonths <= 0) {
                throw new IllegalArgumentException(
                        "Loan term must be greater than zero"
                );
            }

            if (loanType == null ||
                    loanType.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Loan type is required"
                );
            }

            return new Loan(this);
        }
    }
}