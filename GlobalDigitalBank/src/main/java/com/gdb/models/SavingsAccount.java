package com.gdb.models;

import com.gdb.utils.AadharUtils;

import java.time.LocalDate;

public class SavingsAccount extends Account {
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private String aadharNumber;

    public SavingsAccount(String accountNumber, String name, double initialBalance, Privilege privilege, String pinNumber, LocalDate dateOfBirth, Gender gender, String phoneNumber, String aadharNumber) {
        super(accountNumber, name, initialBalance, privilege, pinNumber);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.aadharNumber = AadharUtils.normalizeAadhar(aadharNumber);
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = AadharUtils.normalizeAadhar(aadharNumber);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
