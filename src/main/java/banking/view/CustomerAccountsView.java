
package banking.view;

import banking.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerAccountsView {

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        Label heading = new Label("MY ACCOUNTS");

        int customerID = BankingApplication.getLoggedInCustomerID();

        AccountDAO dao = new AccountDAO();

        ListView<AccountDAO.AccountRecord> accounts = new ListView<>();

        accounts.setItems(
                FXCollections.observableArrayList(
                        dao.getAccountsForCustomer(customerID)
                )
        );

        accounts.setPrefSize(500, 350);

        Button refresh = new Button("Refresh Accounts");
        Button back = new Button("Back");

        refresh.setOnAction(e -> {
            accounts.setItems(
                    FXCollections.observableArrayList(
                            dao.getAccountsForCustomer(customerID)
                    )
            );
        });

        back.setOnAction(e -> {
            new DashboardView().show(stage, "Customer");
        });

        VBox layout = new VBox(
                15, title, branch, heading, accounts, refresh, back
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout, 650, 600);

        stage.setTitle("My Accounts - Botswana National Bank");
        stage.setScene(scene);
        stage.show();
    }
}
