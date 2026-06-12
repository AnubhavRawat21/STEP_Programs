package com.gdb.tests.services;

import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.TransferModeDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;
import com.gdb.exceptions.AccountNotActiveException;
import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.exceptions.DailyTransferLimitExceededException;
import com.gdb.exceptions.IncorrectPinNumberException;
import com.gdb.exceptions.InsufficientFundsException;
import com.gdb.models.Account;
import com.gdb.models.FundTransfer;
import com.gdb.models.Privilege;
import com.gdb.models.SavingsAccount;
import com.gdb.models.Gender;
import com.gdb.repositories.IAccountRepository;
import com.gdb.repositories.ITransactionRepository;
import com.gdb.services.TransactionService;
import com.gdb.services.TransactionLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private IAccountRepository accountRepository;
    @Mock
    private ITransactionRepository transactionRepository;
    @Mock
    private TransactionLogService transactionLogService;

    @InjectMocks
    private TransactionService transactionService;

    private Account testAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        testAccount = new SavingsAccount("1001", "Test User", 50000.0, Privilege.SILVER, "1234",
                LocalDate.of(1990, 1, 1), Gender.MALE, "9876543210", "123456789012");
        testAccount.setActive(true);
        toAccount = new SavingsAccount("1002", "Receiver", 10000.0, Privilege.SILVER, "5678",
                LocalDate.of(1990, 1, 1), Gender.FEMALE, "9876543211", "223456789012");
        toAccount.setActive(true);
    }

    @Test
    void TestTransfer_Success() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 5000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("1002")).thenReturn(toAccount);
        when(transactionRepository.transferAmount(anyString(), anyString(), anyDouble()))
                .thenReturn(45000.0);

        TransferResponse response = transactionService.transfer(dto);

        assertNotNull(response);
        verify(transactionLogService).logTransfer(any(FundTransfer.class), anyDouble(), anyDouble());
    }

    @Test
    void TestTransfer_FromAccountNotFound() throws Exception {
        TransferDTO dto = new TransferDTO("9999", "1002", 5000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("9999"))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        assertThrows(AccountRecordNotFoundException.class, () -> transactionService.transfer(dto));
    }

    @Test
    void TestTransfer_ToAccountNotFound() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "9999", 5000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("9999"))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        assertThrows(AccountRecordNotFoundException.class, () -> transactionService.transfer(dto));
    }

    @Test
    void TestTransfer_FromAccountNotActive() throws Exception {
        Account inactiveAccount = new SavingsAccount("1001", "Test User", 50000.0, Privilege.SILVER, "1234",
                LocalDate.of(1990, 1, 1), Gender.MALE, "9876543210", "123456789012");
        inactiveAccount.setActive(false);

        TransferDTO dto = new TransferDTO("1001", "1002", 5000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(inactiveAccount);

        assertThrows(AccountNotActiveException.class, () -> transactionService.transfer(dto));
    }

    @Test
    void TestTransfer_ToAccountNotActive() throws Exception {
        toAccount.setActive(false);
        TransferDTO dto = new TransferDTO("1001", "1002", 5000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("1002")).thenReturn(toAccount);

        assertThrows(AccountNotActiveException.class, () -> transactionService.transfer(dto));
    }

    @Test
    void TestTransfer_IncorrectPin() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 5000.0, TransferModeDTO.IMPS, "wrong");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("1002")).thenReturn(toAccount);

        assertThrows(IncorrectPinNumberException.class, () -> transactionService.transfer(dto));
    }

    @Test
    void TestTransfer_InsufficientFunds() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 100000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("1002")).thenReturn(toAccount);

        assertThrows(InsufficientFundsException.class, () -> transactionService.transfer(dto));
    }

    @Test
    void TestTransfer_DailyLimitExceeded() throws Exception {
        TransferDTO dto = new TransferDTO("1001", "1002", 10000.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("1002")).thenReturn(toAccount);
        when(transactionLogService.getDailyTransferTotal(eq("1001"), any(LocalDate.class))).thenReturn(20000.0);

        assertThrows(DailyTransferLimitExceededException.class, () -> transactionService.transfer(dto));
    }



    @Test
    void TestTransfer_ZeroAmount() throws Exception {
        testAccount.setBalance(50000.0);
        TransferDTO dto = new TransferDTO("1001", "1002", 0.0, TransferModeDTO.IMPS, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(accountRepository.getAccountByAccountNumber("1002")).thenReturn(toAccount);

        TransferResponse response = transactionService.transfer(dto);

        assertNotNull(response);
    }

    // ==================== WITHDRAW TESTS ====================

    @Test
    void TestWithdraw_Success() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 5000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(transactionRepository.withdrawAmount(anyString(), eq(5000.0))).thenReturn(45000.0);

        WithdrawResponse response = transactionService.withdraw(dto);

        assertNotNull(response);
        verify(transactionLogService).logWithdraw(eq("1001"), eq(5000.0), anyDouble());
    }

    @Test
    void TestWithdraw_AccountNotFound() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("9999", 5000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("9999"))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        assertThrows(AccountRecordNotFoundException.class, () -> transactionService.withdraw(dto));
    }

    @Test
    void TestWithdraw_AccountNotActive() throws Exception {
        testAccount.setActive(false);
        WithdrawDTO dto = new WithdrawDTO("1001", 5000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        assertThrows(AccountNotActiveException.class, () -> transactionService.withdraw(dto));
    }

    @Test
    void TestWithdraw_IncorrectPin() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 5000.0, "wrong");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        assertThrows(IncorrectPinNumberException.class, () -> transactionService.withdraw(dto));
    }

    @Test
    void TestWithdraw_InsufficientFunds() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 100000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        assertThrows(InsufficientFundsException.class, () -> transactionService.withdraw(dto));
    }

    @Test
    void TestWithdraw_LowBalanceWarning() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 41000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(transactionRepository.withdrawAmount(anyString(), eq(41000.0))).thenReturn(9000.0);

        assertDoesNotThrow(() -> transactionService.withdraw(dto));
        assertTrue(dto.getMinimumBalanceWarning());
    }

    @Test
    void TestWithdraw_ZeroAmount() throws Exception {
        WithdrawDTO dto = new WithdrawDTO("1001", 0.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        WithdrawResponse response = transactionService.withdraw(dto);

        assertNotNull(response);
    }

    @Test
    void TestWithdraw_ExactBalance() throws Exception {
        testAccount.setBalance(10000.0);
        WithdrawDTO dto = new WithdrawDTO("1001", 10000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        WithdrawResponse response = transactionService.withdraw(dto);

        assertNotNull(response);
    }

    // ==================== HELPER METHOD TESTS ====================

    @Test
    void TestCheckPinNumber_Correct() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("checkPinNumber", String.class, String.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(transactionService, "1234", "1234");
        assertTrue(result);
    }

    @Test
    void TestCheckPinNumber_Incorrect() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("checkPinNumber", String.class, String.class);
        method.setAccessible(true);

        try {
            method.invoke(transactionService, "1234", "5678");
            fail("Should have thrown IncorrectPinNumberException");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IncorrectPinNumberException);
        }
    }

    @Test
    void TestCheckSufficientFunds_Enough() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("checkSufficientFunds", double.class, double.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(transactionService, 10000.0, 5000.0);
        assertTrue(result);
    }

    @Test
    void TestCheckSufficientFunds_NotEnough() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("checkSufficientFunds", double.class, double.class);
        method.setAccessible(true);

        try {
            method.invoke(transactionService, 5000.0, 10000.0);
            fail("Should have thrown InsufficientFundsException");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof InsufficientFundsException);
        }
    }

    @Test
    void TestCheckSufficientFunds_ExactlyEnough() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("checkSufficientFunds", double.class, double.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(transactionService, 5000.0, 5000.0);
        assertTrue(result);
    }

    @Test
    void TestCheckTransferLimit_UnderLimit() throws Exception {
        when(transactionLogService.getDailyTransferTotal(any(), any())).thenReturn(5000.0);

        Method method = TransactionService.class.getDeclaredMethod("checkTransferLimit", Account.class,
                double.class);
        method.setAccessible(true);
        
        // Should NOT throw - within limit
        assertDoesNotThrow(() -> method.invoke(transactionService, testAccount, 10000.0));
    }

    @Test
    void TestCheckTransferLimit_OverLimit() throws Exception {
        when(transactionLogService.getDailyTransferTotal(any(), any())).thenReturn(20000.0);

        Method method = TransactionService.class.getDeclaredMethod("checkTransferLimit", Account.class,
                double.class);
        method.setAccessible(true);
        
        // Should throw - over limit
        try {
            method.invoke(transactionService, testAccount, 6000.0);
            fail("Should have thrown DailyTransferLimitExceededException");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DailyTransferLimitExceededException);
        }
    }

    @Test
    void TestShouldWarnMinimumBalance_True() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("shouldWarnMinimumBalance", double.class,
                double.class, Privilege.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(transactionService, 50000.0, 45000.0, Privilege.SILVER);

        assertTrue(result);
    }

    @Test
    void TestShouldWarnMinimumBalance_ExactlyAtMin() throws Exception {
        Method method = TransactionService.class.getDeclaredMethod("shouldWarnMinimumBalance", double.class,
                double.class, Privilege.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(transactionService, 50000.0, 40000.0, Privilege.SILVER);

        assertFalse(result);
    }

    // ==================== DEPOSIT TESTS ====================

    @Test
    void TestDeposit_Success() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 5000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);
        when(transactionRepository.depositAmount(anyString(), eq(5000.0))).thenReturn(55000.0);

        DepositResponse response = transactionService.deposit(dto);

        assertNotNull(response);
        verify(transactionLogService).logDeposit(eq("1001"), eq(5000.0), anyDouble());
    }

    @Test
    void TestDeposit_AccountNotFound() throws Exception {
        DepositDTO dto = new DepositDTO("9999", 5000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("9999"))
                .thenThrow(new AccountRecordNotFoundException("Account not found"));

        assertThrows(AccountRecordNotFoundException.class, () -> transactionService.deposit(dto));
    }

    @Test
    void TestDeposit_AccountNotActive() throws Exception {
        testAccount.setActive(false);
        DepositDTO dto = new DepositDTO("1001", 5000.0, "1234");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        assertThrows(AccountNotActiveException.class, () -> transactionService.deposit(dto));
    }

    @Test
    void TestDeposit_IncorrectPin() throws Exception {
        DepositDTO dto = new DepositDTO("1001", 5000.0, "wrong");

        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(testAccount);

        assertThrows(IncorrectPinNumberException.class, () -> transactionService.deposit(dto));
    }
}
