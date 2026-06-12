package com.gdb.ui;

import com.gdb.controllers.ITransactionController;
import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.TransferModeDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;
import com.gdb.exceptions.InvalidInputException;
import com.gdb.utils.TransactionValidator;

import java.util.Scanner;

public class TransactionUI {

    private ITransactionController transactionController;
    private TransactionValidator validator;

    public TransactionUI(ITransactionController transactionController, TransactionValidator validator) {
        this.transactionController = transactionController;
        this.validator = validator;
    }

    public void withdraw() {
        /*
         * Requirements:
         * 1. User will be asked to provide the following details:
         * Account Number
         * PIN number
         * Amount to be withdrawn
         * 2. Rules:
         * Account number = cannot be null, cannot have alphabets
         * PIN number - cannot be null, cannot have alphabets
         * Amount - cannot be null, cannot be zero or less than zero
         * 3. Business Rules:
         * Account related to account number should exist and be active.
         * PIN number needs to match the PIN from the database
         * Account related to account number should have sufficient funds.
         * If minimum balance is breached after transaction, the user must be notified.
         */

        Scanner sc = new Scanner(System.in);

        // Accept the details needed for withdrawing and store in the dto
        WithdrawDTO withdrawDto = acceptWithdrawDetails(sc);

        // Send to backend
        WithdrawResponse response = transactionController.withdraw(withdrawDto);

        // if there was an error print out the details
        if (response.getHttpStatusCode() != 200) {
            System.out.println("Withdraw Transaction failed.");
            System.out.println("Details: " + response.getMessage());
            return;
        }
        displayWithdrawResponse(response);
    }

    public void transfer() {
        /*
         * Requirements:
         * 1. User will be asked to provide the following details:
         * From Account number
         * To Account number
         * Amount to be transferred
         * Transfer Mode - NEFT, RTGS, IMPS
         * PIN number
         * 2. Validation Rules:
         * From Account number = cannot be null, cannot have alphabets
         * To Account number = cannot be null, cannot have alphabets
         * PIN number - cannot be null, cannot have alphabets
         * From Account number and To account number cannot be the same
         * Amount to be transffered cannot be less than or equal to zero
         * Transfer mode needs to be one of these (NEFT, RTGS, IMPS)
         * 3. Logic Rules:
         * From account and To account need to exist and are both active.
         * PIN number needs to match the PIN for from account number from DB.
         * From account needs to have sufficient balance
         * Transfer amount has to be a maximum of the privilege level set for the user
         * for a single
         * day.
         * After transfer is made, return should be
         * From Account number
         * To Account number
         * Amount to be transferred
         * Remaining balance
         * Status
         * Message
         */

        Scanner sc = new Scanner(System.in);

        // Accept the details needed for fund transfer and store in the dto
        TransferDTO transferDTO = acceptTransferDetails(sc);

        // Send the dto to the backed
        TransferResponse response = transactionController.transfer(transferDTO);
        if (response.getHttpStatusCode() != 200) {
            System.out.println("Transfer transaction has failed");
            System.out.println("Details: " + response.getMessage());
        }

        displayTransferResponse(response);
    }

    //
    // PRIVATE methods
    //
    private TransferDTO acceptTransferDetails(Scanner sc) {
        String fromAccountNumber = acceptAccountNumber(sc, "Enter your account number: ");
        String toAccountNumber = acceptAccountNumber(sc, "Enter destination account number: ");
        double transferAmount = acceptTransferAmount(sc);
        TransferModeDTO transferMode = acceptTransferMode(sc);
        String pinNumber = acceptPinNumber(sc);

        return new TransferDTO(fromAccountNumber, toAccountNumber, transferAmount, transferMode, pinNumber);
    }

    private void displayTransferResponse(TransferResponse response) {
        if (response.getHttpStatusCode() != 200) {
            return;
        }

        var dto = response.getTransferDTO();
        System.out.println("\n Transfer Transaction Status ");
        System.out.println("------------------------------------\n");
        System.out.println("Message : " + response.getMessage());
        System.out.println("From Account : " + dto.getFromAccountNumber());
        System.out.println("To Account : " + dto.getToAccountNumber());
        System.out.println("Amount Transferred : " + dto.getTransferAmount());
        System.out.println("Transfer Mode : " + dto.getTransferMode());
        System.out.println("New Balance : " + dto.getNewBalance());

        if (dto.getMinimumBalanceWarning()) {
            System.out.println(
                    "Warning: Your balance has dropped below the minimum balance for your privilege tier.");
        }
        System.out.println("------------------------------------\n");
    }

    private WithdrawDTO acceptWithdrawDetails(Scanner sc) {
        String accountNumber = acceptAccountNumber(sc, "Enter your account number: ");
        String pinNumber = acceptPinNumber(sc);
        double withdrawAmount = acceptWithdrawAmount(sc);

        return new WithdrawDTO(accountNumber, withdrawAmount, pinNumber);
    }

