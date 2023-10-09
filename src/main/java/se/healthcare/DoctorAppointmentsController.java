/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import database.DatabaseConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Appointment;
import DAO.AppointmentDAO;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class DoctorAppointmentsController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> serviceColumn;
    @FXML
    private TableColumn<Appointment, String> doctorColumn;
    @FXML
    private TableColumn<Appointment, String> patientColumn;
    @FXML
    private TableColumn<Appointment, String> costColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTable();
    }

    public void populateTable() {
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnection.getConnection());
            int doctorID = App.getCurrentStaff().getStaffID();
            List<Appointment> appointments = appointmentDAO.getPopulatedAppointmentsByDoctorID(doctorID);

            ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

            doctorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoctor().getFullName()));
            serviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService().getServiceName()));
            costColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService().getCost()));
            patientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getFullName()));

            appointmentTable.setItems(observableList);
        } catch (SQLException e) {
            System.err.println("Error populating table: " + e.getMessage());
            // Handle this exception as you see fit
        }
    }
}
