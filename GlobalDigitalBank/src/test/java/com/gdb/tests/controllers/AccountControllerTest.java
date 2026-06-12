package com.gdb.tests.controllers;

import com.gdb.controllers.AccountController;
import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.dtos.PrivilegeDTO;
import com.gdb.dtos.GenderDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.AccountCreationException;
import com.gdb.exceptions.InvalidInputException;
import com.gdb.services.IAccountService;
import com.gdb.utils.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private IAccountService accountService;
    @Mock
    private AccountValidator validator;

    @InjectMocks
    private AccountController controller;

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
    void TestOpenAccount_Positive_Success() throws Exception {
        OpenAccountResponse response = new OpenAccountResponse(validDto);
        when(accountService.openAccount(any(OpenAccountDTO.class))).thenReturn(response);

        OpenAccountResponse result = controller.openAccount(validDto);

        assertEquals(200, result.getHttpStatusCode());
        verify(validator).validateName("John Doe");
    }

    @Test
    void TestOpenAccount_Negative_ValidationFailed() throws Exception {
        // Force validator to throw exception
        doThrow(new InvalidInputException("Invalid name")).when(validator).validateName(anyString());

        OpenAccountResponse result = controller.openAccount(validDto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestOpenAccount_Negative_UnderAge() throws Exception {
        // Business logic failure
        when(accountService.openAccount(any(OpenAccountDTO.class)))
            .thenThrow(new AccountCreationException("Age must be greater than 18"));

        OpenAccountResponse result = controller.openAccount(validDto);

        assertEquals(400, result.getHttpStatusCode());
    }

    @Test
    void TestOpenAccount_Edge_SystemError() throws Exception {
        // Generic exception
        when(accountService.openAccount(any(OpenAccountDTO.class)))
            .thenThrow(new RuntimeException("Database down"));

        OpenAccountResponse result = controller.openAccount(validDto);

        assertEquals(500, result.getHttpStatusCode());
    }
}
