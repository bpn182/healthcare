
package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Service;

/**
 *
 * @author bipin
 */
public interface IServiceDAO extends AutoCloseable {

    // CREATE
    void insert(Service service) throws SQLException;

    // READ
    Service findById(int id) throws SQLException;

    List<Service> findAll() throws SQLException;

    // UPDATE
    void update(Service service, int id) throws SQLException;

    // DELETE
    void delete(int id) throws SQLException;

    @Override
    void close() throws SQLException;

}
