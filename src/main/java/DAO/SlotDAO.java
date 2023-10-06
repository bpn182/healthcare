package DAO;

import interfaces.ISlotDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import models.Slot;

/**
 *
 * @author bipin
 */
public class SlotDAO implements ISlotDAO {

    private Connection connection;

    public SlotDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insert(Slot slot) throws SQLException {
        String sql = "INSERT INTO slots (day, startHour, endHour, doctorID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, slot.getDay());
            stmt.setString(2, slot.getStartHour());
            stmt.setString(3, slot.getEndHour());
            stmt.setInt(4, slot.getDoctorID());
            stmt.executeUpdate();
        }
    }

    // READ
    public Slot findById(int id) throws SQLException {
        String sql = "SELECT * FROM slots WHERE slotID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractSlotFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Slot> findAll() throws SQLException {
        List<Slot> slots = new ArrayList<>();
        String sql = "SELECT * FROM slots";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                slots.add(extractSlotFromResultSet(rs));
            }
        }
        return slots;
    }

    public Slot findSlotByDate(Date date, int doctorID) throws SQLException {
        // Convert the Date to a day of the week
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE"); // 'EEEE' gives full day name e.g., "Monday"
        String dayOfWeek = sdf.format(date).toUpperCase();

        String sql = "SELECT * FROM slots WHERE day = ? AND doctorID = ? LIMIT 1"; // Limiting the result to one row
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dayOfWeek);
            statement.setInt(2, doctorID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // only expecting one result
                    Slot slot = new Slot();
                    slot.setSlotID(resultSet.getInt("slotID"));
                    slot.setDay(resultSet.getString("day"));
                    slot.setStartHour(resultSet.getString("startHour"));
                    slot.setEndHour(resultSet.getString("endHour"));
                    slot.setDoctorID(resultSet.getInt("doctorID"));
                    return slot;
                }
            }
        }
        return null; // return null if no slot found
    }

    // UPDATE
    public void update(Slot slot) throws SQLException {
        String sql = "UPDATE slots SET day=?, startHour=?, endHour=?, doctorID=? WHERE slotID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, slot.getDay());
            stmt.setString(2, slot.getStartHour());
            stmt.setString(3, slot.getEndHour());
            stmt.setInt(4, slot.getDoctorID());
            stmt.setInt(5, slot.getSlotID());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM slots WHERE slotID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Slot extractSlotFromResultSet(ResultSet rs) throws SQLException {
        Slot slot = new Slot();
        slot.setSlotID(rs.getInt("slotID"));
        slot.setDay((rs.getString("day")));
        slot.setStartHour(rs.getString("startHour"));
        slot.setEndHour(rs.getString("endHour"));
        slot.setDoctorID(rs.getInt("doctorID"));
        return slot;
    }

}
