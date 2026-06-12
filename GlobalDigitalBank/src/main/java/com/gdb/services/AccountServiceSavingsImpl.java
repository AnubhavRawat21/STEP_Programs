package com.gdb.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.gdb.apis.integrations.IAadharApiService;
import com.gdb.dtos.OpenAccountDTO;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.BlacklistedAadharOrCompanyException;
import com.gdb.exceptions.InvalidAadharException;
import com.gdb.exceptions.InvalidAgeException;
import com.gdb.models.Gender;
import com.gdb.models.Privilege;
import com.gdb.models.SavingsAccount;
import com.gdb.repositories.IAccountRepository;
import com.gdb.utils.DateUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of account opening logic for Savings Accounts.
 * This service handles validation of age, identity (Aadhar), and existing
 * records
 * before issuing a new savings account number and activating it.
 */
public class AccountServiceSavingsImpl implements IAccountServiceImpl {
    private static final Logger logger = LogManager.getLogger(AccountServiceSavingsImpl.class);
    private IAccountRepository accountRepository;
    private IAadharApiService aadharApiService;

    public AccountServiceSavingsImpl(IAccountRepository accountRepository, IAadharApiService aadharApiService) {
        this.accountRepository = accountRepository;
        this.aadharApiService = aadharApiService;
    }

    /**
     * Processes the request to open a new individual savings account.
     * 
     * @param openAccountDTO Object containing the applicant's personal and
     *                       financial data.
     * @return OpenAccountResponse containing the assigned account number and
     *         status.
     * @throws InvalidAgeException                 if the applicant is under the
     *                                             required age of 18.
     * @throws BlacklistedAadharOrCompanyException if the provided Aadhar is on the
     *                                             restricted list.
     * @throws AccountAlreadyExistsException       if an active account is already
     *                                             linked to this Aadhar.
     * @throws InvalidAadharException              if the external Aadhar
     *                                             verification service rejects the
     *                                             ID.
     */
    @Override
    public OpenAccountResponse open(OpenAccountDTO openAccountDTO) throws AccountCreationException {

        OpenSavingsAccountDTO savingsAccountDto = (OpenSavingsAccountDTO) openAccountDTO;
        logger.info("Opening savings account for: {}", savingsAccountDto.getName());

        try {
            // Verify that the applicant meets the minimum age requirement of 18 years
            checkAge(savingsAccountDto.getDateOfBirth());

            // Check the internal records to prevent duplicate active accounts for the same
            // person
            checkIfSavingsAccountExists(savingsAccountDto.getAadharNumber());

            // Ensure the applicant's identity document is not flagged in the blacklist
            checkIfAadharIsBlacklisted(savingsAccountDto.getAadharNumber());

            // Contact the external Aadhar gateway to verify the authenticity of the ID
            // number
            checkIfAadharIsValidWithApi(savingsAccountDto.getAadharNumber());

            // Obtain a unique, sequentially generated account number from the repository
            String accountNumber = generateAccountNumber();
            savingsAccountDto.setAccountNumber(accountNumber);

            // Instantiate the account domain model and set its activation timestamps
            SavingsAccount sa = createSavingsAccount(savingsAccountDto, accountNumber);
            sa.setActive(true);
            sa.setActivatedDate(LocalDateTime.now());

            // Persist the new account record to the permanent data store
            storeAccountInformation(sa);

            // Record the creation event in the system audit logs for administrative
            // tracking
            logAccountCreation(sa.getAccountNumber());
            logger.info("Savings account {} created successfully", accountNumber);

            // Return the finalized response containing the new account information
            return new OpenAccountResponse(savingsAccountDto);
        } catch (Exception e) {
            logger.error("Failed to open savings account: {}", e.getMessage());
            throw AccountCreationException.wrap(e);
        }
    }

    //
    // PRIVATE METHODS
    //
    private boolean checkAge(LocalDate dob) throws InvalidAgeException {
        if (DateUtils.calculateAge(dob) < 18) {
            logger.warn("Age validation failed for DOB: {}", dob);
            throw new InvalidAgeException("Age must be greater than 18");
        }
        return true;
    }

    private boolean checkIfSavingsAccountExists(String aadharNumber) throws AccountAlreadyExistsException {
        if (accountRepository.checkIfSavingsAccountExists(aadharNumber)) {
            logger.warn("Savings account already exists for Aadhar: {}", aadharNumber);
            throw new AccountAlreadyExistsException("Active savings account already exists for this Aadhar.");
        }
        return false;
    }

    private boolean checkIfAadharIsBlacklisted(String aadharNumber) throws BlacklistedAadharOrCompanyException {
        if (accountRepository.checkIfAadharIsBlacklisted(aadharNumber)) {
            logger.warn("Aadhar is blacklisted: {}", aadharNumber);
            throw new BlacklistedAadharOrCompanyException("Aadhar number is blacklisted.");
        }
        return false;
    }

    private boolean checkIfAadharIsValidWithApi(String aadharNumber) throws InvalidAadharException {
        if (!aadharApiService.checkIfAadharIsValid(aadharNumber)) {
            logger.warn("Aadhar API validation failed for: {}", aadharNumber);
            throw new InvalidAadharException("Invalid Aadhar number according to API.");
        }
        return true;
    }

    private String generateAccountNumber() {
        return accountRepository.generateAccountNumber();
    }

    private SavingsAccount createSavingsAccount(OpenSavingsAccountDTO dto, String accountNumber) {
        return new SavingsAccount(
                accountNumber,
                dto.getName(),
                dto.getInitialAmount(),
                Privilege.valueOf(dto.getPrivilegeDTO().name()),
                dto.getPinNumber(),
                dto.getDateOfBirth(),
                Gender.valueOf(dto.getGenderDTO().name()),
                dto.getPhoneNumber(),
                dto.getAadharNumber());
    }

    private void storeAccountInformation(SavingsAccount account) {
        accountRepository.storeAccountInformation(account);
    }

    private void logAccountCreation(String accountNumber) {
        System.out.println("Savings Account " + accountNumber + " created successfully.");
    }
}
