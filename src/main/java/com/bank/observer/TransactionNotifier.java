package com.bank.observer;

import com.bank.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionNotifier {

    private final List<TransactionObserver> observers = new ArrayList<>();

    public void addObserver(TransactionObserver observer) {

        if (observer == null) {
            throw new IllegalArgumentException(
                    "Observer cannot be null");
        }

        observers.add(observer);
    }

    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Transaction transaction) {

        for (TransactionObserver observer : observers) {
            observer.update(transaction);
        }
    }

    public void update(Transaction transaction) {

        System.out.println(
                "NOTIFICATION: Transaction "
                        + transaction.getTransactionId()
                        + " completed for account "
                        + transaction.getAccountNumber());
    }
}