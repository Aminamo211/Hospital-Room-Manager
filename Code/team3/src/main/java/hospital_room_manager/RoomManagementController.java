package hospital_room_manager;

import backend.DatabaseConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoomManagementController {

    @FXML
    private TableView<RoomRow> roomTable;

    @FXML
    private TableColumn<RoomRow, Integer> roomIdColumn;

    @FXML
    private TableColumn<RoomRow, Integer> roomNumberColumn;

    @FXML
    private TableColumn<RoomRow, Integer> floorNumberColumn;

    @FXML
    private TableColumn<RoomRow, String> roomTypeColumn;

    @FXML
    private TableColumn<RoomRow, String> roomStatusColumn;

    @FXML
    private TextField roomNumberField;

    @FXML
    private TextField floorNumberField;

    @FXML
    private ComboBox<String> roomTypeBox;

    @FXML
    private ComboBox<String> roomStatusBox;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        roomTypeBox.getItems().addAll(
                "Standard",
                "Private",
                "ICU",
                "Emergency"
        );

        roomStatusBox.getItems().addAll(
                "Available",
                "Occupied",
                "Closed"
        );

        roomStatusBox.setValue("Available");

        roomIdColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getRoomID()
                ).asObject()
        );

        roomNumberColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getRoomNumber()
                ).asObject()
        );

        floorNumberColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getFloorNumber()
                ).asObject()
        );

        roomTypeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRoomType()
                )
        );

        roomStatusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRoomStatus()
                )
        );

        loadRooms();
    }

    // Loads all rooms from MySQL
    @FXML
    private void loadRooms() {
        roomTable.getItems().clear();

        String sql =
                "SELECT RoomID, RoomNumber, FloorNumber, " +
                        "RoomType, RoomStatus " +
                        "FROM Room ORDER BY RoomNumber";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                roomTable.getItems().add(
                        new RoomRow(
                                result.getInt("RoomID"),
                                result.getInt("RoomNumber"),
                                result.getInt("FloorNumber"),
                                result.getString("RoomType"),
                                result.getString("RoomStatus")
                        )
                );
            }

            messageLabel.setText(
                    "Loaded " + roomTable.getItems().size()
                            + " room(s)."
            );
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not load rooms: "
                            + exception.getMessage()
            );
            exception.printStackTrace();
        }
    }

    // Adds a new room to MySQL
    @FXML
    private void addRoom() {
        String roomNumberText =
                roomNumberField.getText().trim();

        String floorNumberText =
                floorNumberField.getText().trim();

        String roomType = roomTypeBox.getValue();
        String roomStatus = roomStatusBox.getValue();

        if (roomNumberText.isEmpty()
                || floorNumberText.isEmpty()) {

            messageLabel.setText(
                    "Enter a room number and floor number."
            );
            return;
        }

        String sql =
                "INSERT INTO Room " +
                        "(RoomNumber, FloorNumber, RoomType, RoomStatus) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    Integer.parseInt(roomNumberText)
            );

            statement.setInt(
                    2,
                    Integer.parseInt(floorNumberText)
            );

            statement.setString(3, roomType);
            statement.setString(4, roomStatus);

            statement.executeUpdate();

            messageLabel.setText("Room added successfully.");
            clearForm();
            loadRooms();
        } catch (NumberFormatException exception) {
            messageLabel.setText(
                    "Room number and floor must be numbers."
            );
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not add room: "
                            + exception.getMessage()
            );
            exception.printStackTrace();
        }
    }

    // Deletes the selected room
    @FXML
    private void deleteRoom() {
        RoomRow selected =
                roomTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText(
                    "Select a room before deleting."
            );
            return;
        }

        String sql = "DELETE FROM Room WHERE RoomID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, selected.getRoomID());
            statement.executeUpdate();

            messageLabel.setText("Room deleted.");
            loadRooms();
        } catch (Exception exception) {
            messageLabel.setText(
                    "Could not delete room. It may have a patient assigned."
            );
            exception.printStackTrace();
        }
    }

    private void clearForm() {
        roomNumberField.clear();
        floorNumberField.clear();
        roomTypeBox.setValue(null);
        roomStatusBox.setValue("Available");
    }

    @FXML
    private void backToDashboard() throws IOException {
        App.setRoot("secondary");
    }

    // Represents one row in the room table
    public static class RoomRow {

        private final int roomID;
        private final int roomNumber;
        private final int floorNumber;
        private final String roomType;
        private final String roomStatus;

        public RoomRow(
                int roomID,
                int roomNumber,
                int floorNumber,
                String roomType,
                String roomStatus
        ) {
            this.roomID = roomID;
            this.roomNumber = roomNumber;
            this.floorNumber = floorNumber;
            this.roomType = roomType;
            this.roomStatus = roomStatus;
        }

        public int getRoomID() {
            return roomID;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public int getFloorNumber() {
            return floorNumber;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getRoomStatus() {
            return roomStatus;
        }
    }
}
