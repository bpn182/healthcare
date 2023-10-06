package DAO;

import interfaces.IServiceDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Service;

/**
 *
 * @author bipin
 */
public class ServiceDAO implements IServiceDAO {

    private Connection connection;

    public ServiceDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    // CREATE
    public void insert(Service service) throws SQLException {
        String insertSql = "INSERT INTO services (serviceName, department, duration, cost) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, service.getServiceName());
            insertStatement.setString(2, service.getDepartment());
            insertStatement.setString(3, service.getDuration());
            insertStatement.setString(4, service.getCost());
            insertStatement.executeUpdate();
        }
    }

    // READ
    public List<Service> findAll() throws SQLException {
        String findAllSql = "SELECT * FROM services ORDER BY serviceName ASC";
        List<Service> services = new ArrayList<>();
        try (PreparedStatement findAllStatement = connection.prepareStatement(findAllSql); ResultSet resultSet = findAllStatement.executeQuery()) {
            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceID(resultSet.getInt("serviceID"));
                service.setServiceName(resultSet.getString("serviceName"));
                service.setDepartment(resultSet.getString("department"));
                service.setDuration(resultSet.getString("duration"));
                service.setCost(resultSet.getString("cost"));
                services.add(service);
            }
        }
        return services;
    }

    public Service findById(int id) throws SQLException {
        String findByIdSql = "SELECT * FROM services WHERE id = ?";
        try (PreparedStatement findByIdStatement = connection.prepareStatement(findByIdSql)) {
            findByIdStatement.setInt(1, id);
            try (ResultSet resultSet = findByIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    Service service = new Service();
                    service.setServiceName(resultSet.getString("serviceName"));
                    service.setDepartment(resultSet.getString("department"));
                    service.setDuration(resultSet.getString("duration"));
                    service.setCost(resultSet.getString("cost"));
                    return service;
                } else {
                    return null;
                }
            }
        }
    }

    // UPDATE
    public void update(Service service, int id) throws SQLException {
        String updateSql = "UPDATE services SET serviceName=?, department=?, duration=?, cost=? WHERE id=?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setString(1, service.getServiceName());
            updateStatement.setString(2, service.getDepartment());
            updateStatement.setString(3, service.getDuration());
            updateStatement.setString(4, service.getCost());
            updateStatement.setInt(5, id);
            updateStatement.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM services WHERE id=?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
