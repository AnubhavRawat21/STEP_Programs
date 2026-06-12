package com.gdb.services;

import java.time.LocalDateTime;

import com.gdb.apis.integrations.ICompanyApiService;
import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.exceptions.BlacklistedAadharOrCompanyException;
import com.gdb.models.CurrentAccount;
import com.gdb.models.Privilege;
import com.gdb.repositories.IAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of account opening logic for Corporate Current Accounts.
 * This service handles verification of company registration and corporate
 * blacklist checks before issuing a business account number.
 */
public class AccountServiceCurrentImpl implements IAccountServiceImpl {
    private static final Logger logger = LogManager.getLogger(AccountServiceCurrentImpl.class);
    private IAccountRepository accountRepository;
    private ICompanyApiService companyApiService;

    public AccountServiceCurrentImpl(IAccountRepository accountRepository, ICompanyApiService companyApiService) {
        this.accountRepository = accountRepository;
        this.companyApiService = companyApiService;
    }

    /**
     * Processes the request to open a new corporate current account.
     * 
     * @param openAccountDTO Object containing the company's registration and
     *                       financial data.
     * @return OpenAccountResponse containing the assigned business account number.
     * @throws AccountAlreadyExistsException       if an active account exists for
     *                                             the company registration.
     * @throws BlacklistedAadharOrCompanyException if the company is on the
     *                                             restricted list.
     */
    @Override
    public OpenAccountResponse open(OpenAccountDTO openAccountDTO)
            throws AccountCreationException, AccountAlreadyExistsException, BlacklistedAadharOrCompanyException {

        OpenCurrentAccountDTO currentAccountDto = (OpenCurrentAccountDTO) openAccountDTO;
        logger.info("Opening current account for company: {}", currentAccountDto.getCompanyName());

        try {
            // Verify that no other active current account is using this registration number
            checkIfCurrentAccountExists(currentAccountDto.getRegistrationNumber());

            // Ensure the company or its registration ID is not flagged in the corporate
            // blacklist
            checkIfCompanyIsBlacklisted(currentAccountDto.getRegistrationNumber());

            // Contact the external Company API to verify the validity of the
            // registration number
            checkIfCompanyIsValidWithApi(currentAccountDto.getRegistrationNumber());

            // Generate a unique identifier for the new corporate account
            String accountNumber = generateAccountNumber();
            currentAccountDto.setAccountNumber(accountNumber);

            // Create the corporate account entity and record the activation timestamp
            CurrentAccount ca = createCurrentAccount(currentAccountDto, accountNumber);
            ca.setActive(true);
            ca.setActivatedDate(LocalDateTime.now());

            // Persist to DB
            storeAccountInformation(ca);

            // Log the account creation for system auditing and reporting
            logAccountCreation(ca.getAccountNumber());
            logger.info("Current account {} created successfully", accountNumber);

            // Return the response with the company's new account details
            return new OpenAccountResponse(currentAccountDto);
        } catch (Exception e) {
            logger.error("Failed to open current account: {}", e.getMessage());
            throw AccountCreationException.wrap(e);
        }
    }

    //
    // PRIVATE METHODS
    //

    private boolean checkIfCurrentAccountExists(String registrationNumber) throws AccountAlreadyExistsException {
        if (accountRepository.checkIfCurrentAccountExists(registrationNumber)) {
            logger.warn("Current account already exists for registration: {}", registrationNumber);
            throw new AccountAlreadyExistsException(
                    "Active current account already exists for this registration number.");
        }
        return false;
    }

    private boolean checkIfCompanyIsBlacklisted(String registrationNumber) throws BlacklistedAadharOrCompanyException {
        if (accountRepository.checkIfCompanyIsBlacklisted(registrationNumber)) {
            logger.warn("Company is blacklisted: {}", registrationNumber);
            throw new BlacklistedAadharOrCompanyException("Company registration number is blacklisted.");
        }
        return false;
    }

    private boolean checkIfCompanyIsValidWithApi(String registrationNumber) {
        if (!companyApiService.checkIfCompanyRegistrationIsValid(registrationNumber)) {
            logger.warn("Company API validation failed for: {}", registrationNumber);
            throw new RuntimeException("Invalid company registration number according to API.");
        }
        return true;
    }

    private String generateAccountNumber() {
        return accountRepository.generateAccountNumber();
    }

    private CurrentAccount createCurrentAccount(OpenCurrentAccountDTO dto, String accountNumber) {
        return new CurrentAccount(
                accountNumber,
                dto.getName(),
                dto.getInitialAmount(),
                Privilege.valueOf(dto.getPrivilegeDTO().name()),
                dto.getPinNumber(),
                dto.getCompanyName(),
                dto.getWebsiteURL(),
                dto.getRegistrationNumber());
    }

    private void storeAccountInformation(CurrentAccount account) {
        accountRepository.storeAccountInformation(account);
    }

    private void logAccountCreation(String accountNumber) {
        System.out.println("Current Account " + accountNumber + " created successfully.");
    }
}
