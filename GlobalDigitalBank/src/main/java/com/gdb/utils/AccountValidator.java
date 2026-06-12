package com.gdb.utils;

import com.gdb.dtos.PrivilegeDTO;
import com.gdb.exceptions.InvalidInputException;
import com.gdb.models.Privilege;
import com.gdb.services.AccountPrivilegeManager;

/**
 * AccountValidator
 */
public class AccountValidator {

    public void validateName(String name) throws InvalidInputException {
        if (name == null || name.length() < 3 || !name.matches("[a-zA-Z .]+")) {
            throw new InvalidInputException(
                    "Name must be at least 3 characters and contain only letters, dots, or spaces");
        }
    }

    public void validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (phoneNumber == null || !phoneNumber.matches("^\\d{10}$")) {
            throw new InvalidInputException("Phone number must be exactly 10 digits");
        }
    }

    public void validateAadharNumber(String aadharNumber) throws InvalidInputException {
        if (aadharNumber == null || !aadharNumber.matches("^\\d{12}$")) {
            throw new InvalidInputException("Aadhar number must be exactly 12 digits");
        }
    }

    public void validatePin(String pin) throws InvalidInputException {
        if (pin == null || !pin.matches("^\\d{4}$")) {
            throw new InvalidInputException("PIN must be exactly 4 digits");
        }
    }

    public void validateInitialAmount(double amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Initial amount must be greater than zero");
        }
    }

    public void validateCompanyName(String companyName) throws InvalidInputException {
        if (companyName == null || companyName.length() < 3 || !companyName.matches("[a-zA-Z0-9 .]+")) {
            throw new InvalidInputException("Company name must be at least 3 characters");
        }
    }

    public void validateCompanyRegistrationNumber(String regNum) throws InvalidInputException {
        if (regNum == null || regNum.trim().isEmpty() || !regNum.trim().matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidInputException(
                    "Invalid Company Registration Number format.");
        }
    }

    public void validateWebsiteURL(String url) throws InvalidInputException {
        if (url == null || url.trim().isEmpty()) {
            return;
        }
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            throw new InvalidInputException("Website URL must start with http:// or https://");
        }
    }

    public void validateMinimumBalance(PrivilegeDTO privilegeDto, double amount) throws InvalidInputException {
        if (privilegeDto == null) {
            throw new InvalidInputException("Privilege must be specified");
        }

        Privilege privilege = Privilege.valueOf(privilegeDto.name());
        double minBalance = AccountPrivilegeManager.getMinimumBalance(privilege);

        if (amount < minBalance) {
            throw new InvalidInputException(
                    "Initial amount for " + privilegeDto + " tier must be at least Rs. " + minBalance);
        }
    }

    public void validateAccountNumber(String accountNumber) throws InvalidInputException {
        if (accountNumber == null || !accountNumber.matches("^\\d{4,}$")) {
            throw new InvalidInputException("Invalid Account Number format");
        }
    }
}
