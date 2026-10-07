package backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents an assignment between a patient and a staff user.
 */
@Entity
public class PatientUserJunction {

    // Unique ID for this patient-to-user assignment
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int PatientUserJunctionID;

    // ID of the assigned patient
    private int PatientID;

    // ID of the assigned staff user
    private int UserID;

    // Time when the assignment started
    private String AssignmentTime;

    // Time when the assignment ended
    private String AssignmentEnd;

    // Empty constructor required by Jakarta Persistence
    public PatientUserJunction() {
    }

    // Constructor for creating a patient-to-user assignment
    public PatientUserJunction(int pi, int ui) {
        this.PatientID = pi;
        this.UserID = ui;
    }

    // Gets the junction record ID
    public int getPatientUserJunctionID() {
        return PatientUserJunctionID;
    }

    // Sets the junction record ID
    public void setPatientUserJunctionID(int pujID) {
        this.PatientUserJunctionID = pujID;
    }

    // Gets the patient ID
    public int getPatientID() {
        return PatientID;
    }

    // Sets the patient ID
    public void setPatientID(int patientID) {
        this.PatientID = patientID;
    }

    // Gets the staff user ID
    public int getUserID() {
        return UserID;
    }

    // Sets the staff user ID
    public void setUserID(int userID) {
        this.UserID = userID;
    }

    // Gets the assignment start time
    public String getAssignmentTime() {
        return AssignmentTime;
    }

    // Sets the assignment start time
    public void setAssignmentTime(String assignmentTime) {
        this.AssignmentTime = assignmentTime;
    }

    // Gets the assignment end time
    public String getAssignmentEnd() {
        return AssignmentEnd;
    }

    // Sets the assignment end time
    public void setAssignmentEnd(String assignmentEnd) {
        this.AssignmentEnd = assignmentEnd;
    }
}