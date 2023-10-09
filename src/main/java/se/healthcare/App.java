package se.healthcare;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import models.Appointment;
import models.Patient;
import models.Staff;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Staff currentStaff;
    private static Patient currentPatient;
    private static Appointment currentAppointment;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml, String... title) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);

        // Check if a title is provided and set it if not null or empty
        if (title != null && title.length > 0 && title[0] != null && !title[0].isEmpty()) {
            ((Stage) scene.getWindow()).setTitle(title[0]);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        launch();
    }

    public static Appointment getCurrentAppointment() {
        return currentAppointment;
    }

    public static void setCurrentAppointment(Appointment appointment) {
        currentAppointment = appointment;
    }

    public static Staff getCurrentStaff() {
        return currentStaff;
    }

    public static void setCurrentStaff(Staff staff) {
        currentStaff = staff;
    }

    public static Patient getCurrentPatient() {
        return currentPatient;
    }

    public static void setCurrentPatient(Patient patient) {
        currentPatient = patient;
    }

}
