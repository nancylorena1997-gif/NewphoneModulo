package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PagoDao implements CrudDao<Pago> {

    @Override
    public List<Pago> findAll() throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT idpagos, metodo, estado_pago, pedido_idpedido FROM pagos ORDER BY idpagos";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                pagos.add(mapRow(resultSet));
            }
        }
        return pagos;
    }

    @Override
    public Pago findById(Object id) throws SQLException {
        String sql = "SELECT idpagos, metodo, estado_pago, pedido_idpedido FROM pagos WHERE idpagos = ?";
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
    public void insert(Pago pago) throws SQLException {
        String sql = "INSERT INTO pagos (metodo, estado_pago, pedido_idpedido) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pago.getMetodo());
            statement.setString(2, pago.getEstadoPago());
            setNullableInteger(statement, 3, pago.getPedidoIdPedido());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pago.setIdPagos(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Pago pago) throws SQLException {
        String sql = "UPDATE pagos SET metodo = ?, estado_pago = ?, pedido_idpedido = ? WHERE idpagos = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pago.getMetodo());
            statement.setString(2, pago.getEstadoPago());
            setNullableInteger(statement, 3, pago.getPedidoIdPedido());
            statement.setInt(4, pago.getIdPagos());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM pagos WHERE idpagos = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Pago mapRow(ResultSet resultSet) throws SQLException {
        Pago pago = new Pago();
        pago.setIdPagos(resultSet.getInt("idpagos"));
        pago.setMetodo(resultSet.getString("metodo"));
        pago.setEstadoPago(resultSet.getString("estado_pago"));
        int pedidoId = resultSet.getInt("pedido_idpedido");
        if (!resultSet.wasNull()) {
            pago.setPedidoIdPedido(pedidoId);
        }
        return pago;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
