package banking.dao;

import banking.model.Account;
import banking.model.SavingsAccount;
import banking.model.ChequeAccount;
import banking.model.InvestmentAccount;
import banking.util.DatabaseConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public static class AccountRecord {
        public int accountID;
        public String accountNumber;
        public int customerID;
        public String accountType;
        public double balance;
        public boolean active;
        public String employerName;
        public String employerAddress;
        public String lastInterestMonth;

        @Override
        public String toString() {
            return accountType + " - " + accountNumber
                    + " (BWP " + String.format("%.2f", balance) + ")";
        }
    }

    public static class PendingRecord {
        public long pendingID;
        public int customerID;
        public int accountID;
        public String accountNumber;
        public String accountType;
        public double amount;
        public String requestDate;

        @Override
        public String toString() {
            return "Customer " + customerID + " - Withdrawal BWP "
                    + String.format("%.2f", amount) + " - " + accountType
                    + " " + accountNumber + " - " + requestDate;
        }
    }

    private Account createAccount(ResultSet rs) throws SQLException {

        String number = rs.getString("accountNumber");
        double balance = rs.getBigDecimal("balance").doubleValue();
        String type = rs.getString("accountType");
        String status = rs.getBoolean("isActive") ? "Active" : "Inactive";
        int customerID = rs.getInt("customerID");

        Account account;

        if ("Savings Account".equals(type)) {

            account = new SavingsAccount(
                    customerID,
                    number,
                    balance,
                    "",
                    type,
                    status,
                    0.0005
            );

        } else if ("Investment Account".equals(type)) {

            account = new InvestmentAccount(
                    customerID,
                    number,
                    balance,
                    "",
                    type,
                    status,
                    0.05,
                    500
            );

        } else if ("Cheque Account".equals(type)) {

            account = new ChequeAccount(
                    customerID,
                    number,
                    balance,
                    "",
                    type,
                    status,
                    rs.getString("employerName"),
                    rs.getString("employerAddress")
            );

        } else {
            throw new SQLException("Unknown account type: " + type);
        }

        account.setCustomerID(customerID);

        return account;
    }

    private AccountRecord readAccount(ResultSet rs) throws SQLException {

        AccountRecord a = new AccountRecord();

        a.accountID = rs.getInt("accountID");
        a.accountNumber = rs.getString("accountNumber");
        a.customerID = rs.getInt("customerID");
        a.accountType = rs.getString("accountType");
        a.balance = rs.getBigDecimal("balance").doubleValue();
        a.active = rs.getBoolean("isActive");
        a.employerName = rs.getString("employerName");
        a.employerAddress = rs.getString("employerAddress");
        a.lastInterestMonth = rs.getString("lastInterestMonth");

        return a;
    }

    public boolean saveAccount(Account account) {

        if (account == null || account.getCustomerID() <= 0) {
            return false;
        }

        if (account.getAccountNumber() == null
                || account.getAccountNumber().trim().isEmpty()) {
            return false;
        }

        if (account.getBalance() < 0) {
            return false;
        }

        if ("Investment Account".equals(account.getAccountType())
                && account.getBalance() < 500) {
            return false;
        }

        String employerName = null;
        String employerAddress = null;

        if (account instanceof ChequeAccount) {

            ChequeAccount cheque = (ChequeAccount) account;

            employerName = cheque.getCompany();
            employerAddress = cheque.getCompanyAddress();

            if (employerName == null || employerName.trim().isEmpty()
                    || employerAddress == null
                    || employerAddress.trim().isEmpty()) {
                return false;
            }
        }

        String sql = "INSERT INTO `Account` "
                + "(accountNumber, customerID, accountType, balance, "
                + "isActive, employerName, employerAddress) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());
            ps.setInt(2, account.getCustomerID());
            ps.setString(3, account.getAccountType());
            ps.setBigDecimal(4, BigDecimal.valueOf(account.getBalance()));
            ps.setBoolean(5, "Active".equalsIgnoreCase(account.getStatus()));
            ps.setString(6, employerName);
            ps.setString(7, employerAddress);

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Account findAccount(String accountNumber) {

        String sql = "SELECT * FROM `Account` WHERE accountNumber = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return createAccount(rs);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean updateAccount(Account account) {

        if (account == null || account.getAccountNumber() == null) {
            return false;
        }

        String employerName = null;
        String employerAddress = null;

        if (account instanceof ChequeAccount) {

            ChequeAccount cheque = (ChequeAccount) account;

            employerName = cheque.getCompany();
            employerAddress = cheque.getCompanyAddress();

            if (employerName == null || employerName.trim().isEmpty()
                    || employerAddress == null
                    || employerAddress.trim().isEmpty()) {
                return false;
            }
        }

        String sql = "UPDATE `Account` SET "
                + "accountType = ?, balance = ?, isActive = ?, "
                + "employerName = ?, employerAddress = ? "
                + "WHERE accountNumber = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getAccountType());
            ps.setBigDecimal(2, BigDecimal.valueOf(account.getBalance()));
            ps.setBoolean(3, "Active".equalsIgnoreCase(account.getStatus()));
            ps.setString(4, employerName);
            ps.setString(5, employerAddress);
            ps.setString(6, account.getAccountNumber());

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(String accountNumber) {

        String sql = "DELETE FROM `Account` WHERE accountNumber = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean openAccount(int customerID, String type,
                               double initialDeposit,
                               String employerName,
                               String employerAddress) {

        if (initialDeposit < 0) {
            return false;
        }

        if ("Investment Account".equals(type) && initialDeposit < 500) {
            return false;
        }

        if ("Cheque Account".equals(type)
                && (employerName == null || employerName.trim().isEmpty()
                || employerAddress == null
                || employerAddress.trim().isEmpty())) {
            return false;
        }

        String insert = "INSERT INTO `Account` "
                + "(accountNumber, customerID, accountType, balance, "
                + "isActive, employerName, employerAddress) "
                + "VALUES (?, ?, ?, ?, TRUE, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                int accountID;

                try (PreparedStatement ps = con.prepareStatement(
                        insert, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, "TEMP-" + java.util.UUID.randomUUID());
                    ps.setInt(2, customerID);
                    ps.setString(3, type);
                    ps.setBigDecimal(4, BigDecimal.valueOf(initialDeposit));
                    ps.setString(5, employerName);
                    ps.setString(6, employerAddress);

                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {

                        if (!keys.next()) {
                            throw new SQLException("Could not generate account ID.");
                        }

                        accountID = keys.getInt(1);
                    }
                }

                String number = "BNB" + String.format("%06d", accountID);

                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE `Account` SET accountNumber = ? WHERE accountID = ?")) {

                    ps.setString(1, number);
                    ps.setInt(2, accountID);
                    ps.executeUpdate();
                }

                if (initialDeposit > 0) {

                    insertTransaction(
                            con,
                            customerID,
                            number,
                            "Initial Deposit",
                            initialDeposit,
                            "Completed",
                            "Account opened with initial deposit of BWP "
                                    + String.format("%.2f", initialDeposit)
                    );
                }

                con.commit();
                return true;

            } catch (SQLException ex) {

                con.rollback();
                ex.printStackTrace();
                return false;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<AccountRecord> getAccountsForCustomer(int customerID) {

        return getAccounts(
                "SELECT * FROM `Account` WHERE customerID = ? ORDER BY accountID",
                customerID
        );
    }

    public List<AccountRecord> getAllAccounts() {

        return getAccounts(
                "SELECT * FROM `Account` ORDER BY accountID",
                null
        );
    }

    private List<AccountRecord> getAccounts(String sql, Integer customerID) {

        List<AccountRecord> result = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (customerID != null) {
                ps.setInt(1, customerID);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    result.add(readAccount(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public boolean setAccountStatus(int accountID, boolean active) {

        String sql = "UPDATE `Account` SET isActive = ? WHERE accountID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, active);
            ps.setInt(2, accountID);

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deposit(int customerID, int accountID, double amount) {

        if (amount <= 0) {
            return false;
        }

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                AccountRecord a = findAccount(con, accountID);

                if (a == null || a.customerID != customerID || !a.active) {
                    throw new SQLException("Account unavailable or inactive.");
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE `Account` SET balance = balance + ? WHERE accountID = ?")) {

                    ps.setBigDecimal(1, BigDecimal.valueOf(amount));
                    ps.setInt(2, accountID);
                    ps.executeUpdate();
                }

                insertTransaction(
                        con,
                        customerID,
                        a.accountNumber,
                        "Deposit",
                        amount,
                        "Completed",
                        "Deposit of BWP " + String.format("%.2f", amount)
                                + " into account " + a.accountNumber
                );

                con.commit();
                return true;

            } catch (SQLException ex) {

                con.rollback();
                ex.printStackTrace();
                return false;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean requestWithdrawal(int customerID, int accountID, double amount) {

        if (amount <= 0) {
            return false;
        }

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                AccountRecord a = findAccount(con, accountID);

                if (a == null || a.customerID != customerID || !a.active
                        || "Savings Account".equals(a.accountType)
                        || a.balance < amount) {

                    throw new SQLException(
                            "Withdrawal is not allowed for this account or balance."
                    );
                }

                String sql = "INSERT INTO PendingWithdrawal "
                        + "(customerID, accountID, amount) VALUES (?, ?, ?)";

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, customerID);
                    ps.setInt(2, accountID);
                    ps.setBigDecimal(3, BigDecimal.valueOf(amount));
                    ps.executeUpdate();
                }

                insertTransaction(
                        con,
                        customerID,
                        a.accountNumber,
                        "Withdrawal Request",
                        amount,
                        "Pending",
                        "Withdrawal request of BWP "
                                + String.format("%.2f", amount)
                                + " - Pending manager approval"
                );

                con.commit();
                return true;

            } catch (SQLException ex) {

                con.rollback();
                ex.printStackTrace();
                return false;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<PendingRecord> getPendingWithdrawals() {

        List<PendingRecord> list = new ArrayList<>();

        String sql = "SELECT p.pendingID, p.customerID, p.accountID, "
                + "p.amount, p.requestDate, a.accountNumber, a.accountType "
                + "FROM PendingWithdrawal p "
                + "JOIN `Account` a ON a.accountID = p.accountID "
                + "WHERE p.requestStatus = 'Pending' "
                + "ORDER BY p.requestDate";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                PendingRecord p = new PendingRecord();

                p.pendingID = rs.getLong("pendingID");
                p.customerID = rs.getInt("customerID");
                p.accountID = rs.getInt("accountID");
                p.amount = rs.getBigDecimal("amount").doubleValue();
                p.requestDate = rs.getString("requestDate");
                p.accountNumber = rs.getString("accountNumber");
                p.accountType = rs.getString("accountType");

                list.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public boolean decideWithdrawal(long pendingID, boolean approve) {

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                String sql = "SELECT p.customerID, p.accountID, p.amount, "
                        + "p.requestStatus, a.accountNumber, a.accountType, "
                        + "a.balance, a.isActive "
                        + "FROM PendingWithdrawal p "
                        + "JOIN `Account` a ON a.accountID = p.accountID "
                        + "WHERE p.pendingID = ? FOR UPDATE";

                int customerID;
                int accountID;
                double amount;
                String status;
                String number;
                String type;
                double balance;
                boolean active;

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setLong(1, pendingID);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException("Pending request not found.");
                        }

                        customerID = rs.getInt("customerID");
                        accountID = rs.getInt("accountID");
                        amount = rs.getBigDecimal("amount").doubleValue();
                        status = rs.getString("requestStatus");
                        number = rs.getString("accountNumber");
                        type = rs.getString("accountType");
                        balance = rs.getBigDecimal("balance").doubleValue();
                        active = rs.getBoolean("isActive");
                    }
                }

                if (!"Pending".equals(status)) {
                    throw new SQLException("Request has already been processed.");
                }

                String newStatus = approve ? "Approved" : "Rejected";

                if (approve) {

                    if (!active || "Savings Account".equals(type)
                            || balance < amount) {

                        throw new SQLException(
                                "Account inactive or insufficient balance."
                        );
                    }

                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE `Account` SET balance = balance - ? "
                                    + "WHERE accountID = ? AND balance >= ?")) {

                        ps.setBigDecimal(1, BigDecimal.valueOf(amount));
                        ps.setInt(2, accountID);
                        ps.setBigDecimal(3, BigDecimal.valueOf(amount));

                        if (ps.executeUpdate() != 1) {
                            throw new SQLException("Balance update failed.");
                        }
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE PendingWithdrawal SET requestStatus = ? "
                                + "WHERE pendingID = ? AND requestStatus = 'Pending'")) {

                    ps.setString(1, newStatus);
                    ps.setLong(2, pendingID);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Request status update failed.");
                    }
                }

                insertTransaction(
                        con,
                        customerID,
                        number,
                        "Withdrawal",
                        amount,
                        newStatus,
                        "Withdrawal of BWP "
                                + String.format("%.2f", amount)
                                + " from account " + number
                                + " - " + newStatus + " by Manager"
                );

                con.commit();
                return true;

            } catch (SQLException ex) {

                con.rollback();
                ex.printStackTrace();
                return false;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int payMonthlyInterest() {

        String month = YearMonth.now().toString();
        int count = 0;

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM `Account` "
                            + "WHERE isActive = TRUE "
                            + "AND accountType IN "
                            + "('Savings Account', 'Investment Account') FOR UPDATE");
                 ResultSet rs = ps.executeQuery()) {

                List<AccountRecord> accounts = new ArrayList<>();

                while (rs.next()) {
                    accounts.add(readAccount(rs));
                }

                for (AccountRecord a : accounts) {

                    if (month.equals(a.lastInterestMonth)) {
                        continue;
                    }

                    double rate = "Savings Account".equals(a.accountType)
                            ? 0.0005 : 0.05;

                    double interest = BigDecimal.valueOf(a.balance)
                            .multiply(BigDecimal.valueOf(rate))
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();

                    try (PreparedStatement update = con.prepareStatement(
                            "UPDATE `Account` SET balance = balance + ?, "
                                    + "lastInterestMonth = ? WHERE accountID = ?")) {

                        update.setBigDecimal(1, BigDecimal.valueOf(interest));
                        update.setString(2, month);
                        update.setInt(3, a.accountID);
                        update.executeUpdate();
                    }

                    insertTransaction(
                            con,
                            a.customerID,
                            a.accountNumber,
                            "Monthly Interest",
                            interest,
                            "Completed",
                            "Monthly interest paid: BWP "
                                    + String.format("%.2f", interest)
                                    + " to " + a.accountType + " "
                                    + a.accountNumber + " for " + month
                    );

                    count++;
                }

                con.commit();
                return count;

            } catch (SQLException ex) {

                con.rollback();
                ex.printStackTrace();
                return -1;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public List<String> getTransactionsForCustomer(int customerID) {

        return getTransactions(
                "SELECT description, transactionDate, transactionStatus "
                        + "FROM `transaction` WHERE customerID = ? "
                        + "ORDER BY transactionDate DESC",
                customerID
        );
    }

    public List<String> getAllTransactions() {

        return getTransactions(
                "SELECT customerID, description, transactionDate, transactionStatus "
                        + "FROM `transaction` ORDER BY transactionDate DESC",
                null
        );
    }

    private List<String> getTransactions(String sql, Integer customerID) {

        List<String> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (customerID != null) {
                ps.setInt(1, customerID);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String prefix = customerID == null
                            ? "Customer ID: " + rs.getInt("customerID") + " - "
                            : "";

                    list.add(
                            prefix + rs.getString("description")
                                    + " | " + rs.getString("transactionStatus")
                                    + " | " + rs.getString("transactionDate")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    private AccountRecord findAccount(Connection con, int accountID)
            throws SQLException {

        String sql = "SELECT * FROM `Account` WHERE accountID = ? FOR UPDATE";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accountID);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? readAccount(rs) : null;
            }
        }
    }

    private void insertTransaction(
            Connection con,
            int customerID,
            String accountNumber,
            String type,
            double amount,
            String status,
            String description
    ) throws SQLException {

        String sql = "INSERT INTO `transaction` "
                + "(customerID, accountNumber, transactionType, amount, "
                + "transactionStatus, description) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerID);
            ps.setString(2, accountNumber);
            ps.setString(3, type);
            ps.setBigDecimal(4, BigDecimal.valueOf(amount));
            ps.setString(5, status);
            ps.setString(6, description);

            ps.executeUpdate();
        }
    }
}