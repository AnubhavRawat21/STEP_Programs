package com.gdb.exceptions;

/**
 * InsufficientFundsException
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Exception e) {
        super(message, e);
    }
}
