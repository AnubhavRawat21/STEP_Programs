package com.gdb.services;

import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.exceptions.BlacklistedAadharOrCompanyException;
import com.gdb.dtos.responses.OpenAccountResponse;

/**
 * AccountService
 * Control Class: Has logic
 * Now refactored to use Strategy pattern via AccountServiceImplFactory.
 */
public class AccountService implements IAccountService {

    private final com.gdb.repositories.IAccountRepository accountRepository;

    public AccountService(com.gdb.repositories.IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public OpenAccountResponse openAccount(OpenAccountDTO openAccountDTO) throws AccountCreationException, AccountAlreadyExistsException, BlacklistedAadharOrCompanyException {
        // Delegate to the specific implementation based on DTO type
        IAccountServiceImpl impl = AccountServiceImplFactory.getInstance().create(openAccountDTO.getClass());
        return impl.open(openAccountDTO);
    }

    @Override
    public com.gdb.dtos.responses.CloseAccountResponse closeAccount(com.gdb.dtos.CloseAccountDTO closeAccountDTO)
            throws com.gdb.exceptions.AccountRecordNotFoundException, com.gdb.exceptions.AccountNotActiveException {
        // TODO: Student implementation
        // 1. Retrieve the account using accountRepository.getAccountByAccountNumber(...)
        // 2. Verify that the account is active. If not, throw AccountNotActiveException.
        // 3. Call accountRepository.closeAccount(...) to update repository state.

        com.gdb.dtos.responses.CloseAccountResponse response = new com.gdb.dtos.responses.CloseAccountResponse(closeAccountDTO);
        response.setResponseStatus(200, "Mock Success");
        return response;
    }
}
