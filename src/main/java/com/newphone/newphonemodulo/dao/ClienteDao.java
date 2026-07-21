package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements CrudDao<Cliente> {

    @Override
    public List<Cliente> findAll() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT cedula, nombre, telefono, registro, cuenta_idcuenta FROM cliente ORDER BY cedula";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clientes.add(mapRow(resultSet));
            }
        }
        return clientes;
    }

    @Override
    public Cliente findById(Object id) throws SQLException {
        String sql = "SELECT cedula, nombre, telefono, registro, cuenta_idcuenta FROM cliente WHERE cedula = ?";
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
    public void insert(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (cedula, nombre, telefono, registro, cuenta_idcuenta) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cliente.getCedula());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getRegistro());
            setNullableInteger(statement, 5, cliente.getCuentaIdCuenta());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre = ?, telefono = ?, registro = ?, cuenta_idcuenta = ? WHERE cedula = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setString(3, cliente.getRegistro());
            setNullableInteger(statement, 4, cliente.getCuentaIdCuenta());
            statement.setInt(5, cliente.getCedula());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE cedula = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Cliente mapRow(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCedula(resultSet.getInt("cedula"));
        cliente.setNombre(resultSet.getString("nombre"));
        cliente.setTelefono(resultSet.getString("telefono"));
        cliente.setRegistro(resultSet.getString("registro"));
        int cuentaId = resultSet.getInt("cuenta_idcuenta");
        if (!resultSet.wasNull()) {
            cliente.setCuentaIdCuenta(cuentaId);
        }
        return cliente;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
