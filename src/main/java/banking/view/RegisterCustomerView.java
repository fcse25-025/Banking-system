package banking.view;

import banking.dao.CustomerDAO;
import banking.model.Customer;
import banking.model.IndividualCustomer;
import banking.model.CompanyCustomer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterCustomerView {

    private CustomerDAO customerDAO = new CustomerDAO();

    public void show(Stage stage) {

        Label title = new Label("BOTSWANA NATIONAL BANK");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label branch = new Label("FRANCISTOWN BRANCH");
        branch.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label heading = new Label("REGISTER CUSTOMER");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ComboBox<String> customerType = new ComboBox<>();
        customerType.getItems().addAll(
                "Individual",
                "Company"
        );
        customerType.setPromptText("Select customer type");
        customerType.setPrefWidth(250);

        // ---------------- INDIVIDUAL FIELDS ----------------

        TextField firstName = new TextField();
        firstName.setPromptText("First name");

        TextField surname = new TextField();
        surname.setPromptText("Surname");

        TextField nationalID = new TextField();
        nationalID.setPromptText("National ID");

        // ---------------- COMPANY FIELDS ----------------

        TextField companyName = new TextField();
        companyName.setPromptText("Company name");

        TextField registrationNumber = new TextField();
        registrationNumber.setPromptText("Registration number");

        // ---------------- COMMON FIELDS ----------------

        TextField address = new TextField();
        address.setPromptText("Address");

        TextField phone = new TextField();
        phone.setPromptText("Phone number");

        TextField email = new TextField();
        email.setPromptText("Email address");

        PasswordField password = new PasswordField();
        password.setPromptText("Customer password");

        Button register = new Button("Register Customer");
        Button back = new Button("Back");

        Label message = new Label();

        // --------------------------------------------------
        // INDIVIDUAL FORM
        // --------------------------------------------------

        GridPane individualFields = new GridPane();

        individualFields.setHgap(10);
        individualFields.setVgap(10);
        individualFields.setAlignment(Pos.CENTER);

        individualFields.add(new Label("First Name:"), 0, 0);
        individualFields.add(firstName, 1, 0);

        individualFields.add(new Label("Surname:"), 0, 1);
        individualFields.add(surname, 1, 1);

        individualFields.add(new Label("National ID:"), 0, 2);
        individualFields.add(nationalID, 1, 2);

        // --------------------------------------------------
        // COMPANY FORM
        // --------------------------------------------------

        GridPane companyFields = new GridPane();

        companyFields.setHgap(10);
        companyFields.setVgap(10);
        companyFields.setAlignment(Pos.CENTER);

        companyFields.add(new Label("Company Name:"), 0, 0);
        companyFields.add(companyName, 1, 0);

        companyFields.add(new Label("Registration Number:"), 0, 1);
        companyFields.add(registrationNumber, 1, 1);

        // --------------------------------------------------
        // COMMON FORM
        // --------------------------------------------------

        GridPane commonFields = new GridPane();

        commonFields.setHgap(10);
        commonFields.setVgap(10);
        commonFields.setAlignment(Pos.CENTER);

        commonFields.add(new Label("Address:"), 0, 0);
        commonFields.add(address, 1, 0);

        commonFields.add(new Label("Phone:"), 0, 1);
        commonFields.add(phone, 1, 1);

        commonFields.add(new Label("Email:"), 0, 2);
        commonFields.add(email, 1, 2);

        commonFields.add(new Label("Password:"), 0, 3);
        commonFields.add(password, 1, 3);

        // --------------------------------------------------
        // MAIN FORM
        // --------------------------------------------------

        VBox form = new VBox(12);

        form.setAlignment(Pos.CENTER);

        Label individualHeading =
                new Label("INDIVIDUAL CUSTOMER DETAILS");

        individualHeading.setStyle("-fx-font-weight: bold;");

        Label companyHeading =
                new Label("COMPANY CUSTOMER DETAILS");

        companyHeading.setStyle("-fx-font-weight: bold;");

        Label commonHeading =
                new Label("CONTACT AND LOGIN DETAILS");

        commonHeading.setStyle("-fx-font-weight: bold;");

        // Initially hide both customer-specific forms
        individualFields.setVisible(false);
        individualFields.setManaged(false);

        individualHeading.setVisible(false);
        individualHeading.setManaged(false);

        companyFields.setVisible(false);
        companyFields.setManaged(false);

        companyHeading.setVisible(false);
        companyHeading.setManaged(false);

        // --------------------------------------------------
        // CHANGE FORM WHEN CUSTOMER TYPE IS SELECTED
        // --------------------------------------------------

        customerType.setOnAction(e -> {

            boolean individual =
                    "Individual".equals(customerType.getValue());

            boolean company =
                    "Company".equals(customerType.getValue());

            individualFields.setVisible(individual);
            individualFields.setManaged(individual);

            individualHeading.setVisible(individual);
            individualHeading.setManaged(individual);

            companyFields.setVisible(company);
            companyFields.setManaged(company);

            companyHeading.setVisible(company);
            companyHeading.setManaged(company);
        });

        form.getChildren().addAll(
                new Label("Customer Type:"),
                customerType,
                individualHeading,
                individualFields,
                companyHeading,
                companyFields,
                commonHeading,
                commonFields
        );

        // --------------------------------------------------
        // REGISTER BUTTON
        // --------------------------------------------------

        register.setOnAction(e -> {

            if (customerType.getValue() == null) {

                message.setText("Please select a customer type.");
                return;
            }

            // Common fields
            if (address.getText().trim().isEmpty()
                    || phone.getText().trim().isEmpty()
                    || email.getText().trim().isEmpty()
                    || password.getText().trim().isEmpty()) {

                message.setText(
                        "Please complete the contact and login details."
                );

                return;
            }

            Customer customer;

            // --------------------------------------------------
            // INDIVIDUAL CUSTOMER
            // --------------------------------------------------

            if (customerType.getValue().equals("Individual")) {

                if (firstName.getText().trim().isEmpty()
                        || surname.getText().trim().isEmpty()
                        || nationalID.getText().trim().isEmpty()) {

                    message.setText(
                            "Please complete all individual customer details."
                    );

                    return;
                }

                customer = new IndividualCustomer(
                        0,
                        address.getText().trim(),
                        phone.getText().trim(),
                        email.getText().trim(),
                        firstName.getText().trim(),
                        surname.getText().trim(),
                        nationalID.getText().trim()
                );

            }

            // --------------------------------------------------
            // COMPANY CUSTOMER
            // --------------------------------------------------

            else {

                if (companyName.getText().trim().isEmpty()
                        || registrationNumber.getText().trim().isEmpty()) {

                    message.setText(
                            "Please complete all company customer details."
                    );

                    return;
                }

                customer = new CompanyCustomer(
                        0,
                        address.getText().trim(),
                        phone.getText().trim(),
                        email.getText().trim(),
                        companyName.getText().trim(),
                        registrationNumber.getText().trim()
                );
            }

            // --------------------------------------------------
            // SAVE CUSTOMER
            // --------------------------------------------------

            boolean saved = customerDAO.saveCustomer(
                    customer,
                    password.getText()
            );

            if (saved) {

                message.setText(
                        "Customer registered successfully. Customer ID: "
                                + customer.getCustomerID()
                );

                // Clear fields
                firstName.clear();
                surname.clear();
                nationalID.clear();

                companyName.clear();
                registrationNumber.clear();

                address.clear();
                phone.clear();
                email.clear();
                password.clear();

                customerType.setValue(null);

                individualFields.setVisible(false);
                individualFields.setManaged(false);

                individualHeading.setVisible(false);
                individualHeading.setManaged(false);

                companyFields.setVisible(false);
                companyFields.setManaged(false);

                companyHeading.setVisible(false);
                companyHeading.setManaged(false);

            } else {

                message.setText(
                        "Customer registration failed. Please try again."
                );
            }
        });

        // --------------------------------------------------
        // BACK BUTTON
        // --------------------------------------------------

        back.setOnAction(e -> {

            DashboardView dashboard = new DashboardView();

            dashboard.show(stage, "Bank Employee");
        });

        VBox layout = new VBox(
                15,
                title,
                branch,
                heading,
                form,
                register,
                back,
                message
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout, 650, 700);

        stage.setTitle(
                "Botswana National Bank - Francistown Branch"
        );

        stage.setScene(scene);
        stage.show();
    }
}