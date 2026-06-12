package com.gdb.tests.services;

import com.gdb.services.AccountPrivilegeManager;

import com.gdb.models.Privilege;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountPrivilegeManagerTest {

    @Test
    void TestGetMinimumBalance_Silver() {
        assertEquals(10000.0, AccountPrivilegeManager.getMinimumBalance(Privilege.SILVER));
    }

    @Test
    void TestGetMinimumBalance_Gold() {
        assertEquals(15000.0, AccountPrivilegeManager.getMinimumBalance(Privilege.GOLD));
    }

    @Test
    void TestGetMinimumBalance_Platinum() {
        assertEquals(20000.0, AccountPrivilegeManager.getMinimumBalance(Privilege.PLATINUM));
    }

    @Test
    void TestGetDailyTransferLimit_Silver() {
        assertEquals(25000.0, AccountPrivilegeManager.getDailyTransferLimit(Privilege.SILVER));
    }

    @Test
    void TestGetDailyTransferLimit_Gold() {
        assertEquals(50000.0, AccountPrivilegeManager.getDailyTransferLimit(Privilege.GOLD));
    }

    @Test
    void TestGetDailyTransferLimit_Platinum() {
        assertEquals(100000.0, AccountPrivilegeManager.getDailyTransferLimit(Privilege.PLATINUM));
    }
}
