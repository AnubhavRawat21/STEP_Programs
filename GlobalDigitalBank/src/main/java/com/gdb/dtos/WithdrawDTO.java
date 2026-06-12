package com.gdb.dtos;

/**
 * WithdrawDTO
 */
public class WithdrawDTO {
    private String accountNumber;
    private double withdrawAmount;
    private String pinNumber;
    private boolean minimumBalanceWarning;
    private double newBalance;
    private double originalBalance;

    public WithdrawDTO(String accountNumber, double withdrawAmount, String pinNumber) {
        this.accountNumber = accountNumber;
        this.withdrawAmount = withdrawAmount;
        this.pinNumber = pinNumber;
        newBalance = 0;
        originalBalance = 0;
        minimumBalanceWarning = false;
    }

    public void setOriginalBalance(double originalBalance) {
        this.originalBalance = originalBalance;
    }

    public double getOriginalBalance() {
        return originalBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setMinimumBalanceWarning(boolean shouldWarnUser) {
        this.minimumBalanceWarning = shouldWarnUser;
    }

    public boolean getMinimumBalanceWarning() {
        return minimumBalanceWarning;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

}
