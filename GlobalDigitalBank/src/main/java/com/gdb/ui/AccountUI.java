package com.gdb.ui;

import com.gdb.controllers.IAccountController;
import com.gdb.dtos.GenderDTO;
import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.dtos.PrivilegeDTO;
import com.gdb.dtos.responses.OpenAccountResponse;
import com.gdb.exceptions.InvalidInputException;
import com.gdb.utils.AccountValidator;
import com.gdb.utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * AccountUI
 */
public class AccountUI {
    private IAccountController accountController;
    private AccountValidator validator;

    public AccountUI(IAccountController accountController, AccountValidator validator) {
        this.accountController = accountController;
        this.validator = validator;
    }

    public void openAccount() {
        /*
         * 1. User will be asked to select whether they want to open savings/current
         * account
         * 2. Based on the choice, the user will be asked to enter information related
         * to respective
         * account.
         * Savings - name, gender, DOB || Current - company name, Company Registration Number
         * 3. Input Validation (Ensure each entry follows their respective rules)
         * Savings account :
         * name - cannot have numbers, cannot have less than 2 letters, cannot have
         * special
         * characters.(other than " " and "."). Must not be null
         * DOB - needs to follow dd/mm/yyyy format, needs to be a valid DOB (cannot be
         * the next
         * day/ older than 18)
         * aadhar number - Has to be exactly 12 digits long, has to be a valid aadhar.
         * Gender - Has to be Male, Female or other.
         * Privlege - Has to be Gold, Silver, Bronze.
         * PIN number - Has to be a valid PIN number(Maximum 4 digits). Must not be null
         * Initial amount - Amount with which the account is opened.
         * Phone Number - phone number needs to be 10 digits.
         * Current Account:
         * company name - cannot have numbers, cannot have less than 2 letters, cannot
         * have
         * special characters(other than " " and ".").Must not be null
         * CID - Has to be a valid company ID, Cannot contain special characters.
         * company registration number - has to be a valid 12 digit number.
         * company url - must follow valid URL format(http/https).
         * PIN number - Has to be a valid PIN number(Maximum 4 digits). Must not be null
         * Initial amount - Amount with which the account is opened.
         * Privlege - Has to be Gold, Silver, Bronze.
         * 4. Navigate to Account Controller
         * Perform Data validation in the controller
         * 5. Navigate to the Service
         * Perform core business logic for savings and current account.
         */

        Scanner sc = new Scanner(System.in);

        // User is asked if they would like to open a "Savings" or "Current" account
        String accountTypeChoice = getUserChoiceForAccountType(sc);

        // Based on the choice, the user will be asked to enter information related to
        // respective account.
        OpenAccountDTO openAccountDto = null;
        if (accountTypeChoice.equalsIgnoreCase("Savings")) {
            openAccountDto = acceptSavingsInformation(sc);
        } else {
            openAccountDto = acceptCurrentInformation(sc);
        }

        // Send DTO to the backend
        OpenAccountResponse response = accountController.openAccount(openAccountDto);
        if (response == null) {
            System.out.println("Error: No response from server.");
            return;
        }

        // If request failed, print the error message
        if (response.getHttpStatusCode() != 200) {
            System.out.println("Account opening failed.");
            System.out.println("Details: " + response.getMessage());
            return;
        }

        // Display Account Details
        displayAccountResponse(response.getOpenAccountDTO());
    }

