
package se.healthcare;

import database.DatabaseConnection;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Staff;
import DAO.StaffDAO;
import javafx.scene.control.PasswordField;
import utils.CommonMethods;
import static utils.CommonMethods.HashConvert;
import utils.Constants.Role;

/**
 *
 * @author bipin
 */
public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button handleLoginButtonAction;

    public void initialize() {
        username.setText("");
        password.setText("");

    }

    @FXML
    void handleLogin(ActionEvent event) throws SQLException, IOException {
        String usernameValue = username.getText();
        String passwordValue = password.getText();

        // Hash the entered password
        String hashedPassword = HashConvert(passwordValue);

        Connection connection = DatabaseConnection.getConnection();
        StaffDAO staffDAO = new StaffDAO(connection);
        Staff staff = staffDAO.findByUsername(usernameValue);
        if (staff == null) {
            CommonMethods.showAlert("ERROR", "Login failed. Incorrect credentials!!!");
            return;
        }
        if (hashedPassword.equals(staff.getPassword())) {
            App.setCurrentStaff(staff);
            System.out.println(staff.getRole());
            if (staff.getRole().equals(Role.STAFF.toString())) {
                App.setRoot("registerPatient", "Staff Dashboard");
            } else if (staff.getRole().equals(Role.ADMIN.toString())) {
                App.setRoot("registerStaff", "Admin Dashboard");
            } else if (staff.getRole().equals(Role.DOCTOR.toString())) {
                App.setRoot("addSlot", "Doctor Dashboard");

            } else {
                CommonMethods.showAlert("ERROR", "UnAuthorized User!!!");

            }
            System.out.println("Login successful!");
        } else {
            // Passwords do not match, login failed
            System.out.println("Login failed. Incorrect credentials.");
            CommonMethods.showAlert("ERROR", "Login failed. Incorrect credentials!!!");
        }

    }

}
