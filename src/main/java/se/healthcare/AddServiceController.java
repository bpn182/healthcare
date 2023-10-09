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
import utils.Constants.Department;
import models.Service;
import DAO.ServiceDAO;
import utils.CommonMethods;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class AddServiceController implements Initializable {

    @FXML
    private TextField cost;

    @FXML
    private ComboBox<String> department;

    @FXML
    private TextField duration;

    @FXML
    private TextField serviceName;

    @FXML
    private TableView<Service> servicesTable;

    @FXML
    private TableColumn<Service, String> serviceNameColumn;

    @FXML
    private TableColumn<Service, String> durationColumn;

    @FXML
    private TableColumn<Service, String> costColumn;

    @FXML
    private TableColumn<Service, String> departmentColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDepartmentDropDown();
        fetchAllServicesAndDisplay();
    }

    @FXML
    void handleSubmitService(ActionEvent event) {
        // 1. Extract values from the input fields
        String serviceNameValue = serviceName.getText();
        String departmentValue = department.getValue();
        String durationValue = duration.getText();
        String costValue = cost.getText();

        if (serviceNameValue.isEmpty() || departmentValue == null || durationValue.isEmpty() || costValue.isEmpty()) {
            // Display an error message or any feedback mechanism you have in place
            return;
        }

        // 2. Create a Service object with the extracted values
        Service service = new Service();
        service.setServiceName(serviceNameValue);
        service.setDepartment(departmentValue);
        service.setDuration(durationValue);
        service.setCost(costValue);

        try {
            // 3. Use the ServiceDAO to insert the Service object into the database
            Connection connection = DatabaseConnection.getConnection();
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            CommonMethods.showAlert("Success", "Service added successfully.");

            serviceDAO.insert(service);
            serviceDAO.close();
            clearFields();
            fetchAllServicesAndDisplay();

        } catch (SQLException ex) {
            CommonMethods.showAlert("Error", "Unable to add Service.");
        }
    }

    public void fetchAllServicesAndDisplay() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            // Fetch patient data
            ObservableList<Service> services = FXCollections.observableArrayList(serviceDAO.findAll());
            System.out.println(services.size());

            // Bind data to TableView
            servicesTable.setItems(services);
        } catch (SQLException ex) {
            Logger.getLogger(AddServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDepartmentDropDown() {
        // Initialize the ComboBox with role names
        Department[] departments = Department.values();
        for (Department g : departments) {
            department.getItems().add(g.toString());
        }

        // Set a default selection if needed
        department.setValue(Department.EMERGENCY.toString());

        department.setOnAction(event -> {
            String selectedDepartment = department.getValue();
            System.out.println("Selected Department: " + selectedDepartment);
        });
    }

    private void clearFields() {
        serviceName.clear();
        department.getSelectionModel().clearSelection();
        duration.clear();
        cost.clear();
    }

}
