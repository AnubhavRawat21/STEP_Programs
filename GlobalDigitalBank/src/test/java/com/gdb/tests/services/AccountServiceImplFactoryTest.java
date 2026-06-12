package com.gdb.tests.services;

import com.gdb.dtos.OpenAccountDTO;
import com.gdb.services.AccountServiceImplFactory;
import com.gdb.services.IAccountServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplFactoryTest {

    @Test
    void testCreate_RegisteredDTO() {
        IAccountServiceImpl mockImpl = mock(IAccountServiceImpl.class);

        AccountServiceImplFactory.getInstance().register(OpenAccountDTO.class, mockImpl);

        IAccountServiceImpl result = AccountServiceImplFactory.getInstance().create(OpenAccountDTO.class);
        assertEquals(mockImpl, result);
    }

    @Test
    void testCreate_UnregisteredDTO() {
        assertThrows(IllegalArgumentException.class, () -> {
            AccountServiceImplFactory.getInstance().create(OpenAccountDTO.class);
        });
    }
}
