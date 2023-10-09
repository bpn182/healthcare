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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.EHR;
import DAO.EHRDAO;
import models.Patient;
import DAO.PatientDAO;
import javafx.scene.text.Text;
import utils.CommonMethods;

/**
 * FXML Controller class
 *
 * @author bipin
 */
public class EhrRecordController implements Initializable {

    @FXML
    private TextField conditions;
    @FXML
    private TextField allergens;
    @FXML
    private TextArea note;

    @FXML
    private TextField currentMedications;
    @FXML
    private TableColumn<EHR, String> allergensColumn;

    @FXML
    private TableColumn<EHR, String> conditionColumn;

    @FXML
    private TableColumn<EHR, String> currentMedicationColumn;

    @FXML
    private TableView<EHR> ehrTableView;

    @FXML
    private TableColumn<EHR, String> noteColumn;
    @FXML
    private Text patientName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDataInTable();
        patientName.setText(App.getCurrentPatient().getFullName());
    }

    @FXML
    void handleSubmitBtn(ActionEvent event) {
        try {
            String conditionValue = conditions.getText();
            String allergensValue = allergens.getText();
            String noteValue = note.getText();
            String currentMedicationsValue = currentMedications.getText();

            EHR ehr = new EHR();
            ehr.setCurrentMedications(currentMedicationsValue);
            ehr.setCondition(conditionValue);
            ehr.setAllergens(allergensValue);
            ehr.setNote(noteValue);
            ehr.setPatientID(App.getCurrentPatient().getPatientID());
            System.err.println(ehr);
            Connection connection = DatabaseConnection.getConnection();
            EHRDAO ehrDAO = new EHRDAO(connection);

            ehrDAO.insert(ehr);
            clearFields();
            setDataInTable();
            CommonMethods.showAlert("Success", "EHR added successfully.");

        } catch (SQLException ex) {
            CommonMethods.showAlert("Error", "Error adding EHR.");

            Logger.getLogger(EhrRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDataInTable() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            EHRDAO ehrdao = new EHRDAO(connection);

            // Fetch patient data
            int patientId = App.getCurrentPatient().getPatientID();
            ObservableList<EHR> ehrs = FXCollections.observableArrayList(ehrdao.findAllByPatientId(patientId));
            System.out.println(ehrs.size());
            ehrTableView.setItems(ehrs);

        } catch (SQLException ex) {
            System.out.println("Error fetching data");
        }
    }

    private void clearFields() {
        conditions.clear();
        allergens.clear();
        note.clear();
        currentMedications.clear();
    }

}
