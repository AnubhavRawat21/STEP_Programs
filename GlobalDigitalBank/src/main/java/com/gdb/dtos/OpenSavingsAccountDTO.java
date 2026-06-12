package com.gdb.dtos;

import java.time.LocalDate;

public class OpenSavingsAccountDTO extends OpenAccountDTO {
    private LocalDate dateOfBirth;
    private GenderDTO gender;
    private String phoneNumber;
    private String aadharNumber; // https://aadhar-service.vercel.app/docs

    public OpenSavingsAccountDTO(
            String name,
            String phoneNumber,
            GenderDTO gender,
            LocalDate dateOfBirth,
            String aadharNumber,
            double initialAmount,
            PrivilegeDTO privilegeDTO,
            String pinNumber) {
        super(name, privilegeDTO, pinNumber, initialAmount);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.aadharNumber = aadharNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderDTO getGenderDTO() {
        return gender;
    }

    public void setGenderDTO(GenderDTO gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
