package com.gdb.tests.services;

import com.gdb.models.Account;
import com.gdb.models.FundTransfer;
import com.gdb.models.Gender;
import com.gdb.models.Privilege;
import com.gdb.models.SavingsAccount;
import com.gdb.models.TransactionType;
import com.gdb.models.TransferMode;
import com.gdb.services.TransactionLogService;
import com.gdb.store.TransactionLogStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionLogServiceTest {

    private TransactionLogService service;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        service = new TransactionLogService();
        TransactionLogStore.logs.clear();
        fromAccount = new SavingsAccount("10001", "Sender", 50000, Privilege.SILVER, "1234", LocalDate.now(),
                Gender.MALE, "123456789012", "987654321098");
        toAccount = new SavingsAccount("10002", "Receiver", 10000, Privilege.SILVER, "1234", LocalDate.now(),
                Gender.FEMALE, "223456789012", "887654321098");
    }

    @Test
    void testLogTransfer_Success() {
        FundTransfer transfer = new FundTransfer(fromAccount, toAccount, 1000, TransferMode.IMPS);

        service.logTransfer(transfer, 49000, 11000);

        assertTrue(TransactionLogStore.logs.containsKey("10001"));
        assertTrue(TransactionLogStore.logs.containsKey("10002"));
    }

    @Test
    void testLogTransfer_CreatesTransferOutAndIn() {
        FundTransfer transfer = new FundTransfer(fromAccount, toAccount, 500, TransferMode.RTGS);

        service.logTransfer(transfer, 49500, 10500);

        LocalDate today = LocalDate.now();
        var fromLogs = TransactionLogStore.logs.get("10001").get(today);
        assertTrue(fromLogs.containsKey(TransactionType.TRANSFER_OUT));

        var toLogs = TransactionLogStore.logs.get("10002").get(today);
        assertTrue(toLogs.containsKey(TransactionType.TRANSFER_IN));
    }

    @Test
    void testLogWithdraw_Success() {
        service.logWithdraw("10001", 500, 49500);

        assertTrue(TransactionLogStore.logs.containsKey("10001"));
    }

    @Test
    void testGetDailyTransferTotal_AccountExists() {
        FundTransfer transfer = new FundTransfer(fromAccount, toAccount, 200, TransferMode.IMPS);
        service.logTransfer(transfer, 49800, 10200);

        double total = service.getDailyTransferTotal("10001", LocalDate.now());
        assertEquals(200, total, "Should return sum of TRANSFER_OUT");
    }

    @Test
    void testGetDailyTransferTotal_AccountNotExists() {
        double total = service.getDailyTransferTotal("99999", LocalDate.now());
        assertEquals(0.0, total, "Should return 0 if account not found");
    }

    @Test
    void testGetDailyTransferTotal_DateNotExists() {
        FundTransfer transfer = new FundTransfer(fromAccount, toAccount, 100, TransferMode.IMPS);
        service.logTransfer(transfer, 49900, 10100);

        double total = service.getDailyTransferTotal("10001", LocalDate.now().plusDays(1));
        assertEquals(0.0, total, "Should return 0 if date not found");
    }

    @Test
    void testGetDailyTransferTotal_NoTransferOut() {
        service.logWithdraw("10001", 500, 49500);

        double total = service.getDailyTransferTotal("10001", LocalDate.now());
        assertEquals(0.0, total, "Should return 0 if no TRANSFER_OUT logs");
    }

    @Test
    void testGetDailyTransferTotal_MultipleTransfers() {
        FundTransfer transfer1 = new FundTransfer(fromAccount, toAccount, 100, TransferMode.IMPS);
        service.logTransfer(transfer1, 49900, 10100);

        double total = service.getDailyTransferTotal("10001", LocalDate.now());
        assertTrue(total >= 100, "Should have at least one transfer");
    }
}
