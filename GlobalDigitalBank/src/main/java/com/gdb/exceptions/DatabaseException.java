package com.gdb.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Exception cause) {
        super(message, cause);
    }
}