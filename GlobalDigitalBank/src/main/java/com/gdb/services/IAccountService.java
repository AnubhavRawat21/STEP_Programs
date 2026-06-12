package com.gdb.services;

import com.gdb.dtos.OpenAccountDTO;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.exceptions.BlacklistedAadharOrCompanyException;
import com.gdb.dtos.responses.OpenAccountResponse;

/**
 * IAccountService
 */
public interface IAccountService {
    public OpenAccountResponse openAccount(OpenAccountDTO openAccountDTO) throws AccountCreationException, AccountAlreadyExistsException, BlacklistedAadharOrCompanyException;

    public com.gdb.dtos.responses.CloseAccountResponse closeAccount(com.gdb.dtos.CloseAccountDTO closeAccountDTO) throws com.gdb.exceptions.AccountRecordNotFoundException, com.gdb.exceptions.AccountNotActiveException;
}

