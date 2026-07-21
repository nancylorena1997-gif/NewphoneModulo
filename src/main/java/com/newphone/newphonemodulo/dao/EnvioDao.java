package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Envio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnvioDao implements CrudDao<Envio> {

    @Override
    public List<Envio> findAll() throws SQLException {
        List<Envio> envios = new ArrayList<>();
        String sql = "SELECT id_envio, direccion, fecha_envio, estado_envio, pedido_idpedido FROM envio ORDER BY id_envio";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                envios.add(mapRow(resultSet));
            }
        }
        return envios;
    }

    @Override
    public Envio findById(Object id) throws SQLException {
        String sql = "SELECT id_envio, direccion, fecha_envio, estado_envio, pedido_idpedido FROM envio WHERE id_envio = ?";
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
    public void insert(Envio envio) throws SQLException {
        String sql = "INSERT INTO envio (direccion, fecha_envio, estado_envio, pedido_idpedido) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, envio.getDireccion());
            statement.setString(2, envio.getFechaEnvio());
            statement.setString(3, envio.getEstadoEnvio());
            setNullableInteger(statement, 4, envio.getPedidoIdPedido());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    envio.setIdEnvio(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Envio envio) throws SQLException {
        String sql = "UPDATE envio SET direccion = ?, fecha_envio = ?, estado_envio = ?, pedido_idpedido = ? WHERE id_envio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, envio.getDireccion());
            statement.setString(2, envio.getFechaEnvio());
            statement.setString(3, envio.getEstadoEnvio());
            setNullableInteger(statement, 4, envio.getPedidoIdPedido());
            statement.setInt(5, envio.getIdEnvio());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM envio WHERE id_envio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Envio mapRow(ResultSet resultSet) throws SQLException {
        Envio envio = new Envio();
        envio.setIdEnvio(resultSet.getInt("id_envio"));
        envio.setDireccion(resultSet.getString("direccion"));
        envio.setFechaEnvio(resultSet.getString("fecha_envio"));
        envio.setEstadoEnvio(resultSet.getString("estado_envio"));
        int pedidoId = resultSet.getInt("pedido_idpedido");
        if (!resultSet.wasNull()) {
            envio.setPedidoIdPedido(pedidoId);
        }
        return envio;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
