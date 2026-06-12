package com.gdb.tests.repositories;

import com.gdb.models.SavingsAccount;
import com.gdb.models.CurrentAccount;
import com.gdb.models.Privilege;
import com.gdb.models.Gender;
import com.gdb.repositories.AccountRepository;
import com.gdb.store.AccountStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private AccountRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AccountRepository();
        AccountStore.accounts.clear();
        AccountStore.blackListedAccounts.clear();
    }

    @Test
    void TestCheckIfSavingsAccountExists_FoundActive() {
        SavingsAccount sa = new SavingsAccount("1001", "User", 1000.0, Privilege.SILVER, "1234", LocalDate.of(1990,1,1), Gender.MALE, "1234567890", "123456789012");
        sa.setActive(true);
        AccountStore.accounts.add(sa);
        
        assertTrue(repository.checkIfSavingsAccountExists("123456789012"));
    }

    @Test
    void TestCheckIfSavingsAccountExists_FoundInactive() {
        SavingsAccount sa = new SavingsAccount("1001", "User", 1000.0, Privilege.SILVER, "1234", LocalDate.of(1990,1,1), Gender.MALE, "1234567890", "123456789012");
        sa.setActive(false);
        AccountStore.accounts.add(sa);
        
        assertFalse(repository.checkIfSavingsAccountExists("123456789012"));
    }

    @Test
    void TestCheckIfCurrentAccountExists_FoundActive() {
        CurrentAccount ca = new CurrentAccount("2001", "Corp", 5000.0, Privilege.GOLD, "5678", "Comp", "c.com", "REG999");
        ca.setActive(true);
        AccountStore.accounts.add(ca);
        
        assertTrue(repository.checkIfCurrentAccountExists("REG999"));
    }

    @Test
    void TestCheckIfAadharIsBlacklisted_Found() {
        SavingsAccount sa = new SavingsAccount("9999", "Black", 0.0, Privilege.PLATINUM, "0000", LocalDate.of(1980,1,1), Gender.MALE, "0000000000", "111111111111");
        AccountStore.blackListedAccounts.add(sa);
        
        assertTrue(repository.checkIfAadharIsBlacklisted("111111111111"));
    }

    @Test
    void TestCheckIfCompanyIsBlacklisted_NullRegistration() {
        assertFalse(repository.checkIfCompanyIsBlacklisted(null));
    }
}
