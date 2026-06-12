package com.gdb.tests.utils;

import com.gdb.dtos.PrivilegeDTO;
import com.gdb.exceptions.InvalidInputException;
import com.gdb.utils.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorTest {

    private AccountValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AccountValidator();
    }

    @Test
    void TestValidateName_ValidName() {
        assertDoesNotThrow(() -> validator.validateName("John Doe"));
    }

    @Test
    void TestValidateName_ExactlyThreeChars() {
        assertDoesNotThrow(() -> validator.validateName("ABC"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "Jo", "", "  ", "John123", "John@Doe" })
    void TestValidateName_InvalidNames(String name) {
        assertThrows(InvalidInputException.class, () -> validator.validateName(name));
    }

    @Test
    void TestValidateName_Null() {
        assertThrows(InvalidInputException.class, () -> validator.validateName(null));
    }

    @Test
    void TestValidatePhoneNumber_Valid() {
        assertDoesNotThrow(() -> validator.validatePhoneNumber("9876543210"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "123456789", "12345678901", "abcdefghij", "123-456-7890" })
    void TestValidatePhoneNumber_Invalid(String phone) {
        assertThrows(InvalidInputException.class, () -> validator.validatePhoneNumber(phone));
    }

    @Test
    void TestValidateInitialAmount_Positive() {
        assertDoesNotThrow(() -> validator.validateInitialAmount(100.0));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.0, -1.0, -100.5 })
    void TestValidateInitialAmount_NonPositive(double amount) {
        assertThrows(InvalidInputException.class, () -> validator.validateInitialAmount(amount));
    }

    @Test
    void TestValidateWebsiteURL_ValidHttp() {
        assertDoesNotThrow(() -> validator.validateWebsiteURL("http://google.com"));
    }

    @Test
    void TestValidateWebsiteURL_ValidHttps() {
        assertDoesNotThrow(() -> validator.validateWebsiteURL("https://google.com"));
    }

    @Test
    void TestValidateWebsiteURL_InvalidProtocol() {
        assertThrows(InvalidInputException.class, () -> validator.validateWebsiteURL("ftp://google.com"));
    }

    @Test
    void TestValidateWebsiteURL_Null() {
        assertDoesNotThrow(() -> validator.validateWebsiteURL(null));
    }

    @Test
    void TestValidateWebsiteURL_Empty() {
        assertDoesNotThrow(() -> validator.validateWebsiteURL(""));
    }

    @Test
    void TestValidateAadharNumber_Valid() {
        assertDoesNotThrow(() -> validator.validateAadharNumber("123456789012"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345678901", "1234567890123", "abcde1234567", "12345678901" })
    void TestValidateAadharNumber_Invalid(String aadhar) {
        assertThrows(InvalidInputException.class, () -> validator.validateAadharNumber(aadhar));
    }

    @Test
    void TestValidateAadharNumber_Null() {
        assertThrows(InvalidInputException.class, () -> validator.validateAadharNumber(null));
    }

    @Test
    void TestValidatePin_Valid() {
        assertDoesNotThrow(() -> validator.validatePin("1234"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "123", "12345", "abcd", "12-3" })
    void TestValidatePin_Invalid(String pin) {
        assertThrows(InvalidInputException.class, () -> validator.validatePin(pin));
    }

    @Test
    void TestValidatePin_Null() {
        assertThrows(InvalidInputException.class, () -> validator.validatePin(null));
    }

    @Test
    void TestValidateCompanyName_Valid() {
        assertDoesNotThrow(() -> validator.validateCompanyName("ABC Corp"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "AB", "", "ABC!", "123$" })
    void TestValidateCompanyName_Invalid(String name) {
        assertThrows(InvalidInputException.class, () -> validator.validateCompanyName(name));
    }

    @Test
    void TestValidateCompanyName_Null() {
        assertThrows(InvalidInputException.class, () -> validator.validateCompanyName(null));
    }

    @Test
    void TestValidateCompanyRegistrationNumber_Valid() {
        assertDoesNotThrow(() -> validator.validateCompanyRegistrationNumber("ABC123DEF4"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "ABC!", "123 456" })
    void TestValidateCompanyRegistrationNumber_Invalid(String regNum) {
        assertThrows(InvalidInputException.class, () -> validator.validateCompanyRegistrationNumber(regNum));
    }

    @Test
    void TestValidateCompanyRegistrationNumber_Null() {
        assertThrows(InvalidInputException.class, () -> validator.validateCompanyRegistrationNumber(null));
    }

    @Test
    void TestValidateMinimumBalance_AtMin() {
        // Silver min is 10000.0
        assertDoesNotThrow(() -> validator.validateMinimumBalance(PrivilegeDTO.SILVER, 10000.0));
    }

    @Test
    void TestValidateMinimumBalance_JustBelowMin() {
        assertThrows(InvalidInputException.class, () -> validator.validateMinimumBalance(PrivilegeDTO.SILVER, 9999.99));
    }

    @Test
    void TestValidateMinimumBalance_NullPrivilegeDTO() {
        assertThrows(InvalidInputException.class, () -> validator.validateMinimumBalance(null, 100.0));
    }
}
