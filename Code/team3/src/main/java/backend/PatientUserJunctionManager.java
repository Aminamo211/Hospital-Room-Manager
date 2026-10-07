package backend;

import java.util.HashMap;
import java.util.Map;

public class PatientUserJunctionManager {

    private final Map<Integer, PatientUserJunction> patientUserJunctions =
            new HashMap<>();

    public boolean canViewPatient(long userID, long patientID) {
        for (PatientUserJunction junction : patientUserJunctions.values()) {
            if (junction.getUserID() == userID
                    && junction.getPatientID() == patientID) {
                return true;
            }
        }

        return false;
    }
}