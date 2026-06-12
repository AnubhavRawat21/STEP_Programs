package com.gdb.tests.utils;

import com.gdb.models.Account;
import com.gdb.models.CurrentAccount;
import com.gdb.models.Gender;
import com.gdb.models.Privilege;
import com.gdb.models.SavingsAccount;
import com.gdb.store.AccountStore;
import com.gdb.store.TransactionLogStore;
import com.gdb.utils.DataSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DataSeederTest {

    @BeforeEach
    void setUp() {
        AccountStore.accounts.clear();
        AccountStore.blackListedAccounts.clear();
        TransactionLogStore.logs.clear();
    }

    @Test
    void testSeed_SmallCount() {
        DataSeeder.seed(10);
        assertEquals(10, AccountStore.accounts.size(), "Should create 10 accounts");
    }

    @Test
    void testSeed_CreatesSavingsAccount() {
        DataSeeder.seed(3);
        // i=1 (index 0): 1 % 2 != 0 -> SavingsAccount
        Account account = AccountStore.accounts.get(0);
        assertTrue(account instanceof SavingsAccount, "Index 0 should be SavingsAccount");
    }

    @Test
    void testSeed_CreatesCurrentAccount() {
        DataSeeder.seed(4);
        // i=2 (index 1): 2 % 2 == 0 -> CurrentAccount
        Account account = AccountStore.accounts.get(1);
        assertTrue(account instanceof CurrentAccount, "Index 1 should be CurrentAccount");
    }

    @Test
    void testSeed_PreservesExistingData() {
        AccountStore.accounts.add(new SavingsAccount("12345", "Test", 1000, Privilege.SILVER, "1234", LocalDate.now(),
                Gender.MALE, "123456789012", "987654321098"));
        DataSeeder.seed(1);
        assertEquals(2, AccountStore.accounts.size(), "Store should preserve existing data");
    }

    @Test
    void testSeed_GeneratesTransactionsForFirst1000Accounts() {
        DataSeeder.seed(1001);
        String accNum = AccountStore.accounts.get(0).getAccountNumber();
        assertTrue(TransactionLogStore.logs.containsKey(accNum),
                "Transactions should be generated for accounts <= 1000");
        String accNum1001 = AccountStore.accounts.get(1000).getAccountNumber();
        assertFalse(TransactionLogStore.logs.containsKey(accNum1001),
                "Transactions should NOT be generated for accounts > 1000");
    }

    @Test
    void testSeed_SetsActivationDate() {
        DataSeeder.seed(1);
        Account account = AccountStore.accounts.get(0);
        assertNotNull(account.getActivatedDate(), "Activated date should be set");
    }

    @Test
    void testSeed_SetsInactiveAccountClosedDate() {
        DataSeeder.seed(1);
        Account account = AccountStore.accounts.get(0);
        if (!account.isActive()) {
            assertNotNull(account.getClosedDate(), "Closed date should be set for inactive accounts");
        }
    }

    @Test
    void testSeed_ZeroCount() {
        DataSeeder.seed(0);
        assertTrue(AccountStore.accounts.isEmpty(), "Should handle zero count");
    }
}
