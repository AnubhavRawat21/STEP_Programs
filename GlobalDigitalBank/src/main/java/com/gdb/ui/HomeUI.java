package com.gdb.ui;

import java.util.Scanner;

/**
 * HomeUI
 */
public class HomeUI {
    private AccountUI accountUI;
    private TransactionUI transactionUI;

    public HomeUI(AccountUI accountUI, TransactionUI transactionUI) {
        this.accountUI = accountUI;
        this.transactionUI = transactionUI;
    }

    public void showUI() {
        Scanner sc = new Scanner(System.in);
        int selection = 0;

        // Show the Menu
        do {
            showMenu();

            // Get user selection and call the associated method
            selection = getUserSelection(sc);
            switch (selection) {
                case 2:
                    accountUI.openAccount();
                    System.out.println();
                    selection = 0;
                    break;
                case 5:
                    transactionUI.withdraw();
                    System.out.println();
                    selection = 0;
                    break;
                case 7:
                    transactionUI.transfer();
                    System.out.println();
                    selection = 0;
                    break;
                case 6:
                    transactionUI.deposit();
                    System.out.println();
                    selection = 0;
                    break;
                case 3:
                    accountUI.closeAccount();
                    System.out.println();
                    selection = 0;
                    break;
                case 1:
                case 4:
                    System.out.println("Unimplemented Feature");
                    System.out.println();
                    selection = 0;
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (selection == 0);
        sc.close();
    }

    private int getUserSelection(Scanner sc) {
        if (!sc.hasNextInt()) {
            sc.nextLine(); // consume bad input
        }
        return sc.nextInt();
    }

    private void showMenu() {
        System.out.println("\n-------------------------");
        System.out.println("Global Digital Bank");
        System.out.println("-------------------------");
        System.out.println("Please select an option: ");
        System.out.println("1) Dashboard");
        System.out.println("2) Open Account");
        System.out.println("3) Close Account");
        System.out.println("4) View Transactions");
        System.out.println("5) Withdraw");
        System.out.println("6) Deposit");
        System.out.println("7) Transfer");
        System.out.println("8) Exit");
        System.out.println("-------------------------");
        System.out.print("Enter choice: ");

    }
}
