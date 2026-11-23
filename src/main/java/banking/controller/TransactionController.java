package banking.controller;

import banking.dao.TransactionDAO;
import banking.model.Transaction;

public class TransactionController {

    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean saveTransaction(Transaction transaction) {
        return transactionDAO.saveTransaction(transaction);
    }

    public Transaction findTransaction(int transactionID) {
        return transactionDAO.findTransaction(transactionID);
    }

    public boolean updateTransaction(Transaction transaction) {
        return transactionDAO.updateTransaction(transaction);
    }

    public boolean deleteTransaction(int transactionID) {
        return transactionDAO.deleteTransaction(transactionID);
    }
}
