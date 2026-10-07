package hospital_room_manager;

import backend.DatabaseConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class PatientManagementController {

    @FXML
    private TableView<PatientRow> patientTable;

    @FXML
    private TableColumn<PatientRow, Integer> patientIdColumn;

    @FXML
    private TableColumn<PatientRow, String> firstNameColumn;

    @FXML
    private TableColumn<PatientRow, String> lastNameColumn;

    @FXML
    private TableColumn<PatientRow, String> phoneColumn;

    @FXML
    private TableColumn<PatientRow, Integer> roomIdColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField roomIdField;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        genderBox.getItems().addAll(
                "Female",
                "Male",
                "Other",
                "Prefer not to say"
        );

        patientIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getPatientID()
                ).asObject()
        );

        firstNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getFirstName()
                )
        );

        lastNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getLastName()
                )
        );

        phoneColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getPhone()
                )
        );

        roomIdColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(
                        data.getValue().getRoomID()
                )
        );

        loadPatients();
    }

    // Loads patients from MySQL into the table
    @FXML
    private void loadPatients() {
        patientTable.getItems().clear();

        String sql =
                "SELECT PatientID, FirstName, LastName, Phone, RoomID " +
                        "FROM Patient ORDER BY PatientID";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Integer roomID = result.getObject(
                        "RoomID",
                        Integer.class
                );

                patientTable.getItems().add(
                        new PatientRow(
                                result.getInt("PatientID"),
                                result.getString("FirstName"),
                                result.getString("LastName"),
                                result.getString("Phone"),
                                roomID
                        )
                );
            }

            messageLabel.setText(
                    "Loaded " + patientTable.getItems().size()
                            + " patient(s)."
            );
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not load patients: "
                            + exception.getMessage()
            );
            exception.printStackTrace();
        }
    }

    // Adds a new patient to MySQL
    @FXML
    private void addPatient() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String gender = genderBox.getValue();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String roomText = roomIdField.getText().trim();

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || dobPicker.getValue() == null
                || phone.isEmpty()
                || password.isEmpty()) {

            messageLabel.setText(
                    "Complete all required patient fields."
            );
            return;
        }

        String sql =
                "INSERT INTO Patient " +
                        "(FirstName, LastName, Gender, DOB, Phone, " +
                        "Email, Pass, RoomID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, gender);
            statement.setString(
                    4,
                    dobPicker.getValue().toString()
            );
            statement.setString(5, phone);
            statement.setString(6, email);
            statement.setString(7, password);

            if (roomText.isEmpty()) {
                statement.setNull(8, Types.INTEGER);
            } else {
                statement.setInt(
                        8,
                        Integer.parseInt(roomText)
                );
            }

            statement.executeUpdate();

            messageLabel.setText("Patient added successfully.");
            clearForm();
            loadPatients();
        } catch (NumberFormatException exception) {
            messageLabel.setText("Room ID must be a number.");
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not add patient: "
                            + exception.getMessage()
            );
            exception.printStackTrace();
        }
    }

    // Deletes the selected patient
    @FXML
    private void deletePatient() {
        PatientRow selected =
                patientTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText(
                    "Select a patient before deleting."
            );
            return;
        }

        String sql = "DELETE FROM Patient WHERE PatientID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, selected.getPatientID());
            statement.executeUpdate();

            messageLabel.setText("Patient deleted.");
            loadPatients();
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not delete patient: "
                            + exception.getMessage()
            );
            exception.printStackTrace();
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        genderBox.setValue(null);
        dobPicker.setValue(null);
        phoneField.clear();
        emailField.clear();
        passwordField.clear();
        roomIdField.clear();
    }

    @FXML
    private void backToDashboard() throws IOException {
        App.setRoot("secondary");
    }

    // Represents one row in the patient table
    public static class PatientRow {

        private final int patientID;
        private final String firstName;
        private final String lastName;
        private final String phone;
        private final Integer roomID;

        public PatientRow(
                int patientID,
                String firstName,
                String lastName,
                String phone,
                Integer roomID
        ) {
            this.patientID = patientID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.roomID = roomID;
        }

        public int getPatientID() {
            return patientID;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhone() {
            return phone;
        }

        public Integer getRoomID() {
            return roomID;
        }
    }
}