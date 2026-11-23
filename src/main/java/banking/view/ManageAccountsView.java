
package banking.view;

import banking.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageAccountsView {

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        Label heading = new Label("MANAGE CUSTOMER ACCOUNTS");

        AccountDAO dao = new AccountDAO();

        ListView<AccountDAO.AccountRecord> accountList = new ListView<>();

        accountList.setItems(
                FXCollections.observableArrayList(dao.getAllAccounts())
        );

        accountList.setPrefHeight(350);
        accountList.setPrefWidth(500);

        Button activate = new Button("Activate Account");
        Button deactivate = new Button("Deactivate Account");
        Button refresh = new Button("Refresh");
        Button back = new Button("Back");

        activate.setPrefWidth(200);
        deactivate.setPrefWidth(200);
        refresh.setPrefWidth(200);
        back.setPrefWidth(200);

        activate.setOnAction(e -> changeStatus(
                accountList, dao, true
        ));

        deactivate.setOnAction(e -> changeStatus(
                accountList, dao, false
        ));

        refresh.setOnAction(e -> accountList.setItems(
                FXCollections.observableArrayList(dao.getAllAccounts())
        ));

        back.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, "Bank Employee");
        });

        VBox layout = new VBox(
                12, title, branch, heading,
                accountList, activate, deactivate, refresh, back
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 650, 700);

        stage.setTitle("Botswana National Bank - Francistown Branch");
        stage.setScene(scene);
        stage.show();
    }

    private void changeStatus(
            ListView<AccountDAO.AccountRecord> accountList,
            AccountDAO dao,
            boolean active) {

        AccountDAO.AccountRecord selected =
                accountList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showMessage("Please select an account first.");
            return;
        }

        boolean updated = dao.setAccountStatus(
                selected.accountID, active
        );

        if (updated) {
            showMessage(active
                    ? "Account activated successfully."
                    : "Account deactivated successfully.");

            accountList.setItems(
                    FXCollections.observableArrayList(dao.getAllAccounts())
            );
        } else {
            showMessage("Could not update account status.");
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Banking System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
