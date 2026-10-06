package com.bank.validation;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void validateAmount(double amount) {

        if (amount <= 0) {

            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }
    }

    public static void validateAccountNumber(
            String accountNumber) {

        if (accountNumber == null ||
                accountNumber.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Account number cannot be empty.");
        }
    }

    public static void validateLoanAmount(double amount) {

        if (amount <= 0) {

            throw new IllegalArgumentException(
                    "Loan amount must be greater than zero.");
        }
    }

    public static void validateInterestRate(
            double interestRate) {

        if (interestRate < 0) {

            throw new IllegalArgumentException(
                    "Interest rate cannot be negative.");
        }
    }

    public static void validateTermMonths(int termMonths) {

        if (termMonths <= 0) {

            throw new IllegalArgumentException(
                    "Loan term must be greater than zero.");
        }
    }
}