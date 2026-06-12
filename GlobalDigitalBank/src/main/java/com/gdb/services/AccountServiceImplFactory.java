package com.gdb.services;

import java.util.HashMap;
import java.util.Map;
import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.OpenSavingsAccountDTO;

/**
 * AccountServiceImplFactory
 * Singleton factory for creating/retrieving the specific account service implementation.
 */
public class AccountServiceImplFactory {
    private static final AccountServiceImplFactory INSTANCE = new AccountServiceImplFactory();
    private final Map<Class<? extends OpenAccountDTO>, IAccountServiceImpl> registry = new HashMap<>();

    private AccountServiceImplFactory() {
        registerDefaults();
    }

    public static AccountServiceImplFactory getInstance() {
        return INSTANCE;
    }

    private void registerDefaults() {
        // Default implementations are registered lazily on first access
    }

    public void register(Class<? extends OpenAccountDTO> dtoClass, IAccountServiceImpl impl) {
        if (registry.containsKey(dtoClass)) {
            throw new IllegalStateException("Implementation already registered for: " + dtoClass.getName());
        }
        registry.put(dtoClass, impl);
    }

    public IAccountServiceImpl create(Class<? extends OpenAccountDTO> dtoClass) {
        IAccountServiceImpl impl = registry.get(dtoClass);
        if (impl == null) {
            throw new IllegalArgumentException("No implementation registered for: " + dtoClass.getName());
        }
        return impl;
    }
}
