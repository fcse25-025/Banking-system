
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    public void show(Stage stage, String role) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 23px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        branch.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        Label welcome = new Label(role.toUpperCase() + " DASHBOARD");
        welcome.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        // Display the bank heading first
        layout.getChildren().addAll(title, branch, welcome);

        if (role.equals("Customer")) {

            Button viewAccounts = createButton("View My Accounts");

            viewAccounts.setOnAction(e ->
                    new CustomerAccountsView().show(stage));

            Button deposit = createButton("Deposit Money");

            deposit.setOnAction(e ->
                    new CustomerDepositView().show(stage));

            layout.getChildren().add(deposit);
            Button withdrawal = createButton("Request Withdrawal");

            withdrawal.setOnAction(e ->
                    new CustomerWithdrawalView().show(stage));

            layout.getChildren().add(withdrawal);
            Button transactions = createButton("View Transactions");

            transactions.setOnAction(e ->
                    new CustomerTransactionsView().show(stage));

            layout.getChildren().add(transactions);

            layout.getChildren().add(3, viewAccounts);

        } else if (role.equals("Bank Employee")) {

            Button register = createButton("Register Customer");
            register.setOnAction(e ->
                    new RegisterCustomerView().show(stage));

            Button open = createButton("Open Customer Account");
            open.setOnAction(e ->
                    new OpenAccountView().show(stage));

            Button viewAccounts = createButton("View Customer Accounts");
            viewAccounts.setOnAction(e ->
                    new ViewAccountsView().show(stage));

            Button manage = createButton("Manage Accounts");
            manage.setOnAction(e ->
                    new ManageAccountsView().show(stage));

            Button transactions = createButton("View Transactions");
            transactions.setOnAction(e ->
                    new ViewTransactionsView().show(stage, "Bank Employee"));

            layout.getChildren().addAll(
                    register, open, viewAccounts, manage, transactions
            );

        } else if (role.equals("Manager")) {

            Button requests = createButton("View Withdrawal Requests");
            requests.setOnAction(e ->
                    new ManageWithdrawalsView().show(stage));

            Button approve = createButton("Approve Withdrawal");
            approve.setOnAction(e ->
                    new ManageWithdrawalsView().show(stage));

            Button reject = createButton("Reject Withdrawal");
            reject.setOnAction(e ->
                    new ManageWithdrawalsView().show(stage));

            Button transactions = createButton("View All Transactions");
            transactions.setOnAction(e ->
                    new ViewTransactionsView().show(stage, "Manager"));

            Button accounts = createButton("View All Accounts");
            accounts.setOnAction(e ->
                    new ViewAccountsView().show(stage));

            layout.getChildren().addAll(
                    requests, approve, reject, transactions, accounts
            );
        }
        Button logout = createButton("Logout");

        logout.setOnAction(e -> {
            BankingApplication.showLogin(stage);
        });
        layout.getChildren().add(logout);

        Scene scene = new Scene(layout, 600, 700);

        stage.setTitle("Botswana National Bank - Francistown Branch");
        stage.setScene(scene);
        stage.show();
    }

    private Button createButton(String name) {
        Button button = new Button(name);
        button.setPrefWidth(250);
        button.setPrefHeight(35);
        return button;
    }

    private void addButton(VBox layout, String name) {
        layout.getChildren().add(createButton(name));
    }
}
