
package banking.view;

import banking.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerDepositView {

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        Label heading = new Label("DEPOSIT MONEY");

        int customerID = BankingApplication.getLoggedInCustomerID();

        AccountDAO dao = new AccountDAO();

        ComboBox<AccountDAO.AccountRecord> accountBox = new ComboBox<>();

        accountBox.setItems(
                FXCollections.observableArrayList(
                        dao.getAccountsForCustomer(customerID)
                )
        );

        accountBox.setPromptText("Select account");
        accountBox.setPrefWidth(250);

        TextField amountField = new TextField();
        amountField.setPromptText("Enter deposit amount");

        Button depositButton = new Button("Confirm Deposit");
        Button backButton = new Button("Back");

        depositButton.setOnAction(e -> {

            AccountDAO.AccountRecord selected =
                    accountBox.getValue();

            if (selected == null || amountField.getText().isEmpty()) {
                showMessage("Please select an account and enter an amount.");
                return;
            }

            try {

                double amount = Double.parseDouble(amountField.getText());

                if (amount <= 0) {
                    showMessage("Deposit amount must be greater than zero.");
                    return;
                }

                boolean success = dao.deposit(
                        customerID,
                        selected.accountID,
                        amount
                );

                if (success) {
                    showMessage("Deposit successful!");
                    amountField.clear();

                    accountBox.setItems(
                            FXCollections.observableArrayList(
                                    dao.getAccountsForCustomer(customerID)
                            )
                    );

                } else {
                    showMessage("Deposit failed. Please check your account.");
                }

            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid amount.");
            }
        });

        backButton.setOnAction(e ->
                new DashboardView().show(stage, "Customer"));

        VBox layout = new VBox(
                15,
                title,
                branch,
                heading,
                new Label("Select Account"),
                accountBox,
                new Label("Amount (BWP)"),
                amountField,
                depositButton,
                backButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 600, 600);

        stage.setTitle("Deposit Money - Botswana National Bank");
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

