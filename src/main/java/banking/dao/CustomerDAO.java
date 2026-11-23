package banking.dao;

import banking.model.*;
import banking.util.DatabaseConnection;
import java.sql.*;

public class CustomerDAO {

    // Save customer
    public boolean saveCustomer(Customer customer, String password) {

        String type;
        String first = null, surname = null, nationalID = null;
        String company = null, registration = null;

        if (customer instanceof IndividualCustomer) {
            IndividualCustomer c = (IndividualCustomer) customer;
            type = "Individual";
            first = c.getFirstName();
            surname = c.getSurName();
            nationalID = c.getNationalID();

        } else {
            CompanyCustomer c = (CompanyCustomer) customer;
            type = "Company";
            company = c.getCompanyName();
            registration = c.getRegistrationNumber();
        }

        String sql = "INSERT INTO Customer "
                + "(address, phoneNumber, email, password, customerType, "
                + "firstName, surName, nationalID, companyName, registrationNumber) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getAddress());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getEmail());
            ps.setString(4, password);
            ps.setString(5, type);
            ps.setString(6, first);
            ps.setString(7, surname);
            ps.setString(8, nationalID);
            ps.setString(9, company);
            ps.setString(10, registration);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                customer.setCustomerID(keys.getInt(1));
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Find customer
    public Customer findCustomer(int customerID) {

        String sql = "SELECT * FROM Customer WHERE customerID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString("customerType").equals("Individual")) {
                    return new IndividualCustomer(
                            customerID, rs.getString("address"),
                            rs.getString("phoneNumber"), rs.getString("email"),
                            rs.getString("firstName"), rs.getString("surName"),
                            rs.getString("nationalID"));
                }

                return new CompanyCustomer(
                        customerID, rs.getString("address"),
                        rs.getString("phoneNumber"), rs.getString("email"),
                        rs.getString("companyName"),
                        rs.getString("registrationNumber"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Login customer
    public Customer loginCustomer(int customerID, String password) {

        String sql = "SELECT password FROM Customer WHERE customerID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                return findCustomer(customerID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    // Save customer (for existing controller)
    public boolean saveCustomer(Customer customer) {
        return saveCustomer(customer, "");
    }


    // Update customer
    public boolean updateCustomer(Customer customer) {

        String sql = "UPDATE Customer SET address = ?, phoneNumber = ?, email = ? "
                + "WHERE customerID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getAddress());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getEmail());
            ps.setInt(4, customer.getCustomerID());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Delete customer
    public boolean deleteCustomer(int customerID) {

        String sql = "DELETE FROM Customer WHERE customerID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}