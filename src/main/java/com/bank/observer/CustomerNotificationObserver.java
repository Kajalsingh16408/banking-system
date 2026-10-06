package com.bank.observer;

import com.bank.model.Transaction;

public class CustomerNotificationObserver
        implements TransactionObserver {

    @Override
    public void update(Transaction transaction) {

        System.out.println(
                "NOTIFICATION: Transaction "
                        + transaction.getTransactionId()
                        + " completed for account "
                        + transaction.getAccountNumber()
        );
    }
}