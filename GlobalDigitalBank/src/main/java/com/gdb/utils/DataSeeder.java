package com.gdb.utils;

import com.gdb.models.*;
import com.gdb.store.AccountStore;
import com.gdb.store.TransactionLogStore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DataSeeder
 * Utility to populate the in-memory store with large amounts of data for stress
 * testing.
 */
public class DataSeeder {

    private static final Random random = new Random();

    public static void seed(int accountCount) {
        System.out.println("Seeding " + accountCount + " accounts and transactions...");

        for (int i = 1; i <= accountCount; i++) {
            Privilege privilege = Privilege.values()[random.nextInt(Privilege.values().length)];
            String pin = String.format("%04d", random.nextInt(10000));
            double balance = 1000 + (random.nextDouble() * 500000);
            String accNum = AccountStore.getNextAccountNumber();

            Account account;
            if (i % 2 == 0) {
                // Current Account
                account = new CurrentAccount(
                        accNum,
                        "Company " + i,
                        balance,
                        privilege,
                        pin,
                        "Enterprise " + i,
                        "https://comp" + i + ".com",
                        "REG" + (100000 + i));
            } else {
                // Savings Account
                Gender gender = Gender.values()[random.nextInt(Gender.values().length)];
                account = new SavingsAccount(
                        accNum,
                        "User " + i,
                        balance,
                        privilege,
                        pin,
                        LocalDate.now().minusYears((long) 18 + random.nextInt(50)),
                        gender,
                        "9" + String.format("%09d", i),
                        "5" + String.format("%011d", i));
            }

            // Set some accounts to inactive (5% chance)
            boolean isActive = random.nextDouble() > 0.05;
            account.setActive(isActive);
            account.setActivatedDate(LocalDateTime.now().minusMonths(6));
            if (!isActive) {
                account.setClosedDate(LocalDateTime.now().minusDays(10));
            }

            AccountStore.accounts.add(account);

            // Generate random transactions for some accounts (first 1000)
            if (i <= 1000) {
                seedTransactions(account);
            }
        }

        System.out.println("Seeding completed successfully.");
    }

    private static void seedTransactions(Account account) {
        String accNum = account.getAccountNumber();
        LocalDate today = LocalDate.now();

        // 1-5 transactions for today
        int txCount = 1 + random.nextInt(5);

        Map<LocalDate, Map<TransactionType, List<Transaction>>> dateMap = TransactionLogStore.logs
                .computeIfAbsent(accNum, k -> new HashMap<>());
        Map<TransactionType, List<Transaction>> typeMap = dateMap.computeIfAbsent(today, k -> new HashMap<>());
        List<Transaction> txList = typeMap.computeIfAbsent(TransactionType.TRANSFER_OUT, k -> new ArrayList<>());

        for (int j = 0; j < txCount; j++) {
            // Random amount between 100 and 15000
            double amount = 100 + (random.nextDouble() * 15000);

            // Create a dummy transaction
            Transaction tx = new Transaction(
                    TransactionType.TRANSFER_OUT,
                    amount,
                    account.getBalance(), // dummy balanceAfter
                    today.atStartOfDay().plusHours(random.nextInt(24)),
                    "Initial Seed Transaction");
            txList.add(tx);
        }
    }
}
