
package models;

/**
 *
 * @author bipin
 */
public class EHR {

    private int ehrID;
    private String currentMedications;
    private String condition;
    private String allergens;
    private String note;
    private int patientID;

    // Default Constructor
    public EHR() {
    }

    // Parameterized Constructor
    public EHR(String currentMedications, String condition, String allergens, String note, int patientID) {
        this.currentMedications = currentMedications;
        this.condition = condition;
        this.allergens = allergens;
        this.note = note;
        this.patientID = patientID;
    }

    public EHR(int ehrID, String currentMedications, String condition, String allergens, String note, int patientID) {
        this.ehrID = ehrID;
        this.currentMedications = currentMedications;
        this.condition = condition;
        this.allergens = allergens;
        this.note = note;
        this.patientID = patientID;
    }

    // Getters and Setters
    public int getEhrID() {
        return ehrID;
    }

    public void setEhrID(int ehrID) {
        this.ehrID = ehrID;
    }

    public String getCurrentMedications() {
        return currentMedications;
    }

    public void setCurrentMedications(String currentMedications) {
        this.currentMedications = currentMedications;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    @Override
    public String toString() {
        return "EHR{" + "ehrID=" + ehrID + ", currentMedications=" + currentMedications + ", condition=" + condition + ", allergens=" + allergens + ", note=" + note + ", patientID=" + patientID + '}';
    }

}
