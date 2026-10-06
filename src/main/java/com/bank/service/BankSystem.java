package com.bank.service;

import com.bank.model.BankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankSystem {

    private static final BankSystem INSTANCE = new BankSystem();

    private final List<BankAccount> accounts;

    private BankSystem() {
        accounts = new ArrayList<>();
    }

    public static BankSystem getInstance() {
        return INSTANCE;
    }

    public void addAccount(BankAccount account) {

        if (account == null) {
            throw new IllegalArgumentException(
                    "Account cannot be null");
        }

        accounts.add(account);
    }

    public List<BankAccount> getAllAccounts() {

        return new ArrayList<>(accounts);
    }

    public Optional<BankAccount> findAccount(
            String accountNumber) {

        return accounts.stream()
                .filter(account -> account.getAccountNumber()
                        .equals(accountNumber))
                .findFirst();
    }

    public boolean removeAccount(
            String accountNumber) {

        return accounts.removeIf(
                account -> account.getAccountNumber()
                        .equals(accountNumber));
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public Optional<BankAccount> findAccountByNumber(String accountNumber) {

        return accounts.stream()
                .filter(account -> account.getAccountNumber()
                        .equals(accountNumber))
                .findFirst();
    }
}