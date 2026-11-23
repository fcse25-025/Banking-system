package banking.dao;

import banking.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public  boolean aithenticate(int userID,String password, String userType){
        String sql;
        if(userType.equalsIgnoreCase("Customer")){
            sql = "SELECT* FROM Customer WHERE customerID =? AND password=?";
        } else if (userType.equalsIgnoreCase("Employee")) {
            sql="SELECT* FROM BankEmployee WHERE employeeID = ? AND password =?";
        } else {
          return false;
        }
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            return results.next();
        } catch (SQLException e){
            return  false;

        }
    }


}
