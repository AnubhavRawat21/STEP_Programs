package com.gdb.tests.services;

import com.gdb.dtos.CloseAccountDTO;
import com.gdb.dtos.responses.CloseAccountResponse;
import com.gdb.exceptions.AccountNotActiveException;
import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.models.Account;
import com.gdb.repositories.IAccountRepository;
import com.gdb.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private CloseAccountDTO closeAccountDTO;

    @BeforeEach
    void setUp() {
        closeAccountDTO = new CloseAccountDTO("1001");
    }

    @Test
    void testCloseAccount_Success() throws Exception {
        Account mockAccount = mock(Account.class);
        when(mockAccount.isActive()).thenReturn(true);
        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(mockAccount);

        CloseAccountResponse response = accountService.closeAccount(closeAccountDTO);

        assertNotNull(response);
        assertEquals(200, response.getHttpStatusCode());
        assertEquals("Account Closed Successfully", response.getMessage());
        verify(accountRepository, times(1)).closeAccount(eq("1001"), any(LocalDateTime.class));
    }

    @Test
    void testCloseAccount_NotFound() throws Exception {
        when(accountRepository.getAccountByAccountNumber("1001")).thenThrow(new AccountRecordNotFoundException("Account record not found"));

        assertThrows(AccountRecordNotFoundException.class, () -> {
            accountService.closeAccount(closeAccountDTO);
        });

        verify(accountRepository, never()).closeAccount(anyString(), any(LocalDateTime.class));
    }

    @Test
    void testCloseAccount_AlreadyInactive() throws Exception {
        Account mockAccount = mock(Account.class);
        when(mockAccount.isActive()).thenReturn(false);
        when(accountRepository.getAccountByAccountNumber("1001")).thenReturn(mockAccount);

        assertThrows(AccountNotActiveException.class, () -> {
            accountService.closeAccount(closeAccountDTO);
        });

        verify(accountRepository, never()).closeAccount(anyString(), any(LocalDateTime.class));
    }
}
