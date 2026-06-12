package com.gdb.exceptions;

/**
 * BlacklistedAadharOrCompanyException
 */
public class BlacklistedAadharOrCompanyException extends Exception {
    public BlacklistedAadharOrCompanyException() {
    }

    public BlacklistedAadharOrCompanyException(String message) {
        super(message);
    }

    public BlacklistedAadharOrCompanyException(String message, Exception e) {
        super(message, e);
    }
}
