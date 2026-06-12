package com.gdb.tests.models;

import com.gdb.models.CurrentAccount;
import com.gdb.models.Privilege;
import com.gdb.models.SavingsAccount;
import com.gdb.models.Gender;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AccountModelsTest {

    @Test
    void TestCurrentAccount_GettersSetters() {
        CurrentAccount ca = new CurrentAccount("1001", "Corp", 5000.0, Privilege.GOLD, "1234", "CorpName", "http://c.com", "REG123");
        ca.setActive(true);
        assertTrue(ca.isActive());
        assertEquals("CorpName", ca.getCompanyName());
        assertEquals("REG123", ca.getRegistrationNumber());
    }

    @Test
    void TestSavingsAccount_GettersSetters() {
        SavingsAccount sa = new SavingsAccount("1002", "User", 5000.0, Privilege.SILVER, "1234", LocalDate.of(1990, 1, 1), Gender.FEMALE, "9999999999", "123456789012");
        sa.setAadharNumber("999999999999");
        assertEquals("999999999999", sa.getAadharNumber());
        assertEquals(Gender.FEMALE, sa.getGender());
    }
}
