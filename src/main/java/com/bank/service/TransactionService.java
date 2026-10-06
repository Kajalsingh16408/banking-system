package com.bank.service;

import com.bank.model.Transaction;
import com.bank.model.TransactionType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {

        private List<Transaction> transactions;

        private static final String FILE_NAME = "transactions.dat";

        public TransactionService() {
                this.transactions = new ArrayList<>();
        }

        public Transaction recordTransaction(
                        String transactionId,
                        String accountNumber,
                        TransactionType type,
                        double amount,
                        String description) {

                Transaction transaction = new Transaction(
                                transactionId,
                                accountNumber,
                                type.name(),
                                amount,
                                description);

                transactions.add(transaction);

                return transaction;
        }

        public List<Transaction> getAllTransactions() {

                return new ArrayList<>(transactions);
        }

        public List<Transaction> getTransactionsByAccount(
                        String accountNumber) {

                return transactions.stream()
                                .filter(transaction -> transaction.getAccountNumber()
                                                .equals(accountNumber))
                                .collect(Collectors.toList());
        }

        public List<Transaction> getTransactionsByType(
                        TransactionType type) {

                return transactions.stream()
                                .filter(transaction -> transaction.getType()
                                                .equals(type.name()))
                                .collect(Collectors.toList());
        }

        public Optional<Transaction> findTransactionById(
                        String transactionId) {

                return transactions.stream()
                                .filter(transaction -> transaction.getTransactionId()
                                                .equals(transactionId))
                                .findFirst();
        }

        public double calculateTotalAmount() {

                return transactions.stream()
                                .mapToDouble(Transaction::getAmount)
                                .sum();
        }

        public long countTransactions() {

                return transactions.stream()
                                .count();
        }

        // ==============================
        // FILE PERSISTENCE
        // ==============================

        public void saveTransactionsToFile() {

                try (ObjectOutputStream outputStream = new ObjectOutputStream(
                                new FileOutputStream(FILE_NAME))) {

                        outputStream.writeObject(transactions);

                        System.out.println(
                                        "Transactions saved successfully.");

                } catch (IOException e) {

                        System.out.println(
                                        "Failed to save transactions: "
                                                        + e.getMessage());
                }
        }

        @SuppressWarnings("unchecked")
        public List<Transaction> loadTransactionsFromFile() {

                try (ObjectInputStream inputStream = new ObjectInputStream(
                                new FileInputStream(FILE_NAME))) {

                        transactions = (List<Transaction>) inputStream.readObject();

                        return new ArrayList<>(transactions);

                } catch (FileNotFoundException e) {

                        System.out.println(
                                        "Transaction file not found.");

                } catch (IOException | ClassNotFoundException e) {

                        System.out.println(
                                        "Failed to load transactions: "
                                                        + e.getMessage());
                }

                return new ArrayList<>();
        }

    
}