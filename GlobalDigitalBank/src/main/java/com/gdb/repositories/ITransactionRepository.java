package com.gdb.repositories;

import com.gdb.exceptions.AccountRecordNotFoundException;

/**
 * ITransactionRepository
 */
public interface ITransactionRepository {

    public double withdrawAmount(String accountNumber, double withdrawAmount) throws AccountRecordNotFoundException;

    public double transferAmount(String fromAccountNumber, String toAccountNumber, double transferAmount) throws AccountRecordNotFoundException;

    public double depositAmount(String accountNumber, double depositAmount) throws AccountRecordNotFoundException;

}
