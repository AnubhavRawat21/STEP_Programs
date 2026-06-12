package com.gdb.exceptions;

public class InvalidCompanyException extends Exception {
    public InvalidCompanyException() {
    }

    public InvalidCompanyException(String message) {
        super(message);
    }
}
