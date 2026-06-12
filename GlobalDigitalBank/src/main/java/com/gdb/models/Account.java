package com.gdb.models;

import java.time.LocalDateTime;

/**
 * Account
 * Entity Class
 */
public abstract class Account {
    private int id;
    private String accountNumber;
    private String name;
    private double balance;
    private String pinNumber;
    private boolean isActive;
    private Privilege privilege;
    private LocalDateTime activatedDate;
    private LocalDateTime closedDate;

    public Account(
            String accountNumber,
            String name,
            double initialBalance,
            Privilege privilege,
            String pinNumber) {
        this.pinNumber = pinNumber;
        this.name = name;
        this.accountNumber = accountNumber;
        this.privilege = privilege;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(LocalDateTime activatedDate) {
        this.activatedDate = activatedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }
}
