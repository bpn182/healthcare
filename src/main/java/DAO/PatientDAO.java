package DAO;

/**
 *
 * @author bipin
 */
import interfaces.IPatientDAO;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import models.Patient;
import models.Patient;

public class PatientDAO implements IPatientDAO {

    private final Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (fullName, dob, gender, address, contact, medicare) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParameters(stmt, patient, false);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patient.setPatientID(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET fullName=?, dob=?, gender=?, address=?, contact=?, medicare=? WHERE patientID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setPreparedStatementParameters(stmt, patient, true);
            stmt.executeUpdate();
        }
    }

    public void delete(int patientID) throws SQLException {
        String sql = "DELETE FROM patients WHERE patientID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientID);
            stmt.executeUpdate();
        }
    }

    public Patient findById(int patientID) throws SQLException {
        String sql = "SELECT * FROM patients WHERE patientID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPatient(rs);
                }
            }
        }
        return null;
    }

    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patientID DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                patients.add(mapRowToPatient(rs));
            }
        }
        return patients;
    }

    private Patient mapRowToPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("patientID"),
                rs.getString("fullName"),
                rs.getDate("dob"),
                rs.getString("gender"),
                rs.getString("address"),
                rs.getString("contact"),
                rs.getString("medicare")
        );
    }

    private void setPreparedStatementParameters(PreparedStatement stmt, Patient patient, boolean includeId) throws SQLException {
        stmt.setString(1, patient.getFullName());
        stmt.setDate(2, new java.sql.Date(patient.getDob().getTime()));
        stmt.setString(3, patient.getGender());
        stmt.setString(4, patient.getAddress());
        stmt.setString(5, patient.getContact());
        stmt.setString(6, patient.getMedicare());
        if (includeId) {
            stmt.setInt(7, patient.getPatientID());
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
