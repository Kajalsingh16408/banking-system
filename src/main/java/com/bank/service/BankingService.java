package com.bank.service;

import com.bank.exception.InsufficientBalanceException;
import com.bank.model.BankAccount;
import com.bank.model.TransactionType;

public class BankingService {

    private TransactionService transactionService;

    public BankingService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void transfer(
            BankAccount sender,
            BankAccount receiver,
            double amount)
            throws InsufficientBalanceException {

        if (sender == null) {
            throw new IllegalArgumentException(
                    "Sender account cannot be null"
            );
        }

        if (receiver == null) {
            throw new IllegalArgumentException(
                    "Receiver account cannot be null"
            );
        }

        if (sender == receiver) {
            throw new IllegalArgumentException(
                    "Sender and receiver cannot be the same account"
            );
        }

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero"
            );
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        transactionService.recordTransaction(
                "TXN-TRANSFER-SENDER",
                sender.getAccountNumber(),
                TransactionType.TRANSFER,
                amount,
                "Transfer to " + receiver.getAccountNumber()
        );

        transactionService.recordTransaction(
                "TXN-TRANSFER-RECEIVER",
                receiver.getAccountNumber(),
                TransactionType.TRANSFER,
                amount,
                "Transfer from " + sender.getAccountNumber()
        );
    }
}