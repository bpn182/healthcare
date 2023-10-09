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
import models.Staff;
import DAO.StaffDAO;
import models.Staff;
import DAO.StaffDAO;
import utils.CommonMethods;
import static utils.CommonMethods.HashConvert;
import utils.Constants;
import utils.Constants.Department;
import utils.Constants.Role;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class RegisterStaffController implements Initializable {

    @FXML
    private TextField fullName;

    @FXML
    private TextField licence;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> role;
    @FXML
    private ComboBox<String> department;

    @FXML
    private TextField username;

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, String> departmentColumn;

    @FXML
    private TableColumn<Staff, String> fullNameColumn;

    @FXML
    private TableColumn<Staff, String> licenceColumn;

    @FXML
    private TableColumn<Staff, String> phoneColumn;

    @FXML
    private TableColumn<Staff, String> roleColumn;

    @FXML
    private TableColumn<Staff, String> usernameColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDepartmentMenu();
        setRoleMenu();
        setDataInTable();
    }

    public void setRoleMenu() {
        Role[] roles = Role.values();
        for (Role r : roles) {
            role.getItems().add(r.toString());
        }

        // Set a default selection if needed
        role.setValue(Role.STAFF.toString());

        role.setOnAction(event -> {
            String selectedValue = role.getValue();
            System.out.println("Selected Role: " + selectedValue);
        });
    }

    public void setDepartmentMenu() {
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

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }

    @FXML
    void handleRegisterStaffButton(ActionEvent event) {
        try {
            String fullNameValue = fullName.getText();
            String licenceValue = licence.getText();
            String passwordValue = password.getText();
            String phoneValue = phone.getText();
            String roleValue = role.getValue();
            String departmentValue = department.getValue();
            String usernameValue = username.getText();

            if (isEmptyOrNull(fullNameValue)
                    || isEmptyOrNull(passwordValue)
                    || isEmptyOrNull(phoneValue)
                    || isEmptyOrNull(roleValue)
                    || isEmptyOrNull(departmentValue)
                    || isEmptyOrNull(usernameValue)) {
                CommonMethods.showAlert("Error", "Please enter all fields!!!");
                return;
            }

            String hashedPassword = HashConvert(passwordValue);
            Staff newStaff = new Staff(fullNameValue, licenceValue, hashedPassword, phoneValue, roleValue, departmentValue, usernameValue);
            Connection connection = DatabaseConnection.getConnection();
            StaffDAO staffDAO = new StaffDAO(connection);
            staffDAO.insert(newStaff);
            CommonMethods.showAlert("Success", "Staff added successfully.");
            clearFields();
            setDataInTable();
        } catch (SQLException ex) {
            System.out.println(ex);
            CommonMethods.showAlert("Error", "Error adding staff.");
        }

    }

    public void setDataInTable() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            StaffDAO staffDAO = new StaffDAO(connection);

            // Fetch staff data
            ObservableList<Staff> staffs = FXCollections.observableArrayList(staffDAO.findAll());
            System.out.println(staffs.size());
            staffTableView.setItems(staffs);

        } catch (SQLException ex) {
            System.out.println("Error fetching data");
        }
    }

    private void clearFields() {
        fullName.clear();
        licence.clear();
        password.clear();
        phone.clear();
        role.getSelectionModel().clearSelection(); // For ComboBox, use clearSelection to deselect any selected item
        department.getSelectionModel().clearSelection();
        username.clear();
    }

}
