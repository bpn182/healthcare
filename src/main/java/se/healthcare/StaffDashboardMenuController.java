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
public class StaffDashboardMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void handleAddPatientMenu(ActionEvent event) throws IOException {
        App.setRoot("registerPatient", "Staff Dashboard");
    }

    @FXML
    void handleLogoutMenu(ActionEvent event) throws IOException {
        App.setRoot("login", "HealthLink Login");
    }

    @FXML
    void handleAddServiceMenu(ActionEvent event) throws IOException {
        App.setRoot("addService", "Staff Dashboard");

    }

}
