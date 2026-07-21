package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Carrito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarritoDao implements CrudDao<Carrito> {

    @Override
    public List<Carrito> findAll() throws SQLException {
        List<Carrito> carritos = new ArrayList<>();
        String sql = "SELECT idcarrito, fecha, cliente_cedula FROM carrito ORDER BY idcarrito";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                carritos.add(mapRow(resultSet));
            }
        }
        return carritos;
    }

    @Override
    public Carrito findById(Object id) throws SQLException {
        String sql = "SELECT idcarrito, fecha, cliente_cedula FROM carrito WHERE idcarrito = ?";
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
    public void insert(Carrito carrito) throws SQLException {
        String sql = "INSERT INTO carrito (fecha, cliente_cedula) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, carrito.getFecha());
            setNullableInteger(statement, 2, carrito.getClienteCedula());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carrito.setIdCarrito(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Carrito carrito) throws SQLException {
        String sql = "UPDATE carrito SET fecha = ?, cliente_cedula = ? WHERE idcarrito = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carrito.getFecha());
            setNullableInteger(statement, 2, carrito.getClienteCedula());
            statement.setInt(3, carrito.getIdCarrito());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM carrito WHERE idcarrito = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Carrito mapRow(ResultSet resultSet) throws SQLException {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(resultSet.getInt("idcarrito"));
        carrito.setFecha(resultSet.getString("fecha"));
        int clienteCedula = resultSet.getInt("cliente_cedula");
        if (!resultSet.wasNull()) {
            carrito.setClienteCedula(clienteCedula);
        }
        return carrito;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
