package com.gdb.dtos;

public class OpenCurrentAccountDTO extends OpenAccountDTO {
    private String companyName;
    private String websiteURL;
    private String registrationNumber;

    public OpenCurrentAccountDTO(
            String name,
            PrivilegeDTO privilegeDTO,
            String pinNumber,
            double initialAmount,
            String companyName,
            String websiteURL,
            String registrationNumber) {
        super(name, privilegeDTO, pinNumber, initialAmount);
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
