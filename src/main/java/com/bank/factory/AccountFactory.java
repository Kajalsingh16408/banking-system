package com.bank.factory;

import com.bank.model.AccountType;
import com.bank.model.BankAccount;
import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.FixedDepositAccount;
import com.bank.model.SavingsAccount;

public class AccountFactory {

    public static BankAccount createAccount(
            AccountType accountType,
            String accountNumber,
            Customer customer,
            double balance) {

        if (accountType == null) {
            throw new IllegalArgumentException(
                    "Account type cannot be null"
            );
        }

        switch (accountType) {

            case SAVINGS:
                return new SavingsAccount(
                        accountNumber,
                        customer,
                        balance,
                        4.0
                );

            case CURRENT:
                return new CurrentAccount(
                        accountNumber,
                        customer,
                        balance,
                        0.5
                );

            case FIXED_DEPOSIT:
                return new FixedDepositAccount(
                        accountNumber,
                        customer,
                        balance,
                        6.5,
                        12
                );

            default:
                throw new IllegalArgumentException(
                        "Unsupported account type"
                );
        }
    }
}