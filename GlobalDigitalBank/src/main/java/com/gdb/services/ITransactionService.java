package com.gdb.services;

import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.AccountNotActiveException;
import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.exceptions.DailyTransferLimitExceededException;
import com.gdb.exceptions.IncorrectPinNumberException;
import com.gdb.exceptions.InsufficientFundsException;

/**
 * ITransactionService
 */
public interface ITransactionService {

    public WithdrawResponse withdraw(WithdrawDTO dto)
            throws AccountRecordNotFoundException, AccountAlreadyExistsException, IncorrectPinNumberException,
            InsufficientFundsException, AccountNotActiveException;

    public TransferResponse transfer(TransferDTO dto)
            throws AccountRecordNotFoundException, AccountNotActiveException, IncorrectPinNumberException,
            InsufficientFundsException, DailyTransferLimitExceededException;

    public DepositResponse deposit(DepositDTO dto)
            throws AccountRecordNotFoundException, IncorrectPinNumberException, AccountNotActiveException;
}

