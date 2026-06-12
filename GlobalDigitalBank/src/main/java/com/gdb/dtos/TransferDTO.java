package com.gdb.dtos;

/**
 * TransferDTO
 */
public class TransferDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private double transferAmount;
    private TransferModeDTO transferMode; // NEFT, IMPS, RTGS
    private String pinNumber;
    private boolean minimumBalanceWarning;
    private double newBalance;

    public TransferDTO(String fromAccountNumber, String toAccountNumber, double transferAmount,
            TransferModeDTO transferMode, String pinNumber) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.transferAmount = transferAmount;
        this.transferMode = transferMode;
        this.pinNumber = pinNumber;
        minimumBalanceWarning = false;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public void setMinimumBalanceWarning(boolean minimumBalanceWarning) {
        this.minimumBalanceWarning = minimumBalanceWarning;
    }

    public boolean getMinimumBalanceWarning() {
        return minimumBalanceWarning;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public TransferModeDTO getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(TransferModeDTO transferMode) {
        this.transferMode = transferMode;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }
}
