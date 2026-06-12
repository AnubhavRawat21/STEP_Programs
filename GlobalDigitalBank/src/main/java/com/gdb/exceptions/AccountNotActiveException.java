package com.gdb.exceptions;

/**
 * AccountNotActiveException
 * Custom exception class. This defines an Exception of Invalid DOB
 */
public class AccountNotActiveException extends Exception {
    public AccountNotActiveException() {
    }

    public AccountNotActiveException(String message) {
        super(message);
    }

    public AccountNotActiveException(String message, Exception e) {
        super(message, e);
    }
}
