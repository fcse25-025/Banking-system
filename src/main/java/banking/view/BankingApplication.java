
package banking.view;

import banking.dao.CustomerDAO;
import banking.dao.BankEmployeeDAO;
import banking.model.Customer;
import javafx.application.Application;
import javafx.stage.Stage;

public class BankingApplication extends Application {

    private static int loggedInCustomerID;

    public static int getLoggedInCustomerID() {
        return loggedInCustomerID;
    }

    public static void showLogin(Stage stage) {

        LoginView loginView = new LoginView();
        loginView.show(stage);

        loginView.getLoginButton().setOnAction(e -> {

            String userID = loginView.getUserID();
            String password = loginView.getPassword();
            String role = loginView.getRole();

            if (userID.isEmpty() || password.isEmpty() || role == null) {
                loginView.showMessage("Please complete all fields.");
                return;
            }

            if (role.equals("Customer")) {

                int customerID;

                try {
                    customerID = Integer.parseInt(userID);
                } catch (NumberFormatException ex) {
                    loginView.showMessage("Please enter a valid Customer ID.");
                    return;
                }

                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer =
                        customerDAO.loginCustomer(customerID, password);

                if (customer == null) {
                    loginView.showMessage("Incorrect Customer ID or password.");
                    return;
                }

                loggedInCustomerID = customer.getCustomerID();

                new DashboardView().show(stage, "Customer");

            } else {

                int employeeID;

                try {
                    employeeID = Integer.parseInt(userID);
                } catch (NumberFormatException ex) {
                    loginView.showMessage("Please enter a valid Employee ID.");
                    return;
                }

                BankEmployeeDAO employeeDAO = new BankEmployeeDAO();

                boolean validLogin =
                        employeeDAO.loginEmployee(employeeID, password, role);

                if (!validLogin) {
                    loginView.showMessage("Incorrect Employee ID or password.");
                    return;
                }

                new DashboardView().show(stage, role);
            }
        });
    }

    @Override
    public void start(Stage stage) {
        showLogin(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
