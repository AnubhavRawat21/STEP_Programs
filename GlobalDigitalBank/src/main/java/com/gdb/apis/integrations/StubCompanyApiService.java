package com.gdb.apis.integrations;

import java.util.Arrays;
import java.util.List;

public class StubCompanyApiService implements ICompanyApiService {

    // Valid company registration numbers from the API mock
    private static final List<String> VALID_COMPANY_REGISTRATION_NUMBERS = Arrays.asList(
            "L01111TN2022PLC444333",
            "L10101UP2022PLC000999",
            "L15142UP2020PLC012345",
            "L17110WB2018PLC666777",
            "L18101MH2020PLC222333",
            "L22213WB2023PLC666555",
            "L24233WB2017PLC678901",
            "L27100TN2019PLC444555",
            "L28920AP2020PLC888999",
            "L29130AP2019PLC890123",
            "L32109UP2019PLC000111",
            "L36911AP2021PLC888777",
            "L67120MH2019PLC234567",
            "L74140MH2021PLC222111",
            "L85110TN2018PLC456789",
            "U45200HR2023PTC789012",
            "U46100DL2022PTC333444",
            "U47910HR2020PTC777666",
            "U51909GJ2022PTC567890",
            "U52100HR2021PTC777888",
            "U55101KA2023PTC111000",
            "U58182RJ2023PTC999888",
            "U62013RJ2021PTC901234",
            "U64200GJ2023PTC555666",
            "U68100GJ2019PTC555444",
            "U70100RJ2022PTC999000",
            "U72900KA2020PTC123456",
            "U74999DL2021PTC345678",
            "U86101DL2020PTC333222",
            "U93030KA2021PTC111222"
    );

    @Override
    public boolean checkIfCompanyRegistrationIsValid(String companyRegistrationNumber) {
        if (companyRegistrationNumber == null) {
            return false;
        }
        String normalized = companyRegistrationNumber.trim().toUpperCase();
        return VALID_COMPANY_REGISTRATION_NUMBERS.contains(normalized);
    }
}