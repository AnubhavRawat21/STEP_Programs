package com.gdb.exceptions;

/**
 * InvalidDOBException
 * Custom exception class. This defines an Exception of Invalid DOB
 */
public class InvalidDOBException extends Exception {
    public InvalidDOBException() {
    }

    public InvalidDOBException(String message) {
        super(message);
    }

    public InvalidDOBException(String message, Exception e) {
        super(message, e);
    }
}
