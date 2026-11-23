
package banking.view;

import banking.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewAccountsView {

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        Label heading = new Label("CUSTOMER ACCOUNTS");

        ListView<AccountDAO.AccountRecord> accountList = new ListView<>();

        AccountDAO dao = new AccountDAO();

        accountList.setItems(
                FXCollections.observableArrayList(dao.getAllAccounts())
        );

        accountList.setPrefHeight(400);
        accountList.setPrefWidth(500);

        Button refresh = new Button("Refresh Accounts");
        Button back = new Button("Back");

        refresh.setOnAction(e -> {
            accountList.setItems(
                    FXCollections.observableArrayList(dao.getAllAccounts())
            );
        });

        back.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, "Bank Employee");
        });

        VBox layout = new VBox(
                15, title, branch, heading, accountList, refresh, back
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout, 650, 600);

        stage.setTitle("Botswana National Bank - Francistown Branch");
        stage.setScene(scene);
        stage.show();
    }
}
