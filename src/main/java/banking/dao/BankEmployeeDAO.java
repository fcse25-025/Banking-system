
package banking.dao;

import banking.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankEmployeeDAO {

    public boolean loginEmployee(int employeeID, String password, String role) {

        String sql = "SELECT employeeID FROM bankemployee "
                + "WHERE employeeID = ? AND password = ? "
                + "AND TRIM(role) = TRIM(?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeID);
            ps.setString(2, password);
            ps.setString(3, role.trim());

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Employee login error: " + e.getMessage());
        }

        return false;
    }
}
