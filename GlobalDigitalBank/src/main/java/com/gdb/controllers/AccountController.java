package com.gdb.controllers;

import com.gdb.services.AccountService;
import com.gdb.services.IAccountService;
import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.dtos.CloseAccountDTO;
import com.gdb.dtos.responses.CloseAccountResponse;
import com.gdb.exceptions.*;
import com.gdb.utils.AccountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.lang.model.type.NullType;
import java.util.InputMismatchException;

/**
 * Controller responsible for handling all account-related operations.
 * Acts as the entry point for the UI, performing initial request validation
 * and routing valid requests to the underlying Account Service.
 */
public class AccountController implements IAccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);
    private IAccountService accountService;
    private AccountValidator validator;

    public AccountController(IAccountService accountService, AccountValidator validator) {
        this.accountService = accountService;
        this.validator = validator;
    }

    /**
     * Entry point for opening a new account (Savings or Current).
     * 
     * @param openAccountDto Object containing the requested account configuration.
     * @return OpenAccountResponse indicating the HTTP status and outcome message.
     */
    @Override
    public OpenAccountResponse openAccount(OpenAccountDTO openAccountDto) {

        /*
         * Account Controller Validation:
         *
         * 1. Validate Account information.
         * 2. If there is an error, we send the message back along with the status.
         * 3. Send account to account service.
         */
        logger.info("Controller: Received request to open account for {}", openAccountDto.getName());
        try {
            validateAccountInformation(openAccountDto);
        } catch (InvalidInputException e) {
            logger.warn("Controller: Account validation failed: {}", e.getMessage());
            OpenAccountResponse response = new OpenAccountResponse(openAccountDto);
            response.setResponseStatus(400, "Validation Error: " + e.getMessage());
            return response;
        }

        OpenAccountResponse response = null;
        try {
            response = accountService.openAccount(openAccountDto);
            response.setResponseStatus(200, "Account Opened Successfully");
            logger.info("Controller: Account opened successfully for {}", openAccountDto.getName());
        } catch (AccountCreationException e) {
            logger.error("Controller: Account opening failed: {}", e.getMessage());
            response = new OpenAccountResponse(openAccountDto);
            response.setResponseStatus(400, "Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Controller: Unexpected error during account opening", e);
            response = new OpenAccountResponse(openAccountDto);
            response.setResponseStatus(500, "Internal Server Error: " + e.getMessage());
        }

        return response;
    }

    protected void validateAccountInformation(OpenAccountDTO openAccountDto) throws InvalidInputException {
        if (openAccountDto == null) {
            throw new InvalidInputException("Account details cannot be null");
        }

        // Base validations
        validator.validateName(openAccountDto.getName());
        validator.validatePin(openAccountDto.getPinNumber());
        validator.validateInitialAmount(openAccountDto.getInitialAmount());
        validator.validateMinimumBalance(openAccountDto.getPrivilegeDTO(),
                openAccountDto.getInitialAmount());

        if (openAccountDto instanceof OpenSavingsAccountDTO) {
            var savings = (OpenSavingsAccountDTO) openAccountDto;
            validator.validatePhoneNumber(savings.getPhoneNumber());
            validator.validateAadharNumber(savings.getAadharNumber());
        } else if (openAccountDto instanceof OpenCurrentAccountDTO) {
            var current = (OpenCurrentAccountDTO) openAccountDto;
            validator.validateCompanyName(current.getCompanyName());
            validator.validateCompanyRegistrationNumber(current.getRegistrationNumber());
            validator.validateWebsiteURL(current.getWebsiteURL());
        }
    }

    @Override
    public CloseAccountResponse closeAccount(CloseAccountDTO closeAccountDto) {
        logger.info("Controller: Received request to close account {}",
                closeAccountDto != null ? closeAccountDto.getAccountNumber() : "null");

        // TODO: Student implementation
        // 1. Validate the DTO is not null and validate the account number format using validator.validateAccountNumber(...)
        // 2. Call accountService.closeAccount(closeAccountDto)
        // 3. Catch exceptions: InvalidInputException, AccountRecordNotFoundException,
        //    AccountNotActiveException and return a response with HTTP 400 and the exception message.
        // 4. Catch general Exception and return status 500.
        try {
            if (closeAccountDto == null) {
                logger.error("Account object is NULL");
                throw new InputMismatchException("A/C number is null");
            }
            validator.validateAccountNumber(closeAccountDto.getAccountNumber());
        } catch (InvalidInputException e) {
            logger.error("A/c is Null");
            CloseAccountResponse response = new CloseAccountResponse(closeAccountDto);
            response.setResponseStatus(400, "Account Number doesn't exist");
        }
        try {
            CloseAccountResponse response = null;
            response = accountService.closeAccount(closeAccountDto);
            response.setResponseStatus(200, "Account Closed Successfully");
            logger.info("A/c is closed");
        }
        catch (AccountNotActiveException e){
            CloseAccountResponse response = new CloseAccountResponse(closeAccountDto);
            response.setResponseStatus(400, "Account Number is inactive");
        }
        catch (AccountRecordNotFoundException e){
            CloseAccountResponse response = new CloseAccountResponse(closeAccountDto);
            response.setResponseStatus(400, "Account Record not found");
        }
        CloseAccountResponse response = new CloseAccountResponse(closeAccountDto);
        response.setResponseStatus(200, "Mock Success");
        return response;
    }
}
