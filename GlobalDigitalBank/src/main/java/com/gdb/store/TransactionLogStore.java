package com.gdb.store;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdb.models.Transaction;
import com.gdb.models.TransactionType;

/**
 * TransactionLogStore
 */
public class TransactionLogStore {
    // Account Number, then Date, then Transaction Type, then List of Transactions
    public static Map<String, Map<LocalDate, Map<TransactionType, List<Transaction>>>> logs = new HashMap<>();
}
