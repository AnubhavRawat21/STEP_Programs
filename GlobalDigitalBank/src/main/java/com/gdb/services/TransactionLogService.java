package com.gdb.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.gdb.models.FundTransfer;
import com.gdb.models.Transaction;
import com.gdb.models.TransactionType;
import com.gdb.store.TransactionLogStore;

/**
 * TransactionLogService
 */
public class TransactionLogService {

    public void logTransfer(FundTransfer transfer, double fromBalanceAfter, double toBalanceAfter) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();

        // FromAccount's Record
        Transaction out = new Transaction(
                TransactionType.TRANSFER_OUT,
                transfer.getTransferAmount(),
                fromBalanceAfter,
                now,
                "Transfer to " + transfer.getToAccount().getAccountNumber() + " via " + transfer.getTransferMode());
        addEntry(transfer.getFromAccount().getAccountNumber(), date, TransactionType.TRANSFER_OUT, out);

        // ToAccount's Record
        Transaction in = new Transaction(
                TransactionType.TRANSFER_IN,
                transfer.getTransferAmount(),
                toBalanceAfter,
                now,
                "Transfer from " + transfer.getFromAccount().getAccountNumber() + " via " + transfer.getTransferMode());
        addEntry(transfer.getToAccount().getAccountNumber(), date, TransactionType.TRANSFER_IN, in);
    }

    public void logWithdraw(String accountNum, double amount, double newBalance) {
        LocalDateTime now = LocalDateTime.now();
        Transaction t = new Transaction(TransactionType.WITHDRAW, amount, newBalance, now, "Cash withdrawal");
        addEntry(accountNum, now.toLocalDate(), TransactionType.WITHDRAW, t);
    }

    public void logDeposit(String accountNum, double amount, double newBalance) {
        LocalDateTime now = LocalDateTime.now();
        Transaction t = new Transaction(TransactionType.DEPOSIT, amount, newBalance, now, "Cash deposit");
        addEntry(accountNum, now.toLocalDate(), TransactionType.DEPOSIT, t);
    }

    public double getDailyTransferTotal(String accountNum, LocalDate date) {
        var accountLogs = TransactionLogStore.logs.get(accountNum);
        if (accountLogs == null)
            return 0.0;

        var dateLogs = accountLogs.get(date);
        if (dateLogs == null)
            return 0.0;

        var transferOutLogs = dateLogs.get(TransactionType.TRANSFER_OUT);
        if (transferOutLogs == null)
            return 0.0;

        return transferOutLogs.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private void addEntry(String accountNum, LocalDate date, TransactionType type, Transaction transaction) {
        TransactionLogStore.logs
                .computeIfAbsent(accountNum, k -> new HashMap<>())
                .computeIfAbsent(date, k -> new HashMap<>())
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(transaction);
    }
}