    public void displayAccountResponse(OpenAccountDTO responseDTO) {
        System.out.println("\n Account Created Successfully ");
        System.out.println("------------------------------------\n");
        System.out.println("Account #      : " + responseDTO.getAccountNumber());
        System.out.println("Name           : " + responseDTO.getName());
        System.out.println("Privilege      : " + responseDTO.getPrivilegeDTO());
        System.out.println("Initial Amount : " + responseDTO.getInitialAmount());
        if (responseDTO instanceof OpenSavingsAccountDTO) {
            OpenSavingsAccountDTO savings = (OpenSavingsAccountDTO) responseDTO;
            System.out.println("Type          : Savings");
            System.out.println("DOB           : " + savings.getDateOfBirth());
            System.out.println("Gender        : " + savings.getGenderDTO());
            System.out.println("Phone         : " + savings.getPhoneNumber());
            System.out.println("Aadhar Number : " + savings.getAadharNumber());
        } else if (responseDTO instanceof OpenCurrentAccountDTO) {
            OpenCurrentAccountDTO current = (OpenCurrentAccountDTO) responseDTO;
            System.out.println("Type          : Current");
            System.out.println("Company       : " + current.getCompanyName());
            System.out.println("Website       : " + current.getWebsiteURL());
            System.out.println("Registration #: " + current.getRegistrationNumber());
        }
        System.out.println("------------------------------------\n");
    }

    //
    // HELPERS
    //

