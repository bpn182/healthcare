package database;

import DAO.StaffDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static Connection connection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "HealthLink";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Create a connection to the MySQL server without specifying a database
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connected to the MySQL server.");

                // Checking if the database exists
                if (!checkDatabaseExists(DB_NAME)) {
                    // If the database doesn't exist, create it along with tables
                    createDatabaseAndTables();
                    connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
                    StaffDAO sdao = new StaffDAO(connection);
                    sdao.createDefaultAdmin();
                    
                } else {
                    // Connecting to the "HealthLink" database
                    connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
                    System.out.println("Connected to the database.");
                }

            }
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection.");
                e.printStackTrace();
            }
        }
    }

    private static boolean checkDatabaseExists(String dbName) {
        try (Connection tempConnection = DriverManager.getConnection(DB_URL + dbName, DB_USER, DB_PASSWORD)) {
            return true;
        } catch (SQLException e) {
            // Database does not exist or connection failed
            return false;
        }
    }

    private static void createDatabaseAndTables() {
        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            // Execute the SQL script to create the database and tables

            String createDatabaseSQL = "CREATE SCHEMA IF NOT EXISTS `HealthLink` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci";
            statement.executeUpdate(createDatabaseSQL);

            // Create the patient table
            String createPatientsTableSQL = "CREATE TABLE IF NOT EXISTS `HealthLink`.`patients` (`patientID` INT NOT NULL AUTO_INCREMENT, `fullName` VARCHAR(255) NOT NULL, `dob` DATE NOT NULL, `gender` ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL, `address` VARCHAR(255) NOT NULL, `contact` VARCHAR(20) NOT NULL, `medicare` VARCHAR(255) NULL DEFAULT NULL, PRIMARY KEY (`patientID`)) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createPatientsTableSQL);

            String createServicesTableSQL = "CREATE TABLE IF NOT EXISTS `HealthLink`.`services` (`serviceID` INT NOT NULL AUTO_INCREMENT, `serviceName` VARCHAR(255) NOT NULL, `department` VARCHAR(255) NOT NULL, `duration` VARCHAR(255) NOT NULL, `cost` VARCHAR(255) NOT NULL, PRIMARY KEY (`serviceID`)) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createServicesTableSQL);

            String createStaffTableSQL = "CREATE TABLE IF NOT EXISTS `HealthLink`.`staff` ("
                    + "`staffID` INT NOT NULL AUTO_INCREMENT,"
                    + "`fullName` VARCHAR(255) NOT NULL,"
                    + "`licence` VARCHAR(255) NOT NULL,"
                    + "`password` VARCHAR(255) NOT NULL,"
                    + "`phone` VARCHAR(255) NOT NULL,"
                    + "`role` VARCHAR(255) NOT NULL,"
                    + "`department` VARCHAR(255) NOT NULL,"
                    + "`username` VARCHAR(255) NOT NULL UNIQUE,"
                    + "PRIMARY KEY (`staffID`)"
                    + ") ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createStaffTableSQL);

            String createSlotsTableSQL = "CREATE TABLE IF NOT EXISTS `HealthLink`.`slots` ("
                    + "`slotID` INT NOT NULL AUTO_INCREMENT, "
                    + "`day` ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL, "
                    + "`startHour` VARCHAR(8) NOT NULL, "
                    + "`endHour` VARCHAR(8) NOT NULL, "
                    + "`doctorID` INT NOT NULL, "
                    + "PRIMARY KEY (`slotID`), "
                    + "FOREIGN KEY (`doctorID`) REFERENCES `staff`(`staffID`)"
                    + ") ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createSlotsTableSQL);

            String createEHRTableSQL = "CREATE TABLE IF NOT EXISTS `HealthLink`.`ehr` ("
                    + "`EHRID` INT NOT NULL AUTO_INCREMENT, "
                    + "`currentMedications` TEXT, "
                    + "`condition` TEXT, "
                    + "`allergens` TEXT, "
                    + "`note` TEXT, "
                    + "`patientID` INT NOT NULL, "
                    + "PRIMARY KEY (`EHRID`), "
                    + "FOREIGN KEY (`patientID`) REFERENCES `patients`(`patientID`)"
                    + ") ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createEHRTableSQL);

            String createAppointmentsTableSQL = "CREATE TABLE IF NOT EXISTS `HealthLink`.`appointments` ("
                    + "`appointmentID` INT NOT NULL AUTO_INCREMENT, "
                    + "`note` TEXT, "
                    + "`date` DATE NOT NULL, "
                    + "`hour` VARCHAR(10) NOT NULL, "
                    + "`serviceID` INT, "
                    + "`doctorID` INT, "
                    + "`patientID` INT, "
                    + "PRIMARY KEY (`appointmentID`), "
                    + "FOREIGN KEY (`serviceID`) REFERENCES `services`(`serviceID`), "
                    + "FOREIGN KEY (`doctorID`) REFERENCES `staff`(`staffID`), "
                    + "FOREIGN KEY (`patientID`) REFERENCES `patients`(`patientID`)"
                    + ") ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
            statement.executeUpdate(createAppointmentsTableSQL);

            System.out.println("Database and tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating database and tables.");
            e.printStackTrace();
        }
    }

}
