
package banking.view;

import banking.dao.AccountDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpenAccountView {

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        branch.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label heading = new Label("OPEN CUSTOMER ACCOUNT");

        TextField customerID = new TextField();
        customerID.setPromptText("Enter registered Customer ID");

        ComboBox<String> accountType = new ComboBox<>();
        accountType.getItems().addAll(
                "Savings Account",
                "Cheque Account",
                "Investment Account"
        );
        accountType.setPromptText("Select account type");

        TextField deposit = new TextField();
        deposit.setPromptText("Enter initial deposit");

        TextField employerName = new TextField();
        employerName.setPromptText("Employer / Company Name");

        TextField employerAddress = new TextField();
        employerAddress.setPromptText("Employer / Company Address");

        Label employerNote = new Label(
                "Employer details are required for Cheque Accounts."
        );

        Button openButton = new Button("Open Account");
        Button backButton = new Button("Back");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(12);
        form.setVgap(12);

        form.add(new Label("Customer ID:"), 0, 0);
        form.add(customerID, 1, 0);

        form.add(new Label("Account Type:"), 0, 1);
        form.add(accountType, 1, 1);

        form.add(new Label("Initial Deposit (BWP):"), 0, 2);
        form.add(deposit, 1, 2);

        form.add(new Label("Employer Name:"), 0, 3);
        form.add(employerName, 1, 3);

        form.add(new Label("Employer Address:"), 0, 4);
        form.add(employerAddress, 1, 4);

        form.add(employerNote, 0, 5, 2, 1);

        openButton.setPrefWidth(180);
        backButton.setPrefWidth(180);

        openButton.setOnAction(e -> {

            try {

                if (customerID.getText().trim().isEmpty()
                        || accountType.getValue() == null
                        || deposit.getText().trim().isEmpty()) {

                    showMessage("Please complete the required fields.");
                    return;
                }

                int id = Integer.parseInt(customerID.getText().trim());
                double amount = Double.parseDouble(deposit.getText().trim());

                if (id <= 0 || amount < 0) {
                    showMessage("Enter a valid Customer ID and deposit.");
                    return;
                }

                String type = accountType.getValue();

                String employer = employerName.getText().trim();
                String address = employerAddress.getText().trim();

                if ("Cheque Account".equals(type)
                        && (employer.isEmpty() || address.isEmpty())) {

                    showMessage(
                            "Cheque Account requires employer name and address."
                    );
                    return;
                }

                if ("Investment Account".equals(type) && amount < 500) {
                    showMessage(
                            "Investment Account requires a minimum deposit of BWP 500."
                    );
                    return;
                }

                AccountDAO dao = new AccountDAO();

                boolean saved = dao.openAccount(
                        id, type, amount, employer, address
                );

                if (saved) {
                    showMessage("Account opened successfully!");
                    customerID.clear();
                    accountType.setValue(null);
                    deposit.clear();
                    employerName.clear();
                    employerAddress.clear();
                } else {
                    showMessage(
                            "Account could not be opened. Check the Customer ID and details."
                    );
                }

            } catch (NumberFormatException ex) {
                showMessage("Customer ID and deposit must be numbers.");
            }
        });

        backButton.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, "Bank Employee");
        });

        VBox layout = new VBox(
                15, title, branch, heading, form, openButton, backButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout, 650, 650);

        stage.setTitle("Botswana National Bank - Francistown Branch");
        stage.setScene(scene);
        stage.show();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Banking System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
