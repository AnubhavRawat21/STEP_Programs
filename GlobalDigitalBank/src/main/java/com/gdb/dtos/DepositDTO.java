package com.gdb.dtos;

/**
 * DepositDTO
 */
public class DepositDTO {
    private String accountNumber;
    private double depositAmount;
    private String pinNumber;
    private double newBalance;
    private double originalBalance;

    public DepositDTO(String accountNumber, double depositAmount, String pinNumber) {
        this.accountNumber = accountNumber;
        this.depositAmount = depositAmount;
        this.pinNumber = pinNumber;
        newBalance = 0;
        originalBalance = 0;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

}
