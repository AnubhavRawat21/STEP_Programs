package com.gdb.controllers;

import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;

/**
 * ITransactionController
 */
public interface ITransactionController {

    public WithdrawResponse withdraw(WithdrawDTO withdrawDTO);

    public TransferResponse transfer(TransferDTO transferDTO);

    public DepositResponse deposit(DepositDTO depositDTO);
}
