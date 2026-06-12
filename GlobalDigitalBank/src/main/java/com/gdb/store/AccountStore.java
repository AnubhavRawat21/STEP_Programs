package com.gdb.store;

import com.gdb.models.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountStore {
    /*
     * Data Generation Strategy:
     * 1. Some active accounts, some inactive accounts
     * 2. Some savings and current accounts
     * 3. Different privileges.
     * 
     */
    public static final List<Account> blackListedAccounts = new ArrayList<>();
    public static final List<Account> accounts = new ArrayList<>();

    private static final AtomicInteger accountNumberCounter = new AtomicInteger(1001);

    public static String getNextAccountNumber() {
        return String.valueOf(accountNumberCounter.getAndIncrement());
    }

    public static final Set<String> REGISTERED_AADHAAR_NUMBERS = Set.of(
            "600000000001",
            "600000000002",
            "600000000003",
            "600000000004",
            "600000000005",
            "600000000006",
            "600000000007",
            "600000000008",
            "600000000009",
            "600000000010");

    public static final Set<String> REGISTERED_COMPANY_NUMBERS = Set.of(
            "L01111TN2022PLC444333",
            "L10101UP2022PLC000999",
            "L15142UP2020PLC012345",
            "L17110WB2018PLC666777",
            "L18101MH2020PLC222333",
            "U45200HR2023PTC789012",
            "U46100DL2022PTC333444",
            "U51909GJ2022PTC567890",
            "U55101KA2023PTC111000",
            "U72900KA2020PTC123456");

    private static void setAccountStatus(Account account, boolean active) {
        account.setActive(active);
        account.setActivatedDate(LocalDateTime.now().minusDays(100));
        if (!active) {
            account.setClosedDate(LocalDateTime.now());
        }
    }

    private static Account createBlacklistedAccount(Account account) {
        account.setActive(false);
        account.setActivatedDate(LocalDateTime.now().minusDays(365));
        account.setClosedDate(LocalDateTime.now().minusDays(300));
        return account;
    }
}
