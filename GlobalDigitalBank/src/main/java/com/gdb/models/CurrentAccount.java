package com.gdb.models;

public class CurrentAccount extends Account{
    private String companyName;
    private String websiteURL;
    private String registrationNumber;

    public CurrentAccount(String accountNumber, String name, double initialBalance, Privilege privilege, String pinNumber, String companyName, String websiteURL, String registrationNumber) {
        super(accountNumber, name, initialBalance, privilege, pinNumber);
        this.companyName = companyName;
        this.websiteURL = websiteURL;
        this.registrationNumber = registrationNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
