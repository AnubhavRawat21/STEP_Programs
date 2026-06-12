import com.gdb.apis.integrations.*;
import com.gdb.controllers.AccountController;
import com.gdb.controllers.IAccountController;
import com.gdb.controllers.ITransactionController;
import com.gdb.controllers.TransactionController;
import com.gdb.dtos.OpenCurrentAccountDTO;
import com.gdb.dtos.OpenSavingsAccountDTO;
import com.gdb.repositories.*;
import com.gdb.services.AccountService;
import com.gdb.services.AccountServiceCurrentImpl;
import com.gdb.services.AccountServiceImplFactory;
import com.gdb.services.AccountServiceSavingsImpl;
import com.gdb.services.IAccountService;
import com.gdb.services.ITransactionService;
import com.gdb.services.TransactionLogService;
import com.gdb.services.TransactionService;
import com.gdb.ui.AccountUI;
import com.gdb.ui.HomeUI;
import com.gdb.ui.TransactionUI;
import com.gdb.utils.AccountValidator;
import com.gdb.utils.TransactionValidator;

import com.gdb.utils.DataSeeder;

public class Main {
    public static void main(String[] args) {

        // Seed large data into Store
        DataSeeder.seed(10000);

        // Initialize the validators
        AccountValidator accountValidator = new AccountValidator();
        TransactionValidator transactionValidator = new TransactionValidator();

        // Initialize the repositories
        IAccountRepository accountRepository = new AccountRepository();
        ITransactionRepository transactionRepository = new TransactionRepository();

        // Initialize the services
        IAadharApiService aadharApiService = new StubAadharApiService();
        ICompanyApiService companyApiService = new HttpCompanyApiService();

        // Strategy Registration for AccountService
        AccountServiceImplFactory.getInstance().register(OpenSavingsAccountDTO.class,
                new AccountServiceSavingsImpl(accountRepository, aadharApiService));
        AccountServiceImplFactory.getInstance().register(OpenCurrentAccountDTO.class,
                new AccountServiceCurrentImpl(accountRepository, companyApiService));

        IAccountService accountService = new AccountService(accountRepository);
        TransactionLogService transactionLogService = new TransactionLogService();
        ITransactionService transactionService = new TransactionService(accountRepository, transactionRepository,
                transactionLogService);

        // Initialize the controllers
        ITransactionController transactionController = new TransactionController(transactionService,
                transactionValidator);
        IAccountController accountController = new AccountController(accountService, accountValidator);

        // Initialize the UI
        AccountUI accountUI = new AccountUI(accountController, accountValidator);
        TransactionUI transactionUI = new TransactionUI(transactionController, transactionValidator);

        var homeUI = new HomeUI(accountUI, transactionUI);

        homeUI.showUI();
    }
}
