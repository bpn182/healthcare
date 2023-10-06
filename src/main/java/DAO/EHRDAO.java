
package DAO;

/**
 *
 * @author bipin
 */
import interfaces.IEHRDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.EHR;

public class EHRDAO implements IEHRDAO {

    private Connection connection;

    public EHRDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insert(EHR ehr) throws SQLException {
        String sql = "INSERT INTO ehr (currentMedications, `condition`, allergens, note, patientID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ehr.getCurrentMedications());
            stmt.setString(2, ehr.getCondition());
            stmt.setString(3, ehr.getAllergens());
            stmt.setString(4, ehr.getNote());
            stmt.setInt(5, ehr.getPatientID());
            stmt.executeUpdate();
        }
    }

    // READ
    public EHR findById(int id) throws SQLException {
        String sql = "SELECT * FROM ehr WHERE EHRID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractEHRFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<EHR> findAll() throws SQLException {
        List<EHR> ehRecords = new ArrayList<>();
        String sql = "SELECT * FROM ehr";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ehRecords.add(extractEHRFromResultSet(rs));
            }
        }
        return ehRecords;
    }

    // Find all EHR records by a specific patient ID
    public List<EHR> findAllByPatientId(int patientId) throws SQLException {
        List<EHR> ehRecords = new ArrayList<>();
        String sql = "SELECT * FROM ehr WHERE patientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ehRecords.add(extractEHRFromResultSet(rs));
                }
            }
        }
        return ehRecords;
    }

    // UPDATE
    public void update(EHR ehr) throws SQLException {
        String sql = "UPDATE ehr SET currentMedications=?, condition=?, allergens=?, note=?, patientID=? WHERE EHRID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ehr.getCurrentMedications());
            stmt.setString(2, ehr.getCondition());
            stmt.setString(3, ehr.getAllergens());
            stmt.setString(4, ehr.getNote());
            stmt.setInt(5, ehr.getPatientID());
            stmt.setInt(6, ehr.getEhrID());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ehr WHERE EHRID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private EHR extractEHRFromResultSet(ResultSet rs) throws SQLException {
        EHR ehr = new EHR();
        ehr.setEhrID(rs.getInt("EHRID"));
        ehr.setCurrentMedications(rs.getString("currentMedications"));
        ehr.setCondition(rs.getString("condition"));
        ehr.setAllergens(rs.getString("allergens"));
        ehr.setNote(rs.getString("note"));
        ehr.setPatientID(rs.getInt("patientID"));
        return ehr;
    }
}
