
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

public class ViewTransactionsView {

    public void show(Stage stage, String role) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        Label heading = new Label("TRANSACTION HISTORY");

        AccountDAO dao = new AccountDAO();

        ListView<String> transactions = new ListView<>();

        transactions.setItems(
                FXCollections.observableArrayList(
                        dao.getAllTransactions()
                )
        );

        transactions.setPrefSize(550, 400);

        Button refresh = new Button("Refresh Transactions");
        Button back = new Button("Back");

        refresh.setOnAction(e -> {
            transactions.setItems(
                    FXCollections.observableArrayList(
                            dao.getAllTransactions()
                    )
            );
        });

        back.setOnAction(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.show(stage, role);
        });

        VBox layout = new VBox(
                15, title, branch, heading,
                transactions, refresh, back
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout, 700, 600);

        stage.setTitle("Botswana National Bank - Francistown Branch");
        stage.setScene(scene);
        stage.show();
    }
}
