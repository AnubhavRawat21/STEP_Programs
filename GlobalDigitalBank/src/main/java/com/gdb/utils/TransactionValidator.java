package com.gdb.utils;

import com.gdb.exceptions.InvalidInputException;

/**
 * TransactionValidator
 */
public class TransactionValidator {

    public void validateAccountNumber(String accountNumber) throws InvalidInputException {
        if (accountNumber == null || !accountNumber.matches("^\\d{4,}$")) {
            throw new InvalidInputException("Invalid Account Number format");
        }
    }

    public void validatePin(String pin) throws InvalidInputException {
        if (pin == null || !pin.matches("^\\d{4}$")) {
            throw new InvalidInputException("Invalid PIN format");
        }
    }

    public void validateWithdrawAmount(double amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Withdraw amount must be greater than zero");
        }
    }

    public void validateTransferAmount(double amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Transfer amount must be greater than zero");
        }
    }

    public void validateDepositAmount(double amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Deposit amount must be greater than zero");
        }
    }
}
