package com.gdb.tests.repositories;

import com.gdb.repositories.TransactionRepository;

import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.models.Account;
import com.gdb.models.SavingsAccount;
import com.gdb.models.Privilege;
import com.gdb.models.Gender;
import com.gdb.store.AccountStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TransactionRepository();
        AccountStore.accounts.clear();
    }

    @Test
    void TestCalculateNewBalance_Debit() throws Exception {
        Method method = TransactionRepository.class.getDeclaredMethod("calculateNewBalance", double.class, double.class, boolean.class);
        method.setAccessible(true);
        double result = (double) method.invoke(repository, 1000.0, 200.0, true);
        assertEquals(800.0, result);
    }

    @Test
    void TestCalculateNewBalance_Credit() throws Exception {
        Method method = TransactionRepository.class.getDeclaredMethod("calculateNewBalance", double.class, double.class, boolean.class);
        method.setAccessible(true);
        double result = (double) method.invoke(repository, 1000.0, 200.0, false);
        assertEquals(1200.0, result);
    }

    @Test
    void TestFindAccountByAccountNumber_Existing() throws Exception {
        SavingsAccount account = new SavingsAccount("1001", "Test User", 5000.0, Privilege.SILVER, "1234", LocalDate.of(1990, 1, 1), Gender.MALE, "9876543210", "123456789012");
        AccountStore.accounts.add(account);

        Method method = TransactionRepository.class.getDeclaredMethod("findAccountByAccountNumber", String.class);
        method.setAccessible(true);
        Account found = (Account) method.invoke(repository, "1001");

        assertNotNull(found);
        assertEquals("1001", found.getAccountNumber());
    }

    @Test
    void TestFindAccountByAccountNumber_NonExistent() throws Exception {
        Method method = TransactionRepository.class.getDeclaredMethod("findAccountByAccountNumber", String.class);
        method.setAccessible(true);
        
        try {
            method.invoke(repository, "999999");
            fail("Should have thrown AccountRecordNotFoundException");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof AccountRecordNotFoundException);
        }
    }

    @Test
    void TestDepositAmount_Success() throws Exception {
        SavingsAccount account = new SavingsAccount("1001", "Test User", 5000.0, Privilege.SILVER, "1234", LocalDate.of(1990, 1, 1), Gender.MALE, "9876543210", "123456789012");
        AccountStore.accounts.add(account);

        double newBalance = repository.depositAmount("1001", 1000.0);
        assertEquals(6000.0, newBalance);
        assertEquals(6000.0, account.getBalance());
    }

    @Test
    void TestDepositAmount_AccountNotFound() {
        assertThrows(AccountRecordNotFoundException.class, () -> repository.depositAmount("9999", 1000.0));
    }
}
