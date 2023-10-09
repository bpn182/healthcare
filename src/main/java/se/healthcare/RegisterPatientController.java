/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import database.DatabaseConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Patient;
import DAO.PatientDAO;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import utils.CommonMethods;
import utils.Constants.Gender;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class RegisterPatientController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField contact;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField fullName;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField medicare;

    @FXML
    private TableView<Patient> patientTableView;

    @FXML
    private TableColumn<Patient, String> fullNameColumn;

    @FXML
    private TableColumn<Patient, Date> dobColumn;

    @FXML
    private TableColumn<Patient, String> genderColumn;

    @FXML
    private TableColumn<Patient, String> addressColumn;

    @FXML
    private TableColumn<Patient, String> contactColumn;

    @FXML
    private TableColumn<Patient, String> medicareColumn;

    @FXML
    private TableColumn<Patient, Void> actionColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDataInTable();
        setGenderDropDown();
        initActionColumn();
    }

    private void initActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            final Button ehrButton = new Button("EHR");
            final Button appointmentButton = new Button("Book Appointment");
            final HBox buttonContainer = new HBox(10);  // 10 is the spacing between buttons

            {
                buttonContainer.getChildren().addAll(ehrButton, appointmentButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ehrButton.setOnAction(event -> handleEHR(getTableRow().getItem()));
                    appointmentButton.setOnAction(event -> handleAppointment(getTableRow().getItem()));
                    setGraphic(buttonContainer);
                }
            }
        });
    }

    private void handleEHR(Patient patient) {
        try {
            App.setCurrentPatient(patient);
            System.out.println("Setting patient " + patient);
            App.setRoot("ehrRecord", "Staff Dashboard");
        } catch (IOException ex) {
            System.out.println("Error creating EHR");
        }
    }

    private void handleAppointment(Patient patient) {
        try {
            App.setCurrentPatient(patient);
            App.setRoot("bookAppointment", "Staff Dashboard");
        } catch (IOException ex) {
            System.out.println("Error booking appointment");
        }

    }

    @FXML
    void handlePatientRegisterButton(ActionEvent event) {
        try {
            // Retrieve values from the form
            String fullNameValue = fullName.getText();

            // For DatePicker, get the LocalDate and then convert to java.sql.Date
            LocalDate dobLocalDate = dob.getValue();

            String genderValue = gender.getValue();
            String addressValue = address.getText();
            String contactValue = contact.getText();
            String medicareValue = medicare.getText();

            // Validation checks
            if (fullNameValue.isEmpty() || dobLocalDate == null || genderValue == null || genderValue.isEmpty()
                    || addressValue.isEmpty() || contactValue.isEmpty() || medicareValue.isEmpty()) {
                CommonMethods.showAlert("Error", "Please enter all fields!!!");
                return;
            }
            Date dobValue = Date.valueOf(dobLocalDate);
            // Create a new Patient object
            Patient patient = new Patient(fullNameValue, dobValue, genderValue, addressValue, contactValue, medicareValue);

            Connection connection = DatabaseConnection.getConnection();
            PatientDAO patientDAO = new PatientDAO(connection);
            patientDAO.insert(patient);
            clearFields();
            setDataInTable();
            CommonMethods.showAlert("Success", "Patient added successfully.");
        } catch (SQLException ex) {
            CommonMethods.showAlert("Error", "Error adding patient.");

        }
    }

    public void setGenderDropDown() {
        // Initialize the ComboBox with gender names
        Gender[] genders = Gender.values();
        for (Gender g : genders) {
            gender.getItems().add(g.toString());
        }

        // Set a default selection if needed
        gender.setValue(Gender.MALE.toString());

        // You can add event handlers for ComboBox actions if needed
        gender.setOnAction(event -> {
            String selectedRole = gender.getValue();
            System.out.println("Selected Role: " + selectedRole);
            // You can perform actions based on the selected role here
        });
    }

    public void setDataInTable() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PatientDAO patientDAO = new PatientDAO(connection);

            // Fetch patient data
            ObservableList<Patient> patients = FXCollections.observableArrayList(patientDAO.findAll());
            System.out.println(patients.size());
            patientTableView.setItems(patients);

        } catch (SQLException ex) {
            System.out.println("Error fetching data");
        }
    }

    /**
     * Clears all the fields of the patient registration form.
     */
    private void clearFields() {
        fullName.clear();
        dob.setValue(null); // For DatePicker
        gender.setValue(null); // If gender is a ComboBox or ChoiceBox, otherwise use clear()
        address.clear();
        contact.clear();
        medicare.clear();
    }

}
