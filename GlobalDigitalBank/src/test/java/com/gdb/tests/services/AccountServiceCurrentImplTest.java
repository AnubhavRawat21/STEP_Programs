package com.gdb.tests.services;

import com.gdb.apis.integrations.ICompanyApiService;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.PrivilegeDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.models.CurrentAccount;
import com.gdb.repositories.IAccountRepository;
import com.gdb.services.AccountServiceCurrentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceCurrentImplTest {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private ICompanyApiService companyApiService;

    @InjectMocks
    private AccountServiceCurrentImpl service;

    private OpenCurrentAccountDTO validDto;

    @BeforeEach
    void setUp() {
        validDto = new OpenCurrentAccountDTO(
                "Company Inc", PrivilegeDTO.GOLD, "1234", 1000.0,
                "Company Inc", "www.corp.com", "REG12345"
        );
    }

    @Test
    void TestOpen_Success() throws Exception {
        when(accountRepository.checkIfCurrentAccountExists(anyString())).thenReturn(false);
        when(accountRepository.checkIfCompanyIsBlacklisted(anyString())).thenReturn(false);
        when(companyApiService.checkIfCompanyRegistrationIsValid(anyString())).thenReturn(true);
        when(accountRepository.generateAccountNumber()).thenReturn("2001");

        OpenAccountResponse response = service.open(validDto);

        assertNotNull(response);
        assertEquals("2001", response.getOpenAccountDTO().getAccountNumber());
        verify(accountRepository, times(1)).storeAccountInformation(any(CurrentAccount.class));
    }

    @Test
    void TestOpen_AlreadyExists() {
        when(accountRepository.checkIfCurrentAccountExists(anyString())).thenReturn(true);
        assertThrows(AccountCreationException.class, () -> service.open(validDto));
    }

    @Test
    void TestOpen_BlacklistedCompany() {
        when(accountRepository.checkIfCurrentAccountExists(anyString())).thenReturn(false);
        when(accountRepository.checkIfCompanyIsBlacklisted(anyString())).thenReturn(true);
        
        assertThrows(AccountCreationException.class, () -> service.open(validDto));
    }
}
