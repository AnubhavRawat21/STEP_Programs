package com.gdb.exceptions;

/**
 * AccountRecordNotFoundException
 * Custom exception class. This defines an Exception of Invalid DOB
 */
public class AccountRecordNotFoundException extends Exception {
    public AccountRecordNotFoundException() {
    }

    public AccountRecordNotFoundException(String message) {
        super(message);
    }

    public AccountRecordNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
