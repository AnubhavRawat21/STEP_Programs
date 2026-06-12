package com.gdb.exceptions;

/**
 * DailyTransferLimitExceededException
 */
public class DailyTransferLimitExceededException extends Exception {
    public DailyTransferLimitExceededException(String message) {
        super(message);
    }
}
