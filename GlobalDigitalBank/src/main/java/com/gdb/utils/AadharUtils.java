package com.gdb.utils;

/**
 * AadharUtils
 */
public class AadharUtils {
    public static String normalizeAadhar(String aadharNumber) {
        if (aadharNumber == null) return null;
        return aadharNumber.replaceAll("\\s+", "");
    }
}
