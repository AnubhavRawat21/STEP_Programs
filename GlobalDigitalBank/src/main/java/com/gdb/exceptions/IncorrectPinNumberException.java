package com.gdb.exceptions;

/**
 * IncorrectPinNumberException
 */
public class IncorrectPinNumberException extends Exception {
    public IncorrectPinNumberException() {
    }

    public IncorrectPinNumberException(String message) {
        super(message);
    }

    public IncorrectPinNumberException(String message, Exception e) {
        super(message, e);
    }
}
