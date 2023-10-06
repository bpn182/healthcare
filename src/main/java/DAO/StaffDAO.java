package DAO;

import database.DatabaseConnection;
import interfaces.IStaffDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Staff;
import static utils.CommonMethods.HashConvert;

/**
 *
 * @author bipin
 */
public class StaffDAO implements IStaffDAO {

    private Connection connection;

    public StaffDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Insert)
    public void insert(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (fullName, licence, password, phone, role, department, username) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getLicence());
            statement.setString(3, staff.getPassword());
            statement.setString(4, staff.getPhone());
            statement.setString(5, staff.getRole());
            statement.setString(6, staff.getDepartment());
            statement.setString(7, staff.getUsername());
            statement.executeUpdate();
        }
    }

    // Read (Select All)
    public List<Staff> findAll() throws SQLException {
        List<Staff> staffs = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY staffID DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setFullName(resultSet.getString("fullName"));
                staff.setLicence(resultSet.getString("licence"));
                staff.setPassword(resultSet.getString("password"));
                staff.setPhone(resultSet.getString("phone"));
                staff.setRole(resultSet.getString("role"));
                staff.setDepartment(resultSet.getString("department"));
                staff.setUsername(resultSet.getString("username"));
                staffs.add(staff);
            }
        }
        return staffs;
    }

    // Read (Select by Username)
    public Staff findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM staff WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setFullName(resultSet.getString("fullName"));
                staff.setLicence(resultSet.getString("licence"));
                staff.setPassword(resultSet.getString("password"));
                staff.setPhone(resultSet.getString("phone"));
                staff.setRole(resultSet.getString("role"));
                staff.setDepartment(resultSet.getString("department"));
                staff.setUsername(resultSet.getString("username"));
                return staff;
            }
        }
        return null;
    }

    public List<Staff> findAllDoctors() throws SQLException {
        List<Staff> staffs = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE role = 'DOCTOR' ORDER BY fullName ASC";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffID"));
                staff.setFullName(resultSet.getString("fullName"));
                staff.setLicence(resultSet.getString("licence"));
                staff.setPassword(resultSet.getString("password"));
                staff.setPhone(resultSet.getString("phone"));
                staff.setRole(resultSet.getString("role"));
                staff.setDepartment(resultSet.getString("department"));
                staff.setUsername(resultSet.getString("username"));
                staffs.add(staff);
            }
        }
        return staffs;
    }

    // Update
    public void update(Staff staff) throws SQLException {
        String sql = "UPDATE staff SET fullName=?, licence=?, password=?, phone=?, role=?, department=? WHERE username=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getLicence());
            statement.setString(3, staff.getPassword());
            statement.setString(4, staff.getPhone());
            statement.setString(5, staff.getRole());
            statement.setString(6, staff.getDepartment());
            statement.setString(7, staff.getUsername());
            statement.executeUpdate();
        }
    }

    // Delete
    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM staff WHERE username=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }

    public void createDefaultAdmin() {
        try {
            Staff defaultAdmin = new Staff();
            defaultAdmin.setFullName("admin");
            defaultAdmin.setLicence("000000-000"); 
            defaultAdmin.setPassword(HashConvert("pass")); 
            defaultAdmin.setPhone("0445566"); 
            defaultAdmin.setRole("ADMIN");
            defaultAdmin.setDepartment("ADMIN");
            defaultAdmin.setUsername("admin");
           
            insert(defaultAdmin);
            System.out.println("Created Default Admin.");

        } catch (SQLException ex) {
            System.out.println("Error Creating Default Staff!!!!");

        }
    }
}
