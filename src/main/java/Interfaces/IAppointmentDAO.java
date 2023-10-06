
package interfaces;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import models.Appointment;

/**
 *
 * @author bipin
 */
public interface IAppointmentDAO {

    void create(Appointment appointment) throws SQLException;

    Appointment findById(int id) throws SQLException;

    List<Appointment> getAllAppointmentsWithDetails() throws SQLException;

    Appointment getAppointmentWithDetailsById(int appointmentId) throws SQLException;

    List<Appointment> getPopulatedAppointmentsByDoctorID(int doctorID) throws SQLException;

    BigDecimal getTotalCostOfAllAppointments() throws SQLException;

    void update(Appointment appointment) throws SQLException;

    void delete(int id) throws SQLException;

    List<Appointment> findAll() throws SQLException;

    List<Appointment> findAppointmentsByDateAndDoctor(java.sql.Date date, int doctorID) throws SQLException;

}