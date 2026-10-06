package com.bank.persistence;

import com.bank.model.Transaction;

import java.io.*;
import java.util.List;

public class FileStorageService {

    private static final String FILE_NAME = "transactions.txt";

    public void saveTransactions(List<Transaction> transactions) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_NAME))) {

            for (Transaction transaction : transactions) {
                writer.write(transaction.toString());
                writer.newLine();
            }

            System.out.println("Transactions saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public void loadTransactions() {

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;

            System.out.println();
            System.out.println("LOADED TRANSACTIONS");
            System.out.println("-------------------");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
}