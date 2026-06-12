package com.gdb.dtos;

public abstract class OpenAccountDTO {
    private String accountNumber;
    private String name;
    private PrivilegeDTO privilegeDTO;
    private String pinNumber;
    private double initialAmount;

    public OpenAccountDTO(String name, PrivilegeDTO privilegeDTO, String pinNumber, double initialAmount) {
        this.name = name;
        this.privilegeDTO = privilegeDTO;
        this.pinNumber = pinNumber;
        this.initialAmount = initialAmount;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrivilegeDTO getPrivilegeDTO() {
        return privilegeDTO;
    }

    public void setPrivilegeDTO(PrivilegeDTO privilegeDTO) {
        this.privilegeDTO = privilegeDTO;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }
}
