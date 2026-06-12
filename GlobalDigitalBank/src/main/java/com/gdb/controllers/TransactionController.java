package com.gdb.controllers;

import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.responses.CloseAccountResponse;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;
import com.gdb.exceptions.*;
import com.gdb.services.ITransactionService;
import com.gdb.utils.TransactionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;

/**
 * Controller responsible for processing financial transactions.
 * Manages the boundary between user input and the core transaction services,
 * ensuring that data is correctly formatted and valid before processing.
 */
public class TransactionController implements ITransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);
    private ITransactionService transactionService;
    private TransactionValidator validator;

    public TransactionController(ITransactionService transactionService, TransactionValidator validator) {
        this.transactionService = transactionService;
        this.validator = validator;
    }

    @Override
    public WithdrawResponse withdraw(WithdrawDTO withdrawDTO) {
        // TODO: Student implementation
        // 1. Validate the input withdrawDTO using validateWithdrawRequest.
        // 2. Delegate the call to transactionService.withdraw.
        // 3. Handle specific exceptions (e.g. InvalidInputException, AccountRecordNotFoundException, IncorrectPinNumberException, InsufficientFundsException, AccountNotActiveException) and map them to HTTP status 400 with a clean message.
        // 4. Map unexpected runtime errors to HTTP status 500.
        // 5. Return response.
        try {
            // Validate request
            validateWithdrawRequest(withdrawDTO);

            // Call service
            WithdrawResponse response = transactionService.withdraw(withdrawDTO);

        } catch (InvalidInputException e) {
            logger.error("Invalid input", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(400, e.getMessage());
            return response;

        } catch (AccountRecordNotFoundException e) {
            logger.error("Account record not found", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(400, "Account record not found");
            return response;

        } catch (IncorrectPinNumberException e) {
            logger.error("Incorrect PIN number", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(400, "Incorrect PIN number");
            return response;

        } catch (InsufficientFundsException e) {
            logger.error("Insufficient funds", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(400, "Insufficient funds");
            return response;

        } catch (AccountNotActiveException e) {
            logger.error("Account not active", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(400, "Account not active");
            return response;

        } catch (RuntimeException e) {
            logger.error("Unexpected error", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(500, "Internal Server Error");
            return response;
        } catch (AccountAlreadyExistsException e) {
            logger.error("Account already exists", e);
            WithdrawResponse response = new WithdrawResponse(withdrawDTO);
            response.setResponseStatus(400, "Account already exists");
        }
        WithdrawResponse response = new WithdrawResponse(withdrawDTO);
        response.setResponseStatus(200, "Success");
        return response;
    }

    /**
     * Initiates a fund transfer transaction request.
     * 
     * @param transferDTO Details of the source, target, and amount.
     * @return TransferResponse containing the HTTP result and transaction feedback.
     */
    @Override
    public TransferResponse transfer(TransferDTO transferDTO) {
        // TODO: Student implementation
        // 1. Validate the input transferDTO using validateTransferRequest.
        // 2. Delegate the call to transactionService.transfer.
        // 3. Handle specific exceptions (e.g. InvalidInputException, AccountRecordNotFoundException, AccountNotActiveException, IncorrectPinNumberException, InsufficientFundsException, DailyTransferLimitExceededException) and map to HTTP status 400.
        // 4. Map unexpected runtime errors to HTTP status 500.
        // 5. Return response.
        TransferResponse response = new TransferResponse(transferDTO);
        response.setResponseStatus(200, "Success");
        return response;
    }

    //
    // Private helpers
    //
    private void validateWithdrawRequest(WithdrawDTO dto) throws InvalidInputException {
        if (dto == null) {
            throw new InvalidInputException("DTO cannot be null");
        }
        validator.validateAccountNumber(dto.getAccountNumber());
        validator.validatePin(dto.getPinNumber());
        validator.validateWithdrawAmount(dto.getWithdrawAmount());
    }

    private void validateTransferRequest(TransferDTO dto) throws InvalidInputException {
        if (dto == null) {
            throw new InvalidInputException("DTO cannot be null");
        }

        validator.validateAccountNumber(dto.getFromAccountNumber());
        validator.validateAccountNumber(dto.getToAccountNumber());
        validator.validateTransferAmount(dto.getTransferAmount());
        validator.validatePin(dto.getPinNumber());

    }

    private void validateDepositRequest(DepositDTO dto) throws InvalidInputException {
        if (dto == null) {
            throw new InvalidInputException("DTO cannot be null");
        }
        validator.validateAccountNumber(dto.getAccountNumber());
        validator.validatePin(dto.getPinNumber());
        validator.validateDepositAmount(dto.getDepositAmount());
    }

    @Override
    public DepositResponse deposit(DepositDTO depositDTO) {
        // TODO: Student implementation
        // 1. Validate the input depositDTO using validateDepositRequest.
        // 2. Delegate the call to transactionService.deposit.
        // 3. Handle specific exceptions (e.g. InvalidInputException, AccountRecordNotFoundException, IncorrectPinNumberException, AccountNotActiveException) and map them to HTTP status 400.
        // 4. Map unexpected runtime errors to HTTP status 500.
        // 5. Return response.
        DepositResponse response = new DepositResponse(depositDTO);
        try {
            validateDepositRequest(depositDTO);
            response = transactionService.deposit(depositDTO);
            response.setResponseStatus(200, "Success");

        } catch (InvalidInputException e) {
            logger.trace(e);
            response.setResponseStatus(400, "Invalid Input");
        } catch (IncorrectPinNumberException e) {
            logger.error("Incorrect PIN number");
            response.setResponseStatus(400, "Incorrect PIN number");
        } catch (AccountRecordNotFoundException e) {
            logger.error("Account record not found");
            response.setResponseStatus(400, "Account Record not found");
        } catch (AccountNotActiveException e) {
            logger.error("Account not active");
            response.setResponseStatus(400, "Account not active");
        } catch (RuntimeException e) {
            logger.error("Unexpected error");
            response.setResponseStatus(500, "Internal Server Error");
        }
        return response;
    }
}
