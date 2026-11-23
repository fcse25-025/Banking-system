package banking.controller;

import banking.dao.AccountDAO;
import banking.model.Account;

public class AccountController {

    private AccountDAO accountDAO = new AccountDAO();

    public boolean saveAccount(Account account) {
        return accountDAO.saveAccount(account);
    }

    public Account findAccount(String accountNumber) {
        return accountDAO.findAccount(accountNumber);
    }

    public boolean updateAccount(Account account) {
        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(String accountNumber) {
        return accountDAO.deleteAccount(accountNumber);
    }
}