# 📝 Student Assignment: Implement Transaction & Account Closure Logic (In-Memory)

Welcome to the Global Digital Bank (GDB) backend developer exercise. Your task is to implement the core business logic for three main financial transaction features (**Deposit**, **Withdraw**, and **Transfer**) and the **Close Account** feature.

---

## 🎯 Objectives
You must implement the logic at two different architectural layers:
1. **Controller Layer:** Validate request DTOs, invoke the corresponding service, catch validation/business exceptions to populate failure details, and catch unexpected system exceptions to report errors.
2. **Service Layer:** Coordinate domain validation (PIN checks, balance availability, active status checks, daily limits) and call repositories to persist data, followed by logging.

---

## 📂 Files & Methods to Modify

You need to open the following files in your IDE and implement the code where the `// TODO: Student implementation` comments are placed.

### 1. Controller Layer
#### 📄 File: `src/main/java/com/gdb/controllers/TransactionController.java`
* **Method: `withdraw(WithdrawDTO withdrawDTO)`**
  * Validate request properties using `validateWithdrawRequest(withdrawDTO)`.
  * Call `transactionService.withdraw(withdrawDTO)`.
  * Catch exceptions: `InvalidInputException`, `AccountRecordNotFoundException`, `IncorrectPinNumberException`, `InsufficientFundsException`, `AccountNotActiveException`. Populate the response with the failure details and set the error message.
  * Catch any general `Exception` to indicate a system error.
* **Method: `transfer(TransferDTO transferDTO)`**
  * Validate request using `validateTransferRequest(transferDTO)`.
  * Call `transactionService.transfer(transferDTO)`.
  * Catch same exceptions (plus `DailyTransferLimitExceededException`) and populate the response with the failure details.
* **Method: `deposit(DepositDTO depositDTO)`**
  * Validate request using `validateDepositRequest(depositDTO)`.
  * Call `transactionService.deposit(depositDTO)`.
  * Catch exceptions and populate the response with the failure details.

#### 📄 File: `src/main/java/com/gdb/controllers/AccountController.java`
* **Method: `closeAccount(CloseAccountDTO closeAccountDto)`**
  * Validate the input using `validator.validateAccountNumber(closeAccountDto.getAccountNumber())`.
  * Call `accountService.closeAccount(closeAccountDto)`.
  * Catch exceptions: `InvalidInputException`, `AccountRecordNotFoundException`, `AccountNotActiveException` and populate the response with the failure details and error message.
  * Catch any generic `Exception` and report a system error.

---

### 2. Service Layer
#### 📄 File: `src/main/java/com/gdb/services/TransactionService.java`
* **Method: `withdraw(WithdrawDTO dto)`**
  * Fetch account using `accountRepository.getAccountByAccountNumber(dto.getAccountNumber())`.
  * Ensure account exists, set original balance in DTO, and check that the account is active.
  * Verify PIN matches: call `checkPinNumber(account.getPinNumber(), dto.getPinNumber())`.
  * Verify sufficient funds: call `checkSufficientFunds(account.getBalance(), dto.getWithdrawAmount())`.
  * Low Balance Warning: check if balance drops below the minimum balance threshold for the account's privilege tier. If so, set `dto.setMinimumBalanceWarning(true)`.
  * Execute state update: call `transactionRepository.withdrawAmount(...)`.
  * Log event: call `transactionLogService.logWithdraw(...)`.
* **Method: `transfer(TransferDTO dto)`**
  * Fetch both sender and receiver accounts; check that both exist and are active.
  * Verify sender's PIN matches database.
  * Verify sender has sufficient funds.
  * Check daily transfer limit: invoke `checkTransferLimit(fromAccount, dto.getTransferAmount())`. If the total daily transfer sum exceeds the tier limit, throw `DailyTransferLimitExceededException`.
  * Check for low balance warning on the sender's account.
  * Execute transfer: call `transactionRepository.transferAmount(...)`.
  * Log event: call `transactionLogService.logTransfer(...)`.
* **Method: `deposit(DepositDTO dto)`**
  * Fetch account and check active status.
  * Verify PIN.
  * Execute deposit: call `transactionRepository.depositAmount(...)`.
  * Log event: call `transactionLogService.logDeposit(...)`.

#### 📄 File: `src/main/java/com/gdb/services/AccountService.java`
* **Method: `closeAccount(CloseAccountDTO closeAccountDTO)`**
  * Fetch account from the repository: `accountRepository.getAccountByAccountNumber(closeAccountDTO.getAccountNumber())`.
  * Check if the account is active. If it is already inactive, throw `AccountNotActiveException`.
  * If active, call the repository's `closeAccount` method passing the account number and current timestamp `LocalDateTime.now()`.
  * Return a successful `CloseAccountResponse` indicating closure.

---

## 📐 Business Rules & Tier Thresholds

Use `AccountPrivilegeManager` helper classes to validate these constraints:

| Privilege Tier | Minimum Balance Constraint | Daily Transfer Limit |
| :--- | :--- | :--- |
| **SILVER** | Rs. `10,000` | Rs. `25,000` |
| **GOLD** | Rs. `15,000` | Rs. `50,000` |
| **PLATINUM** | Rs. `20,000` | Rs. `100,000` |

---

## 🛠️ Verification & Run Instructions

1. **Compile Code:**
   ```powershell
   mvn compile
   ```
2. **Run JUnit Tests:**
   The test suite includes comprehensive tests. Ensure your code compiles and passes tests:
   ```powershell
   mvn test
   ```
3. **Run Console App to Verify Manually:**
   ```powershell
   mvn exec:java "-Dexec.mainClass=Main"
   ```
