package com.gdb.repositories;

import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.models.Account;
import com.gdb.store.AccountStore;

/**
 * TransactionRepository
 */
public class TransactionRepository implements ITransactionRepository {

    @Override
    public double withdrawAmount(String accountNumber, double withdrawAmount) throws AccountRecordNotFoundException {
        Account account = findAccountByAccountNumber(accountNumber);
        double newBalance = calculateNewBalance(account.getBalance(), withdrawAmount, true);
        account.setBalance(newBalance);
        return newBalance;
    }

    @Override
    public double transferAmount(String fromAccountNumber, String toAccountNumber, double transferAmount) throws AccountRecordNotFoundException {
        Account fromAccount = findAccountByAccountNumber(fromAccountNumber);
        Account toAccount = findAccountByAccountNumber(toAccountNumber);
        double fromNewBalance = calculateNewBalance(fromAccount.getBalance(), transferAmount, true);
        double toNewBalance = calculateNewBalance(toAccount.getBalance(), transferAmount, false);
        fromAccount.setBalance(fromNewBalance);
        toAccount.setBalance(toNewBalance);
        return fromNewBalance;
    }

    @Override
    public double depositAmount(String accountNumber, double depositAmount) throws AccountRecordNotFoundException {
        Account account = findAccountByAccountNumber(accountNumber);
        double newBalance = calculateNewBalance(account.getBalance(), depositAmount, false);
        account.setBalance(newBalance);
        return newBalance;
    }

    private Account findAccountByAccountNumber(String accountNumber) throws AccountRecordNotFoundException {
        return AccountStore.accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new AccountRecordNotFoundException("Account Not Found"));
    }

    private double calculateNewBalance(double balance, double amount, boolean isDebit) {
        if (isDebit) {
            return balance - amount;
        } else {
            return balance + amount;
        }
    }
}
