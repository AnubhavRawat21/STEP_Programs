package com.gdb.repositories;

import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.models.Account;
import com.gdb.models.CurrentAccount;
import com.gdb.models.SavingsAccount;
import com.gdb.store.AccountStore;
import com.gdb.utils.AadharUtils;

/**
 * AccountRepository
 * Repository Class: It connects to the database
 * (Otherwise called DAO: Data Access Object)
 */
public class AccountRepository implements IAccountRepository {

    @Override
    public Account getAccountByAccountNumber(String accountNumber) throws AccountRecordNotFoundException {
        return AccountStore.accounts.stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst()
                .orElseThrow(() -> new AccountRecordNotFoundException("Account record not found"));
    }

    @Override
    public boolean checkIfSavingsAccountExists(String aadharNumber) {
        String normalized = AadharUtils.normalizeAadhar(aadharNumber);
        return AccountStore.accounts.stream()
                .filter(this::isSavingsAccount)
                .map(this::asSavingsAccount)
                .anyMatch(sa -> sa.getAadharNumber().equals(normalized) && sa.isActive());
    }

    @Override
    public boolean checkIfCurrentAccountExists(String registrationNumber) {
        return AccountStore.accounts.stream()
                .filter(this::isCurrentAccount)
                .map(this::asCurrentAccount)
                .anyMatch(ca -> ca.getRegistrationNumber().equals(registrationNumber) && ca.isActive());
    }

    @Override
    public boolean checkIfAadharIsBlacklisted(String aadharNumber) {
        String normalized = AadharUtils.normalizeAadhar(aadharNumber);

        return AccountStore.blackListedAccounts.stream()
                .filter(this::isSavingsAccount)
                .map(this::asSavingsAccount)
                .anyMatch(sa -> sa.getAadharNumber().equals(normalized));
    }

    @Override
    public boolean checkIfCompanyIsBlacklisted(String registrationNumber) {
        if (registrationNumber == null)
            return false;

        return AccountStore.blackListedAccounts.stream()
                .filter(this::isCurrentAccount)
                .map(this::asCurrentAccount)
                .anyMatch(ca -> ca.getRegistrationNumber().equals(registrationNumber));
    }

    private boolean isSavingsAccount(Account account) {
        return account instanceof SavingsAccount;
    }

    private SavingsAccount asSavingsAccount(Account account) {
        return (SavingsAccount) account;
    }

    private boolean isCurrentAccount(Account account) {
        return account instanceof CurrentAccount;
    }

    private CurrentAccount asCurrentAccount(Account account) {
        return (CurrentAccount) account;
    }

    @Override
    public String generateAccountNumber() {
        return AccountStore.getNextAccountNumber();
    }

    @Override
    public boolean storeAccountInformation(Account account) {
        return AccountStore.accounts.add(account);
    }

    @Override
    public void closeAccount(String accountNumber, java.time.LocalDateTime closedDate) throws AccountRecordNotFoundException {
        Account account = getAccountByAccountNumber(accountNumber);
        account.setActive(false);
        account.setClosedDate(closedDate);
    }
}
