package com.gdb.repositories;

import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.models.Account;

/**
 * IAccountRepository
 */
public interface IAccountRepository {

    public Account getAccountByAccountNumber(String accountNumber) throws AccountRecordNotFoundException;

    public boolean checkIfSavingsAccountExists(String aadharNumber);

    public boolean checkIfCurrentAccountExists(String registrationNumber);

    public boolean checkIfAadharIsBlacklisted(String aadharNumber);

    public boolean checkIfCompanyIsBlacklisted(String registrationNumber);

    public String generateAccountNumber();

    public boolean storeAccountInformation(Account account);

    public void closeAccount(String accountNumber, java.time.LocalDateTime closedDate) throws com.gdb.exceptions.AccountRecordNotFoundException;
}

