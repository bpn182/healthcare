package utils;

/**
 *
 * @author Chanadra
 */
public class Constants {

    // Enum for Employee Role
    public enum Role {
        STAFF,
        DOCTOR,
        ADMIN;
    }

    // Enum for Employee Role
    public enum Gender {
        MALE,
        FEMALE,
        OTHER;
    }

    // Enum for departments 
    public enum Department {
        STAFF,
        ADMIN,
        CARDIOLOGY,
        EMERGENCY,
        GYNECOLOGY,
        INTERNAL_MEDICINE,
        NEUROLOGY,
        RADIOLOGY,
        SURGERY,
    }
    

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    // Constants for Database Configuration
    public static final String DB_URL = "jdbc:mysql://localhost:3306/HealthLink";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "password";

    // Other Constants
    public static final int MAX_LOGIN_ATTEMPTS = 3;
    public static final int MAX_EMPLOYEE_NAME_LENGTH = 100;
    public static final int HOURLY_RATE = 25;

}

