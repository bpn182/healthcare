
package DAO;

/**
 *
 * @author bipin
 */
import interfaces.IAppointmentDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Appointment;
import models.Patient;
import models.Service;
import models.Staff;

public class AppointmentDAO implements IAppointmentDAO{

    private Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void create(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (note, date, hour, serviceID, doctorID, patientID) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, appointment.getNote());
        pstmt.setDate(2, appointment.getDate());
        pstmt.setString(3, appointment.getHour());
        pstmt.setInt(4, appointment.getServiceID());
        pstmt.setInt(5, appointment.getDoctorID());
        pstmt.setInt(6, appointment.getPatientID());
        pstmt.executeUpdate();
    }

    // Read
    public Appointment findById(int id) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE appointmentID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Appointment(
                    rs.getInt("appointmentID"),
                    rs.getString("note"),
                    rs.getDate("date"),
                    rs.getString("hour"),
                    rs.getInt("serviceID"),
                    rs.getInt("doctorID"),
                    rs.getInt("patientID")
            );
        }
        return null;
    }

    public List<Appointment> getAllAppointmentsWithDetails() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT a.*, s.*, d.*, p.* "
                + "FROM appointments a "
                + "JOIN services s ON a.serviceID = s.serviceID "
                + "JOIN staff d ON a.doctorID = d.staffID "
                + "JOIN patients p ON a.patientID = p.patientID";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Appointment appointment = extractAppointmentFromResultSet(rs);
                appointments.add(appointment);
            }
        }

        return appointments;
    }

    public Appointment getAppointmentWithDetailsById(int appointmentId) throws SQLException {
        Appointment appointment = null;

        String sql = "SELECT a.*, s.*, d.*, p.* "
                + "FROM appointments a "
                + "JOIN services s ON a.serviceID = s.serviceID "
                + "JOIN staff d ON a.doctorID = d.staffID "
                + "JOIN patients p ON a.patientID = p.patientID "
                + "WHERE a.appointmentID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    appointment = extractAppointmentFromResultSet(rs);
                }
            }
        }

        return appointment;
    }

    public List<Appointment> getPopulatedAppointmentsByDoctorID(int doctorID) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT a.*, s.*, d.*, p.* "
                + "FROM appointments a "
                + "JOIN services s ON a.serviceID = s.serviceID "
                + "JOIN staff d ON a.doctorID = d.staffID "
                + "JOIN patients p ON a.patientID = p.patientID "
                + "WHERE d.staffID = ? "
                + "ORDER BY a.date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, doctorID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = extractAppointmentFromResultSet(rs);
                appointments.add(appointment);
            }
        }

        return appointments;
    }

    public BigDecimal getTotalCostOfAllAppointments() throws SQLException {
        BigDecimal totalCost = BigDecimal.ZERO;

        String sql = "SELECT SUM(CAST(s.cost AS DECIMAL(10, 2))) AS total_cost "
                + "FROM appointments a "
                + "JOIN services s ON a.serviceID = s.serviceID "
                + "JOIN staff d ON a.doctorID = d.staffID "
                + "JOIN patients p ON a.patientID = p.patientID";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalCost = rs.getBigDecimal("total_cost");
            }
        }

        return totalCost;
    }

    // Update
    public void update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET note = ?, date = ?, hour = ?, serviceID = ?, doctorID = ?, patientID = ? WHERE appointmentID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, appointment.getNote());
        pstmt.setDate(2, appointment.getDate());
        pstmt.setString(3, appointment.getHour());
        pstmt.setInt(4, appointment.getServiceID());
        pstmt.setInt(5, appointment.getDoctorID());
        pstmt.setInt(6, appointment.getPatientID());
        pstmt.setInt(7, appointment.getAppointmentID());
        pstmt.executeUpdate();
    }

    // Delete
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE appointmentID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    // Retrieve all appointments
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointmentID DESC";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("appointmentID"),
                    rs.getString("note"),
                    rs.getDate("date"),
                    rs.getString("hour"),
                    rs.getInt("serviceID"),
                    rs.getInt("doctorID"),
                    rs.getInt("patientID")
            ));
        }
        return appointments;
    }

    public List<Appointment> findAppointmentsByDateAndDoctor(java.sql.Date date, int doctorID) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointments WHERE date = ? AND doctorID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setDate(1, date);
        pstmt.setInt(2, doctorID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("appointmentID"),
                    rs.getString("note"),
                    rs.getDate("date"),
                    rs.getString("hour"),
                    rs.getInt("serviceID"),
                    rs.getInt("doctorID"),
                    rs.getInt("patientID")
            ));
        }

        return appointments;
    }

    private Appointment extractAppointmentFromResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();

        appointment.setAppointmentID(rs.getInt("a.appointmentID"));
        appointment.setNote(rs.getString("a.note"));
        appointment.setDate(rs.getDate("a.date"));
        appointment.setHour(rs.getString("a.hour"));

        Service service = new Service();
        service.setServiceID(rs.getInt("s.serviceID"));
        service.setServiceName(rs.getString("s.serviceName"));
        service.setDepartment(rs.getString("s.department"));
        service.setDuration(rs.getString("s.duration"));
        service.setCost(rs.getString("s.cost"));
        appointment.setService(service);

        Staff doctor = new Staff();
        doctor.setStaffID(rs.getInt("d.staffID"));
        doctor.setFullName(rs.getString("d.fullName"));
        appointment.setDoctor(doctor);

        Patient patient = new Patient();
        patient.setPatientID(rs.getInt("p.patientID"));
        patient.setFullName(rs.getString("p.fullName"));
        appointment.setPatient(patient);

        return appointment;
    }

}
