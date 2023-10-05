
package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Staff;

/**
 *
 * @author bipin
 */
public interface IStaffDAO {

    // Create (Insert)
    void insert(Staff staff) throws SQLException;

    // Read (Select All)
    List<Staff> findAll() throws SQLException;

    // Read (Select by Username)
    Staff findByUsername(String username) throws SQLException;

    // Read (Select All Doctors)
    List<Staff> findAllDoctors() throws SQLException;

    // Update
    void update(Staff staff) throws SQLException;

    // Delete
    void delete(String username) throws SQLException;
}
