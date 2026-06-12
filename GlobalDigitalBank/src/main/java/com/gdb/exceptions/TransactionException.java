package com.gdb.exceptions;

public class TransactionException extends Exception {
    public TransactionException() {
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Exception e) {
        super(message, e);
    }

    public static TransactionException wrap(Exception e) {
        if (e instanceof TransactionException) {
            return (TransactionException) e;
        }
        return new TransactionException(e.getMessage(), e);
    }
}