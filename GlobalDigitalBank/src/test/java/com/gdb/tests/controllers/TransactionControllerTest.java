package com.gdb.tests.controllers;

import com.gdb.controllers.TransactionController;
import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.TransferModeDTO;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;
import com.gdb.exceptions.*;
import com.gdb.services.ITransactionService;
import com.gdb.utils.TransactionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private ITransactionService transactionService;
    @Mock
    private TransactionValidator validator;

    @InjectMocks
    private TransactionController controller;

    @Test
    void TestTransfer_Success() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500.0, TransferModeDTO.IMPS, "1234");
        TransferResponse response = new TransferResponse(dto);
        when(transactionService.transfer(any(TransferDTO.class))).thenReturn(response);

        TransferResponse result = controller.transfer(dto);

        assertEquals(200, result.getHttpStatusCode());
        verify(transactionService).transfer(dto);
    }

    @Test
    void TestTransfer_BusinessError() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500000.0, TransferModeDTO.IMPS, "1234");
        when(transactionService.transfer(any(TransferDTO.class)))
                .thenThrow(new InsufficientFundsException("Insufficient funds"));

        TransferResponse result = controller.transfer(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestWithdraw_Success() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        WithdrawResponse response = new WithdrawResponse(dto);
        when(transactionService.withdraw(any(WithdrawDTO.class))).thenReturn(response);

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(200, result.getHttpStatusCode());
    }

    @Test
    void TestWithdraw_SystemError() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        when(transactionService.withdraw(any(WithdrawDTO.class)))
                .thenThrow(new RuntimeException("Fatal Error"));

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(500, result.getHttpStatusCode());
    }

    // ==================== NEW TRANSFER TESTS ====================

    @Test
    void TestTransfer_NullDTO() {
        TransferResponse result = controller.transfer(null);

        assertEquals(400, result.getHttpStatusCode());
        assertEquals("Validation Error: DTO cannot be null", result.getMessage());
    }

    @Test
    void TestTransfer_InvalidPin() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500.0, TransferModeDTO.IMPS, "1234");
        when(transactionService.transfer(any(TransferDTO.class)))
                .thenThrow(new IncorrectPinNumberException("Invalid pin"));

        TransferResponse result = controller.transfer(dto);

        assertEquals(400, result.getHttpStatusCode());
        assertTrue(result.getMessage().contains("Error:"));
    }

    @Test
    void TestTransfer_AccountNotFound() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500.0, TransferModeDTO.IMPS, "1234");
        when(transactionService.transfer(any(TransferDTO.class)))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        TransferResponse result = controller.transfer(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestTransfer_AccountNotActive() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500.0, TransferModeDTO.IMPS, "1234");
        when(transactionService.transfer(any(TransferDTO.class)))
                .thenThrow(new AccountNotActiveException("Account not active"));

        TransferResponse result = controller.transfer(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestTransfer_DailyLimitExceeded() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500.0, TransferModeDTO.IMPS, "1234");
        when(transactionService.transfer(any(TransferDTO.class)))
                .thenThrow(new DailyTransferLimitExceededException("Daily limit exceeded"));

        TransferResponse result = controller.transfer(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestTransfer_UnexpectedError() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 500.0, TransferModeDTO.IMPS, "1234");
        when(transactionService.transfer(any(TransferDTO.class)))
                .thenThrow(new RuntimeException("Unexpected"));

        TransferResponse result = controller.transfer(dto);

        assertEquals(500, result.getHttpStatusCode());
        assertTrue(result.getMessage().contains("Internal Server Error"));
    }

    // ==================== NEW WITHDRAW TESTS ====================

    @Test
    void TestWithdraw_NullDTO() {
        WithdrawResponse result = controller.withdraw(null);

        assertEquals(400, result.getHttpStatusCode());
        assertEquals("Validation Error: DTO cannot be null", result.getMessage());
    }

    @Test
    void TestWithdraw_AccountNotFound() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        when(transactionService.withdraw(any(WithdrawDTO.class)))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestWithdraw_InsufficientFunds() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        when(transactionService.withdraw(any(WithdrawDTO.class)))
                .thenThrow(new InsufficientFundsException("Insufficient funds"));

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestWithdraw_AccountNotActive() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        when(transactionService.withdraw(any(WithdrawDTO.class)))
                .thenThrow(new AccountNotActiveException("Account not active"));

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestWithdraw_AccountAlreadyExists() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        when(transactionService.withdraw(any(WithdrawDTO.class)))
                .thenThrow(new AccountAlreadyExistsException("Account already exists"));

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestWithdraw_IncorrectPin() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100.0, "1234");
        when(transactionService.withdraw(any(WithdrawDTO.class)))
                .thenThrow(new IncorrectPinNumberException("Invalid pin"));

        WithdrawResponse result = controller.withdraw(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    // ==================== NEW DEPOSIT TESTS ====================

    @Test
    void TestDeposit_Success() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 100.0, "1234");
        DepositResponse response = new DepositResponse(dto);
        when(transactionService.deposit(any(DepositDTO.class))).thenReturn(response);

        DepositResponse result = controller.deposit(dto);

        assertEquals(200, result.getHttpStatusCode());
    }

    @Test
    void TestDeposit_NullDTO() {
        DepositResponse result = controller.deposit(null);

        assertEquals(400, result.getHttpStatusCode());
        assertEquals("Validation Error: DTO cannot be null", result.getMessage());
    }

    @Test
    void TestDeposit_AccountNotFound() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 100.0, "1234");
        when(transactionService.deposit(any(DepositDTO.class)))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        DepositResponse result = controller.deposit(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestDeposit_AccountNotActive() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 100.0, "1234");
        when(transactionService.deposit(any(DepositDTO.class)))
                .thenThrow(new AccountNotActiveException("Account not active"));

        DepositResponse result = controller.deposit(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestDeposit_IncorrectPin() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 100.0, "1234");
        when(transactionService.deposit(any(DepositDTO.class)))
                .thenThrow(new IncorrectPinNumberException("Invalid pin"));

        DepositResponse result = controller.deposit(dto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestDeposit_SystemError() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 100.0, "1234");
        when(transactionService.deposit(any(DepositDTO.class)))
                .thenThrow(new RuntimeException("Fatal Error"));

        DepositResponse result = controller.deposit(dto);

        assertEquals(500, result.getHttpStatusCode());
    }
}
