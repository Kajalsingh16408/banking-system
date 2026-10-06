package com.bank;

import com.bank.exception.InsufficientBalanceException;
import com.bank.model.BankAccount;
import com.bank.model.Customer;
import com.bank.model.SavingsAccount;
import com.bank.service.BankingService;
import com.bank.service.TransactionService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankingSystemTest {

    @Test
    void depositShouldIncreaseBalance() throws Exception {

        Customer customer =
                new Customer(
                        "TEST-001",
                        "Test User",
                        "test@gmail.com");

        BankAccount account =
                new SavingsAccount(
                        "TEST-1001",
                        customer,
                        10000,
                        4.0);

        account.deposit(2000);

        assertEquals(
                12000,
                account.getBalance());
    }

    @Test
    void withdrawalShouldDecreaseBalance()
            throws Exception {

        Customer customer =
                new Customer(
                        "TEST-002",
                        "Test User",
                        "test2@gmail.com");

        BankAccount account =
                new SavingsAccount(
                        "TEST-1002",
                        customer,
                        10000,
                        4.0);

        account.withdraw(3000);

        assertEquals(
                7000,
                account.getBalance());
    }

    @Test
    void withdrawalGreaterThanBalanceShouldFail() {

        Customer customer =
                new Customer(
                        "TEST-003",
                        "Test User",
                        "test3@gmail.com");

        BankAccount account =
                new SavingsAccount(
                        "TEST-1003",
                        customer,
                        5000,
                        4.0);

        assertThrows(
                InsufficientBalanceException.class,
                () -> account.withdraw(6000));
    }

    @Test
    void transferShouldUpdateBothBalances()
            throws Exception {

        Customer customer1 =
                new Customer(
                        "TEST-004",
                        "User One",
                        "one@gmail.com");

        Customer customer2 =
                new Customer(
                        "TEST-005",
                        "User Two",
                        "two@gmail.com");

        BankAccount sender =
                new SavingsAccount(
                        "TEST-1004",
                        customer1,
                        10000,
                        4.0);

        BankAccount receiver =
                new SavingsAccount(
                        "TEST-1005",
                        customer2,
                        5000,
                        4.0);

        TransactionService transactionService =
                new TransactionService();

        BankingService bankingService =
                new BankingService(transactionService);

        bankingService.transfer(
                sender,
                receiver,
                2000);

        assertEquals(
                8000,
                sender.getBalance());

        assertEquals(
                7000,
                receiver.getBalance());
    }

    @Test
    void invalidAmountShouldFail() {

        assertThrows(
                IllegalArgumentException.class,
                () -> com.bank.validation.ValidationUtil
                        .validateAmount(-100));
    }

    @Test
    void emptyAccountNumberShouldFail() {

        assertThrows(
                IllegalArgumentException.class,
                () -> com.bank.validation.ValidationUtil
                        .validateAccountNumber(""));
    }
}