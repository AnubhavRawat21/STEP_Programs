package com.gdb.models;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private TransactionType type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String description;

    public Transaction(TransactionType type, double amount, double balanceAfter, LocalDateTime timestamp,
            String description) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp;
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
