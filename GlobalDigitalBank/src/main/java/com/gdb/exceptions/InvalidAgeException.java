package com.gdb.exceptions;

/**
 * InvalidAgeException
 */
public class InvalidAgeException extends Exception {
    public InvalidAgeException() {
    }

    public InvalidAgeException(String message) {
        super(message);
    }

    public InvalidAgeException(String message, Exception e) {
        super(message, e);
    }
}
