package com.gdb.apis.integrations;

import com.gdb.utils.AadharUtils;

import java.util.Arrays;
import java.util.List;

public class StubAadharApiService implements IAadharApiService {

    // Valid Aadhar numbers from the API mock
    private static final List<String> VALID_AADHAR_NUMBERS = Arrays.asList(
            "600000000003", "600000000009", "600000000011", "600000000021", "600000000028",
            "600000000060", "600000000072", "600000000089", "600000000103", "600000000114",
            "810000000000", "810000000007", "810000000014", "810000000021", "810000000028",
            "810000000035", "810000000042", "810000000049", "810000000056", "810000000063");

    @Override
    public boolean checkIfAadharIsValid(String aadharNumber) {
        if (aadharNumber == null) {
            return false;
        }
        // Normalize input before checking against the list
        String normalized = AadharUtils.normalizeAadhar(aadharNumber);
        return VALID_AADHAR_NUMBERS.contains(normalized);
    }
}
