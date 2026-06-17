package com.gdb.services;

import java.time.LocalDate;

import com.gdb.dtos.DepositDTO;
import com.gdb.dtos.TransferDTO;
import com.gdb.dtos.TransferModeDTO;
import com.gdb.dtos.WithdrawDTO;
import com.gdb.dtos.responses.DepositResponse;
import com.gdb.dtos.responses.TransferResponse;
import com.gdb.dtos.responses.WithdrawResponse;
import com.gdb.exceptions.AccountAlreadyExistsException;
import com.gdb.exceptions.AccountNotActiveException;
import com.gdb.exceptions.AccountRecordNotFoundException;
import com.gdb.exceptions.DailyTransferLimitExceededException;
import com.gdb.exceptions.IncorrectPinNumberException;
import com.gdb.exceptions.InsufficientFundsException;
import com.gdb.models.Account;
import com.gdb.models.FundTransfer;
import com.gdb.models.Privilege;
import com.gdb.models.TransferMode;
import com.gdb.repositories.IAccountRepository;
import com.gdb.repositories.ITransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service implementation for handling financial transactions.
 * This class coordinates account lookups, security checks, limit validations,
 * and persistent data updates for withdrawals and fund transfers.
 */
public class TransactionService implements ITransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class);
    private IAccountRepository accountRepository;
    private ITransactionRepository transactionRepository;
    private TransactionLogService transactionLogService;

    public TransactionService(IAccountRepository accountRepository, ITransactionRepository transactionRepository,
            TransactionLogService transactionLogService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionLogService = transactionLogService;
    }

    /**
     * Handles cash withdrawal from an individual account.
     * 
     * @param dto Object containing the account number, withdrawal amount, and PIN.
     * @return WithdrawResponse containing the final balance and any status
     *         warnings.
     * @throws AccountRecordNotFoundException if the account number is invalid.
     * @throws AccountAlreadyExistsException  if an internal state conflict occurs.
     * @throws IncorrectPinNumberException    if the provided PIN does not match the
     *                                        record.
     * @throws InsufficientFundsException     if the balance is too low for the
     *                                        withdrawal.
     * @throws AccountNotActiveException      if the account has been disabled or
     *                                        closed.
     */
    @Override
    public WithdrawResponse withdraw(WithdrawDTO dto) throws AccountRecordNotFoundException,
            AccountAlreadyExistsException, IncorrectPinNumberException, InsufficientFundsException,
            AccountNotActiveException {
        // TODO: Student implementation
        // 1. Fetch account from accountRepository and verify it exists and is active.
        // 2. Check PIN matches the account's PIN.
        // 3. Verify sufficient funds for withdrawal.
        // 4. Check if balance drops below minimum balance tier and flag minimumBalanceWarning if needed.
        // 5. Call transactionRepository.withdrawAmount to persist changes.
        // 6. Log transaction using transactionLogService.logWithdraw.
        // 7. Return the completed response.
        Account account = getAccount(dto.getAccountNumber());
        checkAccountIsActive(account);
        checkPinNumber(account.getPinNumber(), dto.getPinNumber());
        checkSufficientFunds(account.getBalance(), dto.getWithdrawAmount());
        dto.setOriginalBalance(account.getBalance());
        boolean warning = shouldWarnMinimumBalance(account.getBalance(), dto.getWithdrawAmount(), account.getPrivilege());
        double newBalance = performWithdrawTransaction(dto.getAccountNumber(),dto.getWithdrawAmount());
        dto.setMinimumBalanceWarning(warning);
        transactionLogService.logWithdraw(dto.getAccountNumber(),dto.getWithdrawAmount(), newBalance);
        return constructResponse(dto, newBalance);
    }

    /**
     * Facilitates a fund transfer between two distinct accounts.
     * 
     * @param dto Object containing sender, receiver, amount, and authorization
     *            details.
     * @return TransferResponse indicating success and providing the updated sender
     *         balance.
     * @throws AccountRecordNotFoundException      if either account ID does not
     *                                             exist in the store.
     * @throws AccountNotActiveException           if either participating account
     *                                             is currently closed.
     * @throws IncorrectPinNumberException         if the sender's PIN verification
     *                                             fails.
     * @throws InsufficientFundsException          if the sender has inadequate
     *                                             balance for the transfer.
     * @throws DailyTransferLimitExceededException if the account tier limit for the
     *                                             day is breached.
     */
    @Override
    public TransferResponse transfer(TransferDTO dto)
            throws AccountRecordNotFoundException, AccountNotActiveException, IncorrectPinNumberException,
            InsufficientFundsException, DailyTransferLimitExceededException {
        // TODO: Student implementation
        // 1. Fetch both sender and receiver accounts and verify they exist and are active.
        // 2. Validate the sender's PIN.
        // 3. Ensure the sender has sufficient funds.
        // 4. Verify that the transfer amount does not exceed the daily cap for the sender's privilege tier.
        // 5. Check if the resulting balance drops below minimum requirements and set warning flag.
        // 6. Call transactionRepository.transferAmount to execute changes.
        // 7. Log the transfer using transactionLogService.logTransfer.
        // 8. Return response.
        //problems-> how we will know which A/c number is wrong we should
        //catch the exception here only
        Account accountSender=getAccount(dto.getFromAccountNumber());
        checkAccountIsActive(accountSender);
        Account accountReceiver=getAccount(dto.getToAccountNumber());
        checkAccountIsActive(accountReceiver);


        checkPinNumber(accountSender.getPinNumber(), dto.getPinNumber());
        checkSufficientFunds(accountSender.getBalance(),dto.getTransferAmount());
        checkTransferLimit(accountSender,dto.getTransferAmount());

        dto.setMinimumBalanceWarning(shouldWarnMinimumBalance(accountSender.getBalance(),dto.getTransferAmount(),accountSender.getPrivilege()));

        transactionRepository.transferAmount(accountSender.getAccountNumber(),accountReceiver.getAccountNumber(), dto.getTransferAmount());

        //problem-where is fundTransfer object

        dto.setNewBalance(0.0);
        return new TransferResponse(dto);
    }

    //
    // PRIVATE METHODS
    //
    private void checkTransferLimit(Account a, double transferAmount) throws DailyTransferLimitExceededException {
        double currentTotal = transactionLogService.getDailyTransferTotal(a.getAccountNumber(), LocalDate.now());
        double limit = AccountPrivilegeManager.getDailyTransferLimit(a.getPrivilege());
        if (currentTotal + transferAmount > limit) {
            throw new DailyTransferLimitExceededException("Daily transfer limit exceeded");
        }
    }

    private boolean shouldWarnMinimumBalance(double balance, double amount, Privilege privilege) {
        return (balance - amount < AccountPrivilegeManager.getMinimumBalance(privilege));
    }

    private Account getAccount(String accountNumber) throws AccountRecordNotFoundException {
        return accountRepository.getAccountByAccountNumber(accountNumber);
    }

    private WithdrawResponse constructResponse(WithdrawDTO dto, double newBalance) {
        dto.setNewBalance(newBalance);
        return new WithdrawResponse(dto);
    }

    private boolean checkAccountIsActive(Account account) throws AccountNotActiveException {
        if (account.isActive()) {
            return true;
        } else {
            throw new AccountNotActiveException("This account is not Active");
        }
    }

    private boolean checkPinNumber(String validPinNumber, String dtoPinNumber) throws IncorrectPinNumberException {
        if (validPinNumber.equalsIgnoreCase(dtoPinNumber)) {
            return true;
        } else {
            throw new IncorrectPinNumberException("Entered pin number is incorrect");
        }

    }

    private boolean checkSufficientFunds(double balance, double withdrawAmount) throws InsufficientFundsException {
        if (balance - withdrawAmount >= 0) {
            return true;
        } else {
            throw new InsufficientFundsException("Insufficient funds to perform this transaction");
        }

    }

    private double performWithdrawTransaction(String accountNumber, double requestedAmount) throws AccountRecordNotFoundException {
        return transactionRepository.withdrawAmount(accountNumber, requestedAmount);
    }

    private double performTransferTransaction(String fromAccountNumber, String toAccountNumber, double transferAmount)
            throws AccountRecordNotFoundException {
        return transactionRepository.transferAmount(fromAccountNumber, toAccountNumber, transferAmount);
    }

    private double performDepositTransaction(String accountNumber, double depositAmount) throws AccountRecordNotFoundException {
        return transactionRepository.depositAmount(accountNumber, depositAmount);
    }

    private TransferMode mapToTransferMode(TransferModeDTO dtoMode) {
        return TransferMode.valueOf(dtoMode.name());
    }

    private DepositResponse constructDepositResponse(DepositDTO dto, double newBalance) {
        dto.setNewBalance(newBalance);
        return new DepositResponse(dto);
    }

    /**
     * Handles cash deposit into an individual account.
     * 
     * @param dto Object containing the account number, deposit amount, and PIN.
     * @return DepositResponse containing the final balance.
     * @throws AccountRecordNotFoundException if the account number is invalid.
     * @throws IncorrectPinNumberException    if the provided PIN does not match the
     *                                        record.
     * @throws AccountNotActiveException      if the account has been disabled or
     *                                        closed.
     */
    @Override
    public DepositResponse deposit(DepositDTO dto) throws AccountRecordNotFoundException,
            IncorrectPinNumberException, AccountNotActiveException {
        // TODO: Student implementation
        // 1. Fetch account from accountRepository and verify it exists and is active.
        // 2. Validate user authorization by checking the provided PIN.
        // 3. Call transactionRepository.depositAmount to update database.
        // 4. Log the deposit using transactionLogService.logDeposit.
        // 5. Return response

        Account account = getAccount(dto.getAccountNumber());
        checkAccountIsActive(account);
        checkPinNumber(account.getPinNumber(), dto.getPinNumber());

        dto.setOriginalBalance(account.getBalance());
        double newBalance = performDepositTransaction(dto.getAccountNumber(), dto.getDepositAmount());
        transactionLogService.logDeposit(dto.getAccountNumber(), dto.getDepositAmount(), newBalance);
        return constructDepositResponse(dto, newBalance);
    }

}
//        dto.setNewBalance(0.0);
//        dto.setOriginalBalance(0.0);
//        return new DepositResponse(dto);
//    }
//
//}
