package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.EHR;

/**
 *
 * @author bipin
 */
public interface IEHRDAO {

    // CREATE
    void insert(EHR ehr) throws SQLException;

    // READ
    EHR findById(int id) throws SQLException;

    List<EHR> findAll() throws SQLException;

    List<EHR> findAllByPatientId(int patientId) throws SQLException;

    // UPDATE
    void update(EHR ehr) throws SQLException;

    // DELETE
    void delete(int id) throws SQLException;

}
