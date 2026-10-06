package com.bank;

import com.bank.exception.InsufficientBalanceException;
import com.bank.factory.AccountFactory;
import com.bank.logging.BankingLogger;
import com.bank.model.AccountType;
import com.bank.model.BankAccount;
import com.bank.model.Customer;
import com.bank.model.Loan;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.observer.CustomerNotificationObserver;
import com.bank.observer.TransactionNotifier;
import com.bank.persistence.FileStorageService;
import com.bank.service.BankSystem;
import com.bank.service.BankingService;
import com.bank.service.LoanService;
import com.bank.service.TransactionService;
import com.bank.strategy.FeeCalculator;
import com.bank.strategy.LoanFeeStrategy;
import com.bank.strategy.TransferFeeStrategy;
import com.bank.monitoring.PerformanceMonitor;

import java.util.List;
import java.util.Optional;

public class Main {

        public static void main(String[] args) {

                BankingLogger.info("Banking System started.");

                // ========================================
                // CUSTOMERS
                // ========================================

                Customer customer = new Customer(
                                "C001",
                                "John Doe",
                                "john@gmail.com");

                Customer customer2 = new Customer(
                                "C002",
                                "Jane Smith",
                                "jane@gmail.com");

                // ========================================
                // ACCOUNT CREATION - FACTORY PATTERN
                // ========================================

                BankAccount savingsAccount = AccountFactory.createAccount(
                                AccountType.SAVINGS,
                                "SAV-1001",
                                customer,
                                50000);

                BankAccount secondSavingsAccount = AccountFactory.createAccount(
                                AccountType.SAVINGS,
                                "SAV-1002",
                                customer2,
                                30000);

                BankAccount currentAccount = AccountFactory.createAccount(
                                AccountType.CURRENT,
                                "CUR-1001",
                                customer,
                                75000);

                BankAccount fixedDepositAccount = AccountFactory.createAccount(
                                AccountType.FIXED_DEPOSIT,
                                "FD-1001",
                                customer,
                                100000);

                // ========================================
                // SERVICES
                // ========================================

                TransactionService transactionService = new TransactionService();

                BankSystem bankSystem = BankSystem.getInstance();

                bankSystem.addAccount(savingsAccount);
                bankSystem.addAccount(secondSavingsAccount);
                bankSystem.addAccount(currentAccount);
                bankSystem.addAccount(fixedDepositAccount);

                BankingService bankingService = new BankingService(transactionService);

                // ========================================
                // HEADER
                // ========================================

                System.out.println();
                System.out.println("================================");
                System.out.println("       JAVA BANKING SYSTEM");
                System.out.println("================================");

                // ========================================
                // ACCOUNT DETAILS
                // ========================================

                System.out.println();
                System.out.println("Savings Account");
                System.out.println("Account: "
                                + savingsAccount.getAccountNumber());
                System.out.println("Balance: ₹"
                                + savingsAccount.getBalance());

                System.out.println();
                System.out.println("Current Account");
                System.out.println("Account: "
                                + currentAccount.getAccountNumber());
                System.out.println("Balance: ₹"
                                + currentAccount.getBalance());

                System.out.println();
                System.out.println("Fixed Deposit Account");
                System.out.println("Account: "
                                + fixedDepositAccount.getAccountNumber());
                System.out.println("Balance: ₹"
                                + fixedDepositAccount.getBalance());

                // ========================================
                // DEPOSIT + WITHDRAWAL
                // ========================================

                System.out.println();

                try {

                        savingsAccount.deposit(5000);

                        transactionService.recordTransaction(
                                        "TXN-001",
                                        savingsAccount.getAccountNumber(),
                                        TransactionType.DEPOSIT,
                                        5000,
                                        "Cash deposit");

                        BankingLogger.info(
                                        "Deposit completed for account "
                                                        + savingsAccount.getAccountNumber()
                                                        + " amount=5000");

                        savingsAccount.withdraw(2000);

                        transactionService.recordTransaction(
                                        "TXN-002",
                                        savingsAccount.getAccountNumber(),
                                        TransactionType.WITHDRAWAL,
                                        2000,
                                        "Cash withdrawal");

                        BankingLogger.info(
                                        "Withdrawal completed for account "
                                                        + savingsAccount.getAccountNumber()
                                                        + " amount=2000");

                        System.out.println(
                                        "Savings balance after transactions: ₹"
                                                        + savingsAccount.getBalance());

                } catch (InsufficientBalanceException e) {

                        BankingLogger.error(
                                        "Transaction failed: " + e.getMessage());

                        System.out.println(
                                        "Transaction failed: "
                                                        + e.getMessage());
                }

                // ========================================
                // TRANSFER
                // ========================================

                System.out.println();

                try {

                        bankingService.transfer(
                                        savingsAccount,
                                        secondSavingsAccount,
                                        5000);

                        System.out.println("TRANSFER SUCCESSFUL");

                        System.out.println(
                                        "Sender balance: ₹"
                                                        + savingsAccount.getBalance());

                        System.out.println(
                                        "Receiver balance: ₹"
                                                        + secondSavingsAccount.getBalance());

                        BankingLogger.info(
                                        "Transfer completed from "
                                                        + savingsAccount.getAccountNumber()
                                                        + " to "
                                                        + secondSavingsAccount.getAccountNumber()
                                                        + " amount=5000");

                } catch (InsufficientBalanceException e) {

                        BankingLogger.error(
                                        "Transfer failed: " + e.getMessage());

                        System.out.println(
                                        "Transfer failed: "
                                                        + e.getMessage());
                }

                // ========================================
                // TRANSACTION HISTORY
                // ========================================

                System.out.println();
                System.out.println("TRANSACTION HISTORY");
                System.out.println("-------------------");

                for (Transaction transaction : transactionService.getAllTransactions()) {

                        System.out.println(transaction);
                }

                // ========================================
                // PERFORMANCE MONITORING
                // ========================================

                System.out.println();
                System.out.println("PERFORMANCE MONITORING");
                System.out.println("----------------------");

                long performanceStart = PerformanceMonitor.start();

                transactionService.getAllTransactions()
                                .stream()
                                .filter(t -> t.getAmount() > 0)
                                .count();

                PerformanceMonitor.end(performanceStart);

                // ========================================
                // STREAM OPERATIONS
                // ========================================

                System.out.println();
                System.out.println("STREAM OPERATIONS");
                System.out.println("-----------------");

                List<Transaction> deposits = transactionService.getTransactionsByType(
                                TransactionType.DEPOSIT);

                System.out.println(
                                "Total deposits: "
                                                + deposits.size());

                List<Transaction> withdrawals = transactionService.getTransactionsByType(
                                TransactionType.WITHDRAWAL);

                System.out.println(
                                "Total withdrawals: "
                                                + withdrawals.size());

                List<Transaction> accountTransactions = transactionService.getTransactionsByAccount(
                                "SAV-1001");

                System.out.println(
                                "Savings account transactions: "
                                                + accountTransactions.size());

                System.out.println(
                                "Total transaction amount: ₹"
                                                + transactionService.calculateTotalAmount());

                System.out.println(
                                "Total transaction count: "
                                                + transactionService.countTransactions());

                // ========================================
                // OPTIONAL
                // ========================================

                System.out.println();
                System.out.println("OPTIONAL SEARCH");
                System.out.println("----------------");

                Optional<Transaction> transaction = transactionService.findTransactionById(
                                "TXN-001");

                transaction.ifPresent(
                                foundTransaction -> System.out.println(
                                                "Found: "
                                                                + foundTransaction));

                // ========================================
                // BANK SYSTEM - SINGLETON
                // ========================================

                System.out.println();
                System.out.println("BANK SYSTEM");
                System.out.println("-----------");

                System.out.println(
                                "Total accounts: "
                                                + bankSystem.getAccountCount());

                Optional<BankAccount> foundAccount = bankSystem.findAccount("SAV-1001");

                foundAccount.ifPresent(
                                account -> System.out.println(
                                                "Found account: "
                                                                + account.getAccountNumber()
                                                                + " | Balance: ₹"
                                                                + account.getBalance()));

                // ========================================
                // STRATEGY PATTERN
                // ========================================

                System.out.println();
                System.out.println("STRATEGY PATTERN");
                System.out.println("-----------------");

                FeeCalculator feeCalculator = new FeeCalculator(
                                new TransferFeeStrategy());

                double transferFee = feeCalculator.calculateFee(5000);

                System.out.println(
                                "Transfer fee: ₹"
                                                + transferFee);

                feeCalculator.setFeeStrategy(
                                new LoanFeeStrategy());

                double loanFee = feeCalculator.calculateFee(100000);

                System.out.println(
                                "Loan fee: ₹"
                                                + loanFee);

                // ========================================
                // OBSERVER PATTERN
                // ========================================

                System.out.println();
                System.out.println("OBSERVER PATTERN");
                System.out.println("-----------------");

                TransactionNotifier notifier = new TransactionNotifier();

                CustomerNotificationObserver notificationObserver = new CustomerNotificationObserver();

                notifier.addObserver(notificationObserver);

                Optional<Transaction> observedTransaction = transactionService.findTransactionById(
                                "TXN-001");

                observedTransaction.ifPresent(
                                notifier::notifyObservers);

                // ========================================
                // BUILDER PATTERN
                // ========================================

                System.out.println();
                System.out.println("BUILDER PATTERN - LOAN");
                System.out.println("-----------------------");

                Loan homeLoan = new Loan.Builder()
                                .loanId("LOAN-001")
                                .accountNumber("SAV-1001")
                                .amount(500000)
                                .interestRate(8.5)
                                .termMonths(60)
                                .loanType("HOME LOAN")
                                .build();

                System.out.println(homeLoan);

                // ========================================
                // LOAN OPERATIONS
                // ========================================

                System.out.println();
                System.out.println("LOAN OPERATIONS");
                System.out.println("----------------");

                LoanService loanService = new LoanService();

                loanService.applyLoan(homeLoan);

                System.out.println(
                                "Total loans: "
                                                + loanService.countLoans());

                System.out.println(
                                "Total loan amount: ₹"
                                                + loanService.calculateTotalLoanAmount());

                System.out.println(
                                "Loan interest: ₹"
                                                + loanService.calculateSimpleInterest(
                                                                homeLoan));

                System.out.println(
                                "Total payable amount: ₹"
                                                + loanService.calculateTotalPayableAmount(
                                                                homeLoan));

                System.out.println(
                                "Monthly EMI: ₹"
                                                + loanService.calculateMonthlyEMI(
                                                                homeLoan));

                Optional<Loan> foundLoan = loanService.findLoanById("LOAN-001");

                foundLoan.ifPresent(
                                loan -> System.out.println(
                                                "Found loan: "
                                                                + loan));

                // ========================================
                // FILE PERSISTENCE
                // ========================================

                System.out.println();
                System.out.println("FILE PERSISTENCE");
                System.out.println("-----------------");

                FileStorageService fileStorageService = new FileStorageService();

                fileStorageService.saveTransactions(
                                transactionService.getAllTransactions());

                fileStorageService.loadTransactions();

                // ========================================
                // END
                // ========================================

                BankingLogger.info(
                                "Banking System execution completed successfully.");

                System.out.println();
                System.out.println("================================");
                System.out.println("       EXECUTION COMPLETED");
                System.out.println("================================");
        }
}