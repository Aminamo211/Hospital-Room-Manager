package hospital_room_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import backend.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;


public class PrimaryController {

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void openCreateAccount() throws IOException {
        App.setRoot("create_account");
    }

    // Checks the entered phone number and password against MySQL
    @FXML
    private void handleLogin() {
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();

        if (phone.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter your phone number and password.");
            return;
        }

        String sql =
                "SELECT UserRole FROM Client WHERE Phone = ? AND Pass = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, phone);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String role = result.getString("UserRole");
                    messageLabel.setText("Login successful: " + role);
                    App.setRoot("secondary");
                } else {
                    messageLabel.setText("Incorrect phone number or password.");
                }
            }
        } catch (IOException exception) {
            messageLabel.setText("Could not open the next screen.");
            exception.printStackTrace();
        } catch (Exception exception) {
            messageLabel.setText("Database connection failed.");
            exception.printStackTrace();

        }
    }
}
