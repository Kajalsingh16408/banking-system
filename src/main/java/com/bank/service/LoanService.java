package com.bank.service;

import com.bank.model.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanService {

    private final List<Loan> loans = new ArrayList<>();

    public void applyLoan(Loan loan) {

        if (loan == null) {
            throw new IllegalArgumentException(
                    "Loan cannot be null");
        }

        loans.add(loan);

        System.out.println(
                "Loan applied successfully: "
                        + loan.getLoanId());
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    public Optional<Loan> findLoanById(String loanId) {

        return loans.stream()
                .filter(loan -> loan.getLoanId().equals(loanId))
                .findFirst();
    }

    public List<Loan> getLoansByAccount(String accountNumber) {

        return loans.stream()
                .filter(loan -> loan.getAccountNumber()
                        .equals(accountNumber))
                .toList();
    }

    public double calculateTotalLoanAmount() {

        return loans.stream()
                .mapToDouble(Loan::getAmount)
                .sum();
    }

    public int countLoans() {
        return loans.size();
    }

    public double calculateSimpleInterest(Loan loan) {

        return loan.getAmount()
                * loan.getInterestRate()
                * loan.getTermMonths()
                / (100 * 12);
    }

    public double calculateTotalPayableAmount(Loan loan) {

        return loan.getAmount()
                + calculateSimpleInterest(loan);
    }

    public double calculateMonthlyEMI(Loan loan) {

        double principal = loan.getAmount();

        double monthlyRate = loan.getInterestRate() / 100 / 12;

        int months = loan.getTermMonths();

        if (monthlyRate == 0) {
            return principal / months;
        }

        return principal
                * monthlyRate
                * Math.pow(1 + monthlyRate, months)
                / (Math.pow(1 + monthlyRate, months) - 1);
    }

    public double calculateInterest(Loan loan) {

        return loan.getAmount()
                * loan.getInterestRate()
                * loan.getTermMonths()
                / (12 * 100);
    }

    public double calculateTotalPayable(Loan loan) {

        return loan.getAmount()
                + calculateInterest(loan);
    }

    public double calculateEMI(Loan loan) {

        double principal = loan.getAmount();

        double monthlyRate = loan.getInterestRate() / 12 / 100;

        int months = loan.getTermMonths();

        if (monthlyRate == 0) {
            return principal / months;
        }

        return principal
                * monthlyRate
                * Math.pow(1 + monthlyRate, months)
                / (Math.pow(1 + monthlyRate, months) - 1);
    }
}