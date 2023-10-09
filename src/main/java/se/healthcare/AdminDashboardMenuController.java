/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package se.healthcare;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class AdminDashboardMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void handleAddStaffMenu(ActionEvent event) throws IOException {
        App.setRoot("registerStaff", "Admin Dashboard");
    }

    @FXML
    void handleLogoutMenu(ActionEvent event) throws IOException {
        App.setRoot("login", "HealthLink Login");
    }

    @FXML
    void handleStaffMenu(ActionEvent event) throws IOException {
        App.setRoot("stats", "Admin Dashboard");

    }

}
