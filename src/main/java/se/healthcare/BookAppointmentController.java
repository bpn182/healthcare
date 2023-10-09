/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import database.DatabaseConnection;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import models.Appointment;
import DAO.AppointmentDAO;
import models.EHR;
import DAO.EHRDAO;
import models.Service;
import DAO.ServiceDAO;
import models.Slot;
import DAO.SlotDAO;
import models.Staff;
import DAO.StaffDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import models.Patient;
import static utils.CommonMethods.convertFormattedHourTo24Hour;
import static utils.CommonMethods.generateHourList;
import static utils.CommonMethods.isStringEmptyOrNull;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class BookAppointmentController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> hours;
    @FXML
    private TextField note;

    @FXML
    private ComboBox<Staff> doctor;
    @FXML
    private ComboBox<Service> service;
    private StaffDAO staffDAO;
    private ServiceDAO serviceDAO;

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
    @FXML
    private Text patientName;
    @FXML
    private TableColumn<Appointment, Void> actionColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fetchDoctors();
        fetchServices();
        populateTable();
        initActionColumn();
        patientName.setText(App.getCurrentPatient().getFullName());
        date.valueProperty().addListener((observable, oldValue, newValue) -> {
            checkAndFetchSlots();
        });

        doctor.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                checkAndFetchSlots();
            }
        });
    }

    private void initActionColumn() {
        // Set up the "Action" column with an "Edit" button
        actionColumn.setCellFactory(param -> new TableCell<>() {
            final Button billBtn = new Button("View Bill");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(billBtn);
                    billBtn.setOnAction(event -> handleViewBill(getTableRow().getItem()));
                }
            }
        });
    }

    public void handleViewBill(Appointment appointment) {
        try {
            App.setCurrentAppointment(appointment);
            App.setRoot("billingInfo", "Staff Dashboard");
            System.err.println(appointment);
        } catch (IOException ex) {
            Logger.getLogger(BookAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleRegisterAppointment(ActionEvent event) {
        LocalDate dateLocalDate = date.getValue();
        String noteValue = note.getText();
        String hourValue = hours.getValue();

        // Check for null and empty values
        if (dateLocalDate == null
                || isStringEmptyOrNull(hourValue)
                || isStringEmptyOrNull(noteValue)
                || service.getSelectionModel().getSelectedItem() == null
                || doctor.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Please fill out all the required fields.");
            return;
        }

        int formattedHour = convertFormattedHourTo24Hour(hourValue);
        int serviceID = service.getSelectionModel().getSelectedItem().getServiceID();
        int doctorID = doctor.getSelectionModel().getSelectedItem().getStaffID();
        int patientID = App.getCurrentPatient().getPatientID();
        try {
            Connection connection = DatabaseConnection.getConnection();
            AppointmentDAO appointmentDAO = new AppointmentDAO(connection);

            Appointment appointment = new Appointment();
            appointment.setDate(java.sql.Date.valueOf(dateLocalDate));
            appointment.setNote(noteValue);
            appointment.setDoctorID(doctorID);
            appointment.setPatientID(patientID);
            appointment.setServiceID(serviceID);

            appointment.setHour(hourValue);
            appointmentDAO.create(appointment);
            populateTable();
            clearFields();
            checkAndFetchSlots();
            System.out.println("Appointment registered successfully.");

        } catch (SQLException e) {
            System.err.println("Error while registering appointment: " + e.getMessage());
            // Handle the error as you see fit, perhaps show a dialog to inform the user
        }
    }

    public void clearFields() {
        note.clear();
        hours.setValue(null);
    }

    private void checkAndFetchSlots() {
        LocalDate selectedDate = date.getValue();
        Staff selectedDoctor = doctor.getSelectionModel().getSelectedItem();

        if (selectedDate != null && selectedDoctor != null) {
            fetchSlots(java.sql.Date.valueOf(selectedDate), selectedDoctor.getStaffID());
        }
    }

    public void setHoursMenu(String start, String end) {
        List<String> hourList = generateHourList(10, 16);
        hours.setItems(FXCollections.observableArrayList(hourList));
        hours.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int result = convertFormattedHourTo24Hour(newValue);
            String selectedHourTo24Hr = String.valueOf(result);
            System.out.println("Selected hour changed " + newValue);
            System.out.println("Selected hour in 24-hour format: " + selectedHourTo24Hr);

        });
    }

    public void fetchServices() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            serviceDAO = new ServiceDAO(connection);

            List<Service> servicesList = serviceDAO.findAll();
            service.setItems(FXCollections.observableArrayList(servicesList));

            service.valueProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    int serviceID = newSelection.getServiceID();
                    System.out.println("Selected service ID: " + serviceID);
                }
            });
        } catch (SQLException ex) {
            System.err.println("Error loading service: " + ex.getMessage());
        }
    }

    public void fetchDoctors() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            staffDAO = new StaffDAO(connection);
            List<Staff> staffList = staffDAO.findAllDoctors();
            doctor.setItems(FXCollections.observableArrayList(staffList));
        } catch (SQLException ex) {
            System.err.println("Error loading staff: " + ex.getMessage());
        }
    }

    public void fetchSlots(Date date, int doctorID) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            SlotDAO slotDAO = new SlotDAO(connection);
            Slot slot = slotDAO.findSlotByDate(date, doctorID);
            if (slot != null) {
                List<String> hourList = generateHourList(parseInt(slot.getStartHour()), parseInt(slot.getEndHour()));

                List<String> bookedHours = new ArrayList<>();
                AppointmentDAO appointmentDAO = new AppointmentDAO(connection);
                List<Appointment> appointments = appointmentDAO.findAppointmentsByDateAndDoctor(date, doctorID);
                for (Appointment appointment : appointments) {
                    String hourStr = appointment.getHour();
                    bookedHours.add(hourStr);
                }

                System.out.println("slot " + hourList.toString());
                System.out.println("booked " + bookedHours.toString());

                // Remove booked slots from available slots
                hourList.removeAll(bookedHours);
                hours.setItems(FXCollections.observableArrayList(hourList));

            } else {
                hours.getItems().clear();

                System.out.println("No slot found for Doctor ID: " + doctorID + " on Date: " + date);
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching slot: " + ex.getMessage());
        }
    }

    public void populateTable() {
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnection.getConnection());
            List<Appointment> appointments = appointmentDAO.getAllAppointmentsWithDetails();

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
