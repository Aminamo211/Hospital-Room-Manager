package hospital_room_manager;

import backend.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAccountController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    // Adds roles to the dropdown when the screen opens
    @FXML
    private void initialize() {
        roleBox.getItems().addAll("Nurse", "Doctor");
    }

    // Checks the form and saves the account in MySQL
    @FXML
    private void createAccount() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String role = roleBox.getValue();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmation = confirmPasswordField.getText();

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || role == null
                || dobPicker.getValue() == null
                || phone.isEmpty()
                || password.isEmpty()) {

            messageLabel.setText("Complete all required fields.");
            return;
        }

        if (!password.equals(confirmation)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        String sql =
                "INSERT INTO Client " +
                        "(FirstName, LastName, UserRole, DOB, Phone, Email, Pass) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, role);
            statement.setString(
                    4,
                    dobPicker.getValue().toString()
            );
            statement.setString(5, phone);
            statement.setString(6, email);
            statement.setString(7, password);

            statement.executeUpdate();

            messageLabel.setText(
                    "Account created. Return to login."
            );
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not create account: "
                            + exception.getMessage()
            );
            exception.printStackTrace();
        }
    }

    // Returns to the login screen
    @FXML
    private void backToLogin() throws IOException {
        App.setRoot("primary");
    }
}