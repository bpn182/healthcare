/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import DAO.AppointmentDAO;
import DAO.PatientDAO;
import DAO.ServiceDAO;
import DAO.StaffDAO;
import database.DatabaseConnection;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.Appointment;
import models.Patient;
import models.Service;
import models.Staff;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class StatsController implements Initializable {

    @FXML
    private Text appointments;

    @FXML
    private Text earnings;

    @FXML
    private Text patients;

    @FXML
    private Text services;

    @FXML
    private Text staffs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetchData();
    }

    public void fetchData() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            AppointmentDAO appointmentDAO = new AppointmentDAO(connection);
            List<Appointment> appointmentsList = appointmentDAO.findAll();
            appointments.setText(String.valueOf(appointmentsList.size()));

            // Assuming similar DAO classes for Patients, Services, and Staffs
            PatientDAO patientDAO = new PatientDAO(connection);
            List<Patient> patientsList = patientDAO.findAll();
            patients.setText(String.valueOf(patientsList.size()));

            ServiceDAO serviceDAO = new ServiceDAO(connection);
            List<Service> servicesList = serviceDAO.findAll();
            services.setText(String.valueOf(servicesList.size()));

            StaffDAO staffDAO = new StaffDAO(connection);
            List<Staff> staffsList = staffDAO.findAll();
            staffs.setText(String.valueOf(staffsList.size()));

            BigDecimal totalEarnings = appointmentDAO.getTotalCostOfAllAppointments();
            earnings.setText(String.format("%.2f", totalEarnings));

        } catch (SQLException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
