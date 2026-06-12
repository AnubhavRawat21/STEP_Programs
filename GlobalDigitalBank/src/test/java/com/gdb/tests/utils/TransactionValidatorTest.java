package com.gdb.tests.utils;

import com.gdb.exceptions.InvalidInputException;
import com.gdb.utils.TransactionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {

    private TransactionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TransactionValidator();
    }

    @Test
    void TestValidateAccountNumber_Valid() {
        assertDoesNotThrow(() -> validator.validateAccountNumber("1001"));
    }

    @Test
    void TestValidateAccountNumber_Null() {
        assertThrows(InvalidInputException.class, () -> validator.validateAccountNumber(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "ABCD", "12 34", "12A"})
    void TestValidateAccountNumber_Invalid(String accNum) {
        assertThrows(InvalidInputException.class, () -> validator.validateAccountNumber(accNum));
    }

    @Test
    void TestValidateWithdrawAmount_Valid() {
        assertDoesNotThrow(() -> validator.validateWithdrawAmount(100.0));
    }

    @Test
    void TestValidateWithdrawAmount_Zero() {
        assertThrows(InvalidInputException.class, () -> validator.validateWithdrawAmount(0.0));
    }

    @Test
    void TestValidateWithdrawAmount_Negative() {
        assertThrows(InvalidInputException.class, () -> validator.validateWithdrawAmount(-0.01));
    }

    @Test
    void TestValidateWithdrawAmount_LargeValue() {
        assertDoesNotThrow(() -> validator.validateWithdrawAmount(1000000.0));
    }
}