    private String getUserChoiceForAccountType(Scanner sc) {
        do {
            System.out.println("Choose an Account type (Savings/Current): ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Savings") || choice.equalsIgnoreCase("Current")) {
                return choice;
            }
            System.out.println("Enter valid account type.");
        } while (true);
    }

    private OpenSavingsAccountDTO acceptSavingsInformation(Scanner sc) {
        String name = acceptName(sc);
        PrivilegeDTO privilege = acceptPrivilege(sc);
        double initialAmount = acceptInitialAmount(sc, privilege);

        // Savings specific info
        String phoneNumber = acceptPhoneNumber(sc);
        GenderDTO gender = acceptGender(sc);
        LocalDate dob = acceptDateOfBirth(sc);
        String aadharNumber = acceptAadharNumber(sc);

        // PIN at the end
        String pin = acceptPin(sc);

        return new OpenSavingsAccountDTO(name, phoneNumber, gender, dob, aadharNumber, initialAmount,
                privilege, pin);
    }

    private OpenCurrentAccountDTO acceptCurrentInformation(Scanner sc) {
        String name = acceptName(sc);
        PrivilegeDTO privilege = acceptPrivilege(sc);
        double initialAmount = acceptInitialAmount(sc, privilege);

        // Current specific information
        String companyName = acceptCompanyName(sc);
        String registrationNumber = acceptRegistrationNumber(sc);
        String websiteURL = acceptWebsiteURL(sc);

        // PIN at the end
        String pin = acceptPin(sc);

        // Return the DTO
        return new OpenCurrentAccountDTO(name, privilege, pin, initialAmount, companyName,
                websiteURL, registrationNumber);
    }

    //
    // PRIVATE INPUT METHODS
    //

    private String acceptName(Scanner sc) {
        do {
            System.out.println("Enter your name: ");
            String name = sc.nextLine().trim();
            try {
                validator.validateName(name);
                return name;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private PrivilegeDTO acceptPrivilege(Scanner sc) {
        do {
            System.out.println("Enter the privilege tier (1: SILVER, 2: GOLD, 3: PLATINUM): ");
            if (sc.hasNextInt()) {
                int privChoice = sc.nextInt();
                sc.nextLine(); // consume newline
                switch (privChoice) {
                    case 1:
                        return PrivilegeDTO.SILVER;
                    case 2:
                        return PrivilegeDTO.GOLD;
                    case 3:
                        return PrivilegeDTO.PLATINUM;
                    default:
                        System.out.println("Enter a valid option.");
                        continue;
                }
            }
            System.out.println("Enter a valid option (1, 2, or 3).");
            sc.nextLine(); // Consume bad input
        } while (true);
    }

    private double acceptInitialAmount(Scanner sc, PrivilegeDTO privilege) {
        do {
            System.out.println("Enter initial amount: ");
            if (sc.hasNextDouble()) {
                double amount = sc.nextDouble();
                sc.nextLine(); // consume newline
                try {
                    validator.validateInitialAmount(amount);
                    validator.validateMinimumBalance(privilege, amount);
                    return amount;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Enter a valid positive initial amount.");
                sc.nextLine(); // Consume bad input
            }
        } while (true);
    }

    private String acceptPin(Scanner sc) {
        do {
            System.out.println("Enter a 4-digit PIN: ");
            String pin = sc.nextLine().trim();
            try {
                validator.validatePin(pin);
                return pin;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private String acceptPhoneNumber(Scanner sc) {
        do {
            System.out.println("Enter your phone number (10 digits): ");
            String phoneNumber = sc.nextLine().trim();
            try {
                validator.validatePhoneNumber(phoneNumber);
                return phoneNumber;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private GenderDTO acceptGender(Scanner sc) {
        do {
            System.out.println("Enter your gender (1: Male, 2: Female, 3: Other):");
            if (sc.hasNextInt()) {
                int genderChoice = sc.nextInt();
                sc.nextLine(); // consume newline
                switch (genderChoice) {
                    case 1:
                        return GenderDTO.MALE;
                    case 2:
                        return GenderDTO.FEMALE;
                    case 3:
                        return GenderDTO.OTHER;
                    default:
                        System.out.println("Enter a valid option");
                        continue;
                }
            }
            System.out.println("Enter a valid option (1, 2, or 3).");
            sc.nextLine(); // Consume bad input
        } while (true);
    }

    private LocalDate acceptDateOfBirth(Scanner sc) {
        do {
            System.out.println("Enter your date of birth (dd/MM/yyyy): ");
            String inputDate = sc.nextLine().trim();
            try {
                return DateUtils.parseDate(inputDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        } while (true);
    }

    private String acceptAadharNumber(Scanner sc) {
        do {
            System.out.println("Enter your Aadhar number (12 digits): ");
            String aadharNumber = sc.nextLine().trim();
            try {
                validator.validateAadharNumber(aadharNumber);
                return aadharNumber;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private String acceptCompanyName(Scanner sc) {
        do {
            System.out.println("Enter company name: ");
            String companyName = sc.nextLine().trim();
            try {
                validator.validateCompanyName(companyName);
                return companyName;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private String acceptRegistrationNumber(Scanner sc) {
        do {
            System.out.println("Enter Company Registration Number: ");
            String regNum = sc.nextLine().trim();
            try {
                validator.validateCompanyRegistrationNumber(regNum);
                return regNum;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private String acceptWebsiteURL(Scanner sc) {
        do {
            System.out.println("Enter Company Website URL (press Enter to skip): ");
            String websiteURL = sc.nextLine().trim();
            if (websiteURL.isEmpty()) {
                return websiteURL;
            }
            try {
                validator.validateWebsiteURL(websiteURL);
                return websiteURL;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void closeAccount() {
        Scanner sc = new Scanner(System.in);
        String accountNumber = null;
        do {
            System.out.println("Enter your account number: ");
            accountNumber = sc.nextLine();
            try {
                validator.validateAccountNumber(accountNumber);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        com.gdb.dtos.CloseAccountDTO closeAccountDto = new com.gdb.dtos.CloseAccountDTO(accountNumber);
        com.gdb.dtos.responses.CloseAccountResponse response = accountController.closeAccount(closeAccountDto);

        if (response == null) {
            System.out.println("Error: No response from server.");
            return;
        }

        if (response.getHttpStatusCode() == 200) {
            System.out.println("\n Account Closed Successfully ");
            System.out.println("------------------------------------");
            System.out.println("Message : Success");
            System.out.println("Account #      : " + response.getCloseAccountDTO().getAccountNumber());
            System.out.println("------------------------------------");
        } else {
            System.out.println("\n Account Closure Failed ");
            System.out.println("------------------------------------");
            System.out.println("Message : " + response.getMessage());
            System.out.println("------------------------------------");
        }
    }
}
