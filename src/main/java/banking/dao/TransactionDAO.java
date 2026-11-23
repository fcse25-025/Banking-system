package banking.dao;

import banking.model.Transaction;
import banking.util.DatabaseConnection;

import java.sql.*;

public class TransactionDAO {

    public boolean saveTransaction(Transaction transaction) {

        String sql = "INSERT INTO BankTransaction (accountNumber, transactionDate, amount, transactionType) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getAccountNumber());
            statement.setTimestamp(2, new Timestamp(transaction.getTransactionDate().getTime()));
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getTransactionType());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public Transaction findTransaction(int transactionID) {

        String sql = "SELECT * FROM BankTransaction WHERE transactionID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, transactionID);
            ResultSet result = statement.executeQuery();

            if (result.next()) {

                return new Transaction(
                        result.getInt("transactionID"),
                        result.getString("accountNumber"),
                        result.getTimestamp("transactionDate"),
                        result.getDouble("amount"),
                        result.getString("transactionType")
                );
            }

        } catch (SQLException e) {
            return null;
        }

        return null;
    }

    public boolean updateTransaction(Transaction transaction) {

        String sql = "UPDATE BankTransaction SET accountNumber = ?, transactionDate = ?, amount = ?, transactionType = ? WHERE transactionID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getAccountNumber());
            statement.setTimestamp(2, new Timestamp(transaction.getTransactionDate().getTime()));
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getTransactionType());
            statement.setInt(5, transaction.getTransactionID());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteTransaction(int transactionID) {

        String sql = "DELETE FROM BankTransaction WHERE transactionID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, transactionID);

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }
}