/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import DAO.AppointmentDAO;
import database.DatabaseConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.Appointment;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class BillingInfoController implements Initializable {

    @FXML
    private Text doctor;
    @FXML
    private Text patientFullName;
    @FXML
    private Text appointmentDate;
    @FXML
    private Text serviceName;
    @FXML
    private Text serviceCost;
    @FXML
    private Text serviceDuration;
    @FXML
    private Text serviceDepartment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetchAppointmentData();
    }

    public void fetchAppointmentData() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            AppointmentDAO appointmentDAO = new AppointmentDAO(connection);
            int appointmentId = App.getCurrentAppointment().getAppointmentID();
            Appointment appointment = appointmentDAO.getAppointmentWithDetailsById(appointmentId);
            updateFields(appointment);
        } catch (SQLException ex) {
            System.err.println("Error fetching appointment");
        }

    }

    private void updateFields(Appointment appointment) {
        doctor.setText(appointment.getDoctor().getFullName());
        patientFullName.setText(appointment.getPatient().getFullName());
        appointmentDate.setText(appointment.getDate().toString());
        serviceName.setText(appointment.getService().getServiceName());
        serviceCost.setText(appointment.getService().getCost());
        serviceDuration.setText(appointment.getService().getDuration() + " hours");
        serviceDepartment.setText(appointment.getService().getDepartment());
    }

}