    private TransferModeDTO acceptTransferMode(Scanner sc) {
        while (true) {
            System.out.println("Enter transferMode (1: NEFT, 2: IMPS, 3: RTGS):");
            if (sc.hasNextInt()) {
                int transferModeChoice = sc.nextInt();
                sc.nextLine(); // consume newline
                TransferModeDTO mode = mapToTransferModeDTO(transferModeChoice);
                if (mode != null) {
                    return mode;
                }
            } else {
                sc.nextLine(); // consume bad input
            }
            System.out.println("Enter a valid option (1, 2, or 3).");
        }
    }

    private TransferModeDTO mapToTransferModeDTO(int choice) {
        switch (choice) {
            case 1:
                return TransferModeDTO.NEFT;
            case 2:
                return TransferModeDTO.IMPS;
            case 3:
                return TransferModeDTO.RTGS;
            default:
                return null;
        }
    }

    private String acceptAccountNumber(Scanner sc, String message) {
        do {
            System.out.print(message);
            String accountNumber = sc.nextLine();
            try {
                validator.validateAccountNumber(accountNumber);
                return accountNumber;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private String acceptPinNumber(Scanner sc) {
        do {
            System.out.println("Enter your 4-digit PIN: ");
            String pinNumber = sc.nextLine();
            try {
                validator.validatePin(pinNumber);
                return pinNumber;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private double acceptWithdrawAmount(Scanner sc) {
        while (true) {
            System.out.println("Enter amount to withdraw: ");
            if (sc.hasNextDouble()) {
                double amount = sc.nextDouble();
                try {
                    sc.nextLine();
                    validator.validateWithdrawAmount(amount);
                    return amount;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private double acceptTransferAmount(Scanner sc) {
        while (true) {
            System.out.println("Enter amount to transfer: ");
            if (sc.hasNextDouble()) {
                double amount = sc.nextDouble();
                try {
                    sc.nextLine();
                    validator.validateTransferAmount(amount);
                    return amount;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void displayWithdrawResponse(WithdrawResponse response) {
        var dto = response.getWithdrawDTO();
        System.out.println("\n Transaction Status ");
        System.out.println("------------------------------------\n");
        System.out.println("Message : " + response.getMessage());
        if (response.getHttpStatusCode() == 200) {
            System.out.println("Previous Balance : " + dto.getOriginalBalance());
            System.out.println("Amount Withdrawn : " + dto.getWithdrawAmount());
            System.out.println("New Balance      : " + dto.getNewBalance());
            if (response.getWithdrawDTO().getMinimumBalanceWarning()) {
                System.out.println(
                        "Warning: Your balance has dropped below the minimum balance for your privilege tier.");
            }
        }
        System.out.println("------------------------------------\n");
    }

    public void deposit() {
        /*
         * Requirements:
         * 1. User will be asked to provide the following details:
         * Account Number
         * PIN number
         * Amount to be deposited
         * 2. Rules:
         * Account number = cannot be null, cannot have alphabets
         * PIN number - cannot be null, cannot have alphabets
         * Amount - cannot be null, cannot be zero or less than zero
         * 3. Business Rules:
         * Account related to account number should exist and be active.
         * PIN number needs to match the PIN from the database
         */

        Scanner sc = new Scanner(System.in);

        // Accept the details needed for depositing and store in the dto
        DepositDTO depositDto = acceptDepositDetails(sc);

        // Send to backend
        DepositResponse response = transactionController.deposit(depositDto);

        // if there was an error print out the details
        if (response.getHttpStatusCode() != 200) {
            System.out.println("Deposit Transaction failed.");
            System.out.println("Details: " + response.getMessage());
            return;
        }
        displayDepositResponse(response);
    }

    private DepositDTO acceptDepositDetails(Scanner sc) {
        String accountNumber = acceptAccountNumber(sc, "Enter your account number: ");
        String pinNumber = acceptPinNumber(sc);
        double depositAmount = acceptDepositAmount(sc);

        return new DepositDTO(accountNumber, depositAmount, pinNumber);
    }

    private double acceptDepositAmount(Scanner sc) {
        while (true) {
            System.out.println("Enter amount to deposit: ");
            if (sc.hasNextDouble()) {
                double amount = sc.nextDouble();
                try {
                    sc.nextLine();
                    validator.validateDepositAmount(amount);
                    return amount;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void displayDepositResponse(DepositResponse response) {
        var dto = response.getDepositDTO();
        System.out.println("\n Deposit Transaction Status ");
        System.out.println("------------------------------------\n");
        System.out.println("Message          : " + response.getMessage());
        if (response.getHttpStatusCode() == 200) {
            System.out.println("Previous Balance : " + dto.getOriginalBalance());
            System.out.println("Amount Deposited : " + dto.getDepositAmount());
            System.out.println("New Balance      : " + dto.getNewBalance());
        }
        System.out.println("------------------------------------\n");
    }
}
