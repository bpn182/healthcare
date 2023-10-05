package interfaces;

/**
 *
 * @author bipin
 */
import java.sql.SQLException;
import java.util.List;
import models.Patient;

public interface IPatientDAO {

    void insert(Patient patient) throws SQLException;

    void update(Patient patient) throws SQLException;

    void delete(int patientID) throws SQLException;

    Patient findById(int patientID) throws SQLException;

    List<Patient> findAll() throws SQLException;

    void close() throws Exception;
}
