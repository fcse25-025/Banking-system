
package banking.view;

import banking.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageWithdrawalsView {

    private AccountDAO dao = new AccountDAO();

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        Label heading = new Label("WITHDRAWAL REQUESTS");

        ListView<AccountDAO.PendingRecord> requests = new ListView<>();
        requests.setPrefSize(550, 350);

        loadRequests(requests);

        Button approve = new Button("Approve Withdrawal");
        Button reject = new Button("Reject Withdrawal");
        Button refresh = new Button("Refresh Requests");
        Button back = new Button("Back");

        approve.setPrefWidth(200);
        reject.setPrefWidth(200);
        refresh.setPrefWidth(200);
        back.setPrefWidth(200);

        approve.setOnAction(e -> {

            AccountDAO.PendingRecord selected =
                    requests.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showMessage("Please select a withdrawal request.");
                return;
            }

            dao.decideWithdrawal(selected.pendingID, true);

            showMessage("Withdrawal request approved.");
            loadRequests(requests);
        });

        reject.setOnAction(e -> {

            AccountDAO.PendingRecord selected =
                    requests.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showMessage("Please select a withdrawal request.");
                return;
            }

            dao.decideWithdrawal(selected.pendingID, false);

            showMessage("Withdrawal request rejected.");
            loadRequests(requests);
        });

        refresh.setOnAction(e -> loadRequests(requests));

        back.setOnAction(e -> {
            new DashboardView().show(stage, "Manager");
        });

        VBox layout = new VBox(
                15,
                title,
                branch,
                heading,
                requests,
                approve,
                reject,
                refresh,
                back
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout, 650, 650);

        stage.setTitle("Manager - Withdrawal Requests");
        stage.setScene(scene);
        stage.show();
    }

    private void loadRequests(ListView<AccountDAO.PendingRecord> requests) {
        requests.setItems(
                FXCollections.observableArrayList(
                        dao.getPendingWithdrawals()
                )
        );
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Banking System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
