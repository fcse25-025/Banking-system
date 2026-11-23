
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private TextField customerIDField;
    private PasswordField passwordField;
    private ComboBox<String> roleBox;
    private Button loginButton;
    private Label messageLabel;

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        branch.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        Label roleLabel = new Label("Login As");

        roleBox = new ComboBox<>();
        roleBox.getItems().addAll(
                "Customer",
                "Bank Employee",
                "Manager"
        );
        roleBox.setPromptText("Select user type");
        roleBox.setPrefWidth(250);

        Label customerIDLabel = new Label("User ID");

        customerIDField = new TextField();
        customerIDField.setPromptText("Enter Customer ID or Employee ID");

        Label passwordLabel = new Label("Password");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        loginButton = new Button("Login");
        loginButton.setPrefWidth(200);

        messageLabel = new Label();

        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                title,
                branch,
                roleLabel,
                roleBox,
                customerIDLabel,
                customerIDField,
                passwordLabel,
                passwordField,
                loginButton,
                messageLabel
        );

        Scene scene = new Scene(layout, 550, 550);

        stage.setTitle("Botswana National Bank - Francistown Branch");
        stage.setScene(scene);
        stage.show();
    }

    public String getUserID() {
        return customerIDField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getRole() {
        return roleBox.getValue();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }
}
