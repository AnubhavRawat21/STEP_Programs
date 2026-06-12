package com.gdb.services;

import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.exceptions.BlacklistedAadharOrCompanyException;

/**
 * IAccountServiceImpl
 * Strategy interface for specific account type implementations.
 */
public interface IAccountServiceImpl {
    OpenAccountResponse open(OpenAccountDTO openAccountDTO) throws AccountCreationException, AccountAlreadyExistsException, BlacklistedAadharOrCompanyException;
}
