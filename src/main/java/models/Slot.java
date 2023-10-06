package models;

/**
 *
 * @author bipin
 */
public class Slot {

    private int slotID;
    private String day;
    private String startHour;
    private String endHour;
    private int doctorID;

    // Constructors
    public Slot() {
    }

    public Slot(String day, String startHour, String endHour, int doctorID) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        this.doctorID = doctorID;
    }

    // Getters and Setters
    public int getSlotID() {
        return slotID;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Slot{"
                + "slotID=" + slotID
                + ", day='" + day + '\''
                + ", startHour='" + startHour + '\''
                + ", endHour='" + endHour + '\''
                + ", doctorID=" + doctorID
                + '}';
    }
}
