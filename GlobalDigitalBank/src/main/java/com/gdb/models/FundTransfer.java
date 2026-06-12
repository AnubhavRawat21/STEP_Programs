package com.gdb.models;

/**
 * FundTransfer
 */
public class FundTransfer {
    private Account fromAccount;
    private Account toAccount;
    private double transferAmount;
    private TransferMode transferMode;

    public FundTransfer(Account fromAccount, Account toAccount, double transferAmount, TransferMode transferMode){
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transferAmount = transferAmount;
        this.transferMode = transferMode;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public TransferMode getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(TransferMode transferMode) {
        this.transferMode = transferMode;
    }
}
