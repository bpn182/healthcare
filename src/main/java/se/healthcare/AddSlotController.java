/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import database.DatabaseConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Service;
import DAO.ServiceDAO;
import models.Slot;
import DAO.SlotDAO;
import utils.CommonMethods;
import utils.Constants.Day;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class AddSlotController implements Initializable {

    @FXML
    private TextField doctor;

    @FXML
    private ComboBox<String> day;

    @FXML
    private TextField end;

    @FXML
    private TextField start;

    @FXML
    private TableView<Slot> slotTableView;

    @FXML
    private TableColumn<Slot, String> startColumn;

    @FXML
    private TableColumn<Slot, String> endColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDayMenu();
        fetchAllSlotsAndDisplay();
        doctor.setText(App.getCurrentStaff().getFullName());
        doctor.setDisable(true);
        System.out.println(App.getCurrentStaff().getFullName());
    }

    public void fetchAllSlotsAndDisplay() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            SlotDAO slotDAO = new SlotDAO(connection);
            // Fetch patient data
            ObservableList<Slot> slots = FXCollections.observableArrayList(slotDAO.findAll());
            System.out.println(slots.size());

            // Bind data to TableView
            slotTableView.setItems(slots);
        } catch (SQLException ex) {
            Logger.getLogger(AddSlotController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleSaveSlotButton(ActionEvent event) {
        try {
            String dayValue = day.getValue();
            String startValue = start.getText();
            String endValue = end.getText();

            if (dayValue == null || startValue.isEmpty() || endValue.isEmpty()) {
                CommonMethods.showAlert("Error", "Please enter all fields!!!");
                return;
            }

            Slot slot = new Slot();
            slot.setDay(dayValue);
            slot.setDoctorID(App.getCurrentStaff().getStaffID());
            slot.setStartHour(startValue);
            slot.setEndHour(endValue);
            Connection connection = DatabaseConnection.getConnection();
            SlotDAO slotDAO = new SlotDAO(connection);
            slotDAO.insert(slot);
            clearFields();
            CommonMethods.showAlert("Success", "Slot added successfully.");
            fetchAllSlotsAndDisplay();

        } catch (SQLException ex) {
            CommonMethods.showAlert("Error", "Error adding slot.");

            Logger.getLogger(AddSlotController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clearFields() {
        day.setValue(null);
        start.clear();
        end.clear();
    }

    public void setDayMenu() {
        Day[] genders = Day.values();
        for (Day g : genders) {
            day.getItems().add(g.toString());
        }

        // Set a default selection if needed
        day.setValue(Day.MONDAY.toString());

    }

}
