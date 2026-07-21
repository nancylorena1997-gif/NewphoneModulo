package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.AtencionCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AtencionClienteDao implements CrudDao<AtencionCliente> {

    @Override
    public List<AtencionCliente> findAll() throws SQLException {
        List<AtencionCliente> tickets = new ArrayList<>();
        String sql = "SELECT id_ticket, mensaje, fecha, respuesta, cliente_cedula FROM atencion_cliente ORDER BY id_ticket";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                tickets.add(mapRow(resultSet));
            }
        }
        return tickets;
    }

    @Override
    public AtencionCliente findById(Object id) throws SQLException {
        String sql = "SELECT id_ticket, mensaje, fecha, respuesta, cliente_cedula FROM atencion_cliente WHERE id_ticket = ?";
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
    public void insert(AtencionCliente atencionCliente) throws SQLException {
        String sql = "INSERT INTO atencion_cliente (mensaje, fecha, respuesta, cliente_cedula) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, atencionCliente.getMensaje());
            statement.setString(2, atencionCliente.getFecha());
            statement.setString(3, atencionCliente.getRespuesta());
            setNullableInteger(statement, 4, atencionCliente.getClienteCedula());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    atencionCliente.setIdTicket(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(AtencionCliente atencionCliente) throws SQLException {
        String sql = "UPDATE atencion_cliente SET mensaje = ?, fecha = ?, respuesta = ?, cliente_cedula = ? WHERE id_ticket = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, atencionCliente.getMensaje());
            statement.setString(2, atencionCliente.getFecha());
            statement.setString(3, atencionCliente.getRespuesta());
            setNullableInteger(statement, 4, atencionCliente.getClienteCedula());
            statement.setInt(5, atencionCliente.getIdTicket());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM atencion_cliente WHERE id_ticket = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private AtencionCliente mapRow(ResultSet resultSet) throws SQLException {
        AtencionCliente atencionCliente = new AtencionCliente();
        atencionCliente.setIdTicket(resultSet.getInt("id_ticket"));
        atencionCliente.setMensaje(resultSet.getString("mensaje"));
        atencionCliente.setFecha(resultSet.getString("fecha"));
        atencionCliente.setRespuesta(resultSet.getString("respuesta"));
        int clienteCedula = resultSet.getInt("cliente_cedula");
        if (!resultSet.wasNull()) {
            atencionCliente.setClienteCedula(clienteCedula);
        }
        return atencionCliente;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
