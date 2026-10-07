package hospital_room_manager;

import java.io.IOException;

import javafx.fxml.FXML;

public class SecondaryController {

    // Returns the user to the login screen
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
        @FXML
        private void openPatientManagement() throws IOException {
            App.setRoot("patient_management");
        }
    @FXML
    private void openRoomManagement() throws IOException {
        App.setRoot("room_management");
    }
    }
