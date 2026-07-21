package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CuentaDao implements CrudDao<Cuenta> {

    @Override
    public List<Cuenta> findAll() throws SQLException {
        List<Cuenta> cuentas = new ArrayList<>();
        String sql = "SELECT idcuenta, contrasena, email FROM cuenta ORDER BY idcuenta";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cuentas.add(mapRow(resultSet));
            }
        }
        return cuentas;
    }

    @Override
    public Cuenta findById(Object id) throws SQLException {
        String sql = "SELECT idcuenta, contrasena, email FROM cuenta WHERE idcuenta = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public void insert(Cuenta cuenta) throws SQLException {
        String sql = "INSERT INTO cuenta (contrasena, email) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cuenta.getContrasena());
            statement.setString(2, cuenta.getEmail());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cuenta.setIdCuenta(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Cuenta cuenta) throws SQLException {
        String sql = "UPDATE cuenta SET contrasena = ?, email = ? WHERE idcuenta = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cuenta.getContrasena());
            statement.setString(2, cuenta.getEmail());
            statement.setInt(3, cuenta.getIdCuenta());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM cuenta WHERE idcuenta = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Cuenta mapRow(ResultSet resultSet) throws SQLException {
        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(resultSet.getInt("idcuenta"));
        cuenta.setContrasena(resultSet.getString("contrasena"));
        cuenta.setEmail(resultSet.getString("email"));
        return cuenta;
    }
}
