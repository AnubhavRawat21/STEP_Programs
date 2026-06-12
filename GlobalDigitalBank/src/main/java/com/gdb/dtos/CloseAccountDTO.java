package com.gdb.dtos;

/**
 * CloseAccountDTO
 */
public class CloseAccountDTO {
    private String accountNumber;

    public CloseAccountDTO(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
