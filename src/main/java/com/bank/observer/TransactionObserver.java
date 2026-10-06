package com.bank.observer;

import com.bank.model.Transaction;

public interface TransactionObserver {

    void update(Transaction transaction);
}