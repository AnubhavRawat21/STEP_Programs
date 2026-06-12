package com.gdb.controllers;

import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.responses.OpenAccountResponse;

/**
 * IAccountController
 */
public interface IAccountController {
    public OpenAccountResponse openAccount(OpenAccountDTO openAccountDto);

    public com.gdb.dtos.responses.CloseAccountResponse closeAccount(com.gdb.dtos.CloseAccountDTO closeAccountDto);
}

