package com.gdb.exceptions;

public class AccountCreationException extends Exception {
    public AccountCreationException() {
    }

    public AccountCreationException(String message) {
        super(message);
    }

    public AccountCreationException(String message, Exception e) {
        super(message, e);
    }

    public static AccountCreationException wrap(Exception e) {
        if (e instanceof AccountCreationException) {
            return (AccountCreationException) e;
        }
        return new AccountCreationException(e.getMessage(), e);
    }
}