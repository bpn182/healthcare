package models;

/**
 *
 * @author bipin
 */
import java.sql.Date;

public class Appointment {

    private int appointmentID;
    private String note;
    private Date date;
    private String hour;
    private int serviceID;
    private int doctorID;
    private int patientID;

    private Service service;
    private Staff doctor;
    private Patient patient;

    // Constructors
    public Appointment() {
    }

    public Appointment(int appointmentID, String note, Date date, String hour, int serviceID, int doctorID, int patientID) {
        this.appointmentID = appointmentID;
        this.note = note;
        this.date = date;
        this.hour = hour;
        this.serviceID = serviceID;
        this.doctorID = doctorID;
        this.patientID = patientID;

    }

    // Getters and Setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Staff getDoctor() {
        return doctor;
    }

    public void setDoctor(Staff doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    @Override
    public String toString() {
        return "Appointment{"
                + "appointmentID=" + appointmentID
                + ", note='" + note + '\''
                + ", date=" + date
                + ", hour='" + hour + '\''
                + ", serviceID=" + serviceID
                + ", doctorID=" + doctorID
                + '}';
    }
}
