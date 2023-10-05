package models;

/**
 *
 * @author bipin
 */
import java.sql.Date;

public class Patient {

    private int patientID;
    private String fullName;
    private Date dob;
    private String gender;
    private String address;
    private String contact;
    private String medicare;

    public Patient() {
    }

    public Patient(String fullName, Date dob, String gender, String address, String contact, String medicare) {
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.medicare = medicare;
    }

    public Patient(int patientID, String fullName, Date dob, String gender, String address, String contact, String medicare) {
        this.patientID = patientID;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.medicare = medicare;
    }
    
    

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMedicare() {
        return medicare;
    }

    public void setMedicare(String medicare) {
        this.medicare = medicare;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientID=" + patientID + ", fullName=" + fullName + ", dob=" + dob + ", gender=" + gender + ", address=" + address + ", contact=" + contact + ", medicare=" + medicare + '}';
    }

}
