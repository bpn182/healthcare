package interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import models.Slot;

/**
 *
 * @author bipin
 */
public interface ISlotDAO {

    // CREATE
    void insert(Slot slot) throws SQLException;

    // READ
    Slot findById(int id) throws SQLException;

    List<Slot> findAll() throws SQLException;

    Slot findSlotByDate(Date date, int doctorID) throws SQLException;

    // UPDATE
    void update(Slot slot) throws SQLException;

    // DELETE
    void delete(int id) throws SQLException;
}
