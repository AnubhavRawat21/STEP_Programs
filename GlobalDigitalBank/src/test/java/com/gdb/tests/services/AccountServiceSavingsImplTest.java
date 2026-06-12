package com.gdb.tests.services;

import com.gdb.apis.integrations.IAadharApiService;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.dtos.PrivilegeDTO;
import com.gdb.dtos.GenderDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.exceptions.InvalidAgeException;
import com.gdb.models.SavingsAccount;
import com.gdb.repositories.IAccountRepository;
import com.gdb.services.AccountServiceSavingsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceSavingsImplTest {

    @Mock
    private IAccountRepository accountRepository;
    @Mock
    private IAadharApiService aadharApiService;

    @InjectMocks
    private AccountServiceSavingsImpl service;

    private OpenSavingsAccountDTO validDto;

    @BeforeEach
    void setUp() {
        validDto = new OpenSavingsAccountDTO(
                "John Doe", "9876543210", GenderDTO.MALE,
                LocalDate.now().minusYears(25), "123456789012",
                15000.0, PrivilegeDTO.SILVER, "1234"
        );
    }

    @Test
    void TestOpen_Success() throws Exception {
        when(accountRepository.checkIfSavingsAccountExists(anyString())).thenReturn(false);
        when(accountRepository.checkIfAadharIsBlacklisted(anyString())).thenReturn(false);
        when(aadharApiService.checkIfAadharIsValid(anyString())).thenReturn(true);
        when(accountRepository.generateAccountNumber()).thenReturn("1001");

        OpenAccountResponse response = service.open(validDto);

        assertNotNull(response);
        assertEquals("1001", response.getOpenAccountDTO().getAccountNumber());
        verify(accountRepository, times(1)).storeAccountInformation(any(SavingsAccount.class));
    }

    @Test
    void TestOpen_UnderAge() {
        validDto.setDateOfBirth(LocalDate.now().minusYears(10));
        AccountCreationException ex = assertThrows(AccountCreationException.class, () -> service.open(validDto));
        assertTrue(ex.getMessage().contains("Age must be greater than 18"));
    }

    @Test
    void TestOpen_AccountAlreadyExists() {
        when(accountRepository.checkIfSavingsAccountExists(anyString())).thenReturn(true);
        AccountCreationException ex = assertThrows(AccountCreationException.class, () -> service.open(validDto));
        assertTrue(ex.getMessage().contains("Active savings account already exists"));
    }

    @Test
    void TestOpen_ExactlyEighteen() throws Exception {
        validDto.setDateOfBirth(LocalDate.now().minusYears(18));
        when(accountRepository.checkIfSavingsAccountExists(anyString())).thenReturn(false);
        when(accountRepository.checkIfAadharIsBlacklisted(anyString())).thenReturn(false);
        when(aadharApiService.checkIfAadharIsValid(anyString())).thenReturn(true);
        when(accountRepository.generateAccountNumber()).thenReturn("1002");

        OpenAccountResponse response = service.open(validDto);
        assertNotNull(response);
        assertEquals("1002", response.getOpenAccountDTO().getAccountNumber());
    }

    @Test
    void TestOpen_BlacklistedAadhar() {
        when(accountRepository.checkIfSavingsAccountExists(anyString())).thenReturn(false);
        when(accountRepository.checkIfAadharIsBlacklisted(anyString())).thenReturn(true);
        
        AccountCreationException ex = assertThrows(AccountCreationException.class, () -> service.open(validDto));
        assertTrue(ex.getMessage().contains("blacklisted"));
    }

    @Test
    void TestOpen_InvalidAadharApi() {
        when(accountRepository.checkIfSavingsAccountExists(anyString())).thenReturn(false);
        when(accountRepository.checkIfAadharIsBlacklisted(anyString())).thenReturn(false);
        when(aadharApiService.checkIfAadharIsValid(anyString())).thenReturn(false);
        
        AccountCreationException ex = assertThrows(AccountCreationException.class, () -> service.open(validDto));
        assertTrue(ex.getMessage().contains("Invalid Aadhar"));
    }
}
