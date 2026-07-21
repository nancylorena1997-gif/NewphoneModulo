package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao implements CrudDao<Pedido> {

    @Override
    public List<Pedido> findAll() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT idpedido, fecha, estado, total, cliente_cedula FROM pedido ORDER BY idpedido";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                pedidos.add(mapRow(resultSet));
            }
        }
        return pedidos;
    }

    @Override
    public Pedido findById(Object id) throws SQLException {
        String sql = "SELECT idpedido, fecha, estado, total, cliente_cedula FROM pedido WHERE idpedido = ?";
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
    public void insert(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (fecha, estado, total, cliente_cedula) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pedido.getFecha());
            statement.setString(2, pedido.getEstado());
            statement.setDouble(3, pedido.getTotal());
            setNullableInteger(statement, 4, pedido.getClienteCedula());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setIdPedido(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET fecha = ?, estado = ?, total = ?, cliente_cedula = ? WHERE idpedido = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pedido.getFecha());
            statement.setString(2, pedido.getEstado());
            statement.setDouble(3, pedido.getTotal());
            setNullableInteger(statement, 4, pedido.getClienteCedula());
            statement.setInt(5, pedido.getIdPedido());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM pedido WHERE idpedido = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Pedido mapRow(ResultSet resultSet) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(resultSet.getInt("idpedido"));
        pedido.setFecha(resultSet.getString("fecha"));
        pedido.setEstado(resultSet.getString("estado"));
        pedido.setTotal(resultSet.getDouble("total"));
        int clienteCedula = resultSet.getInt("cliente_cedula");
        if (!resultSet.wasNull()) {
            pedido.setClienteCedula(clienteCedula);
        }
        return pedido;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
