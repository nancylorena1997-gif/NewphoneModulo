package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDao implements CrudDao<Administrador> {

    @Override
    public List<Administrador> findAll() throws SQLException {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "SELECT cedula, nombre, permisos, telefono, cuenta_idcuenta FROM administrador ORDER BY cedula";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                administradores.add(mapRow(resultSet));
            }
        }
        return administradores;
    }

    @Override
    public Administrador findById(Object id) throws SQLException {
        String sql = "SELECT cedula, nombre, permisos, telefono, cuenta_idcuenta FROM administrador WHERE cedula = ?";
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
    public void insert(Administrador administrador) throws SQLException {
        String sql = "INSERT INTO administrador (cedula, nombre, permisos, telefono, cuenta_idcuenta) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, administrador.getCedula());
            statement.setString(2, administrador.getNombre());
            statement.setString(3, administrador.getPermisos());
            statement.setString(4, administrador.getTelefono());
            setNullableInteger(statement, 5, administrador.getCuentaIdCuenta());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Administrador administrador) throws SQLException {
        String sql = "UPDATE administrador SET nombre = ?, permisos = ?, telefono = ?, cuenta_idcuenta = ? WHERE cedula = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, administrador.getNombre());
            statement.setString(2, administrador.getPermisos());
            statement.setString(3, administrador.getTelefono());
            setNullableInteger(statement, 4, administrador.getCuentaIdCuenta());
            statement.setInt(5, administrador.getCedula());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM administrador WHERE cedula = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Administrador mapRow(ResultSet resultSet) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setCedula(resultSet.getInt("cedula"));
        administrador.setNombre(resultSet.getString("nombre"));
        administrador.setPermisos(resultSet.getString("permisos"));
        administrador.setTelefono(resultSet.getString("telefono"));
        int cuentaId = resultSet.getInt("cuenta_idcuenta");
        if (!resultSet.wasNull()) {
            administrador.setCuentaIdCuenta(cuentaId);
        }
        return administrador;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
