package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDao implements CrudDao<DetallePedido> {

    @Override
    public List<DetallePedido> findAll() throws SQLException {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = """
                SELECT iddetalle_pedido, cantidad, subtotal, pedido_idpedido, producto_idproducto
                FROM detalle_pedido ORDER BY iddetalle_pedido
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                detalles.add(mapRow(resultSet));
            }
        }
        return detalles;
    }

    @Override
    public DetallePedido findById(Object id) throws SQLException {
        String sql = """
                SELECT iddetalle_pedido, cantidad, subtotal, pedido_idpedido, producto_idproducto
                FROM detalle_pedido WHERE iddetalle_pedido = ?
                """;
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
    public void insert(DetallePedido detallePedido) throws SQLException {
        String sql = """
                INSERT INTO detalle_pedido (cantidad, subtotal, pedido_idpedido, producto_idproducto)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, detallePedido.getCantidad());
            statement.setDouble(2, detallePedido.getSubtotal());
            setNullableInteger(statement, 3, detallePedido.getPedidoIdPedido());
            setNullableInteger(statement, 4, detallePedido.getProductoIdProducto());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detallePedido.setIdDetallePedido(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(DetallePedido detallePedido) throws SQLException {
        String sql = """
                UPDATE detalle_pedido SET cantidad = ?, subtotal = ?, pedido_idpedido = ?, producto_idproducto = ?
                WHERE iddetalle_pedido = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, detallePedido.getCantidad());
            statement.setDouble(2, detallePedido.getSubtotal());
            setNullableInteger(statement, 3, detallePedido.getPedidoIdPedido());
            setNullableInteger(statement, 4, detallePedido.getProductoIdProducto());
            statement.setInt(5, detallePedido.getIdDetallePedido());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM detalle_pedido WHERE iddetalle_pedido = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private DetallePedido mapRow(ResultSet resultSet) throws SQLException {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setIdDetallePedido(resultSet.getInt("iddetalle_pedido"));
        detallePedido.setCantidad(resultSet.getInt("cantidad"));
        detallePedido.setSubtotal(resultSet.getDouble("subtotal"));
        int pedidoId = resultSet.getInt("pedido_idpedido");
        if (!resultSet.wasNull()) {
            detallePedido.setPedidoIdPedido(pedidoId);
        }
        int productoId = resultSet.getInt("producto_idproducto");
        if (!resultSet.wasNull()) {
            detallePedido.setProductoIdProducto(productoId);
        }
        return detallePedido;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
