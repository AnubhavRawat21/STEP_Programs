package com.gdb.services;

import com.gdb.models.Privilege;

/**
 * AccountPrivilegeManager
 */
public class AccountPrivilegeManager {
    public static double getMinimumBalance(Privilege privilege) {
        if (privilege == Privilege.SILVER) {
            return 10000;
        } else if (privilege == Privilege.GOLD) {
            return 15000;
        } else if (privilege == Privilege.PLATINUM) {
            return 20000;
        }
        return 0;
    }

    public static double getDailyTransferLimit(Privilege privilege) {
        if (privilege == Privilege.PLATINUM) {
            return 100000;
        } else if (privilege == Privilege.GOLD) {
            return 50000;
        } else if (privilege == Privilege.SILVER) {
            return 25000;
        }
        return 0;
    }

}
