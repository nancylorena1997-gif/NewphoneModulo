package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.DetalleCarrito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetalleCarritoDao implements CrudDao<DetalleCarrito> {

    @Override
    public List<DetalleCarrito> findAll() throws SQLException {
        List<DetalleCarrito> detalles = new ArrayList<>();
        String sql = """
                SELECT iddetalle_carrito, subtotal, cantidad, precio_unitario,
                       producto_idproducto, carrito_idcarrito
                FROM detalle_carrito ORDER BY iddetalle_carrito
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
    public DetalleCarrito findById(Object id) throws SQLException {
        String sql = """
                SELECT iddetalle_carrito, subtotal, cantidad, precio_unitario,
                       producto_idproducto, carrito_idcarrito
                FROM detalle_carrito WHERE iddetalle_carrito = ?
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
    public void insert(DetalleCarrito detalleCarrito) throws SQLException {
        String sql = """
                INSERT INTO detalle_carrito (subtotal, cantidad, precio_unitario, producto_idproducto, carrito_idcarrito)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, detalleCarrito.getSubtotal());
            statement.setInt(2, detalleCarrito.getCantidad());
            statement.setDouble(3, detalleCarrito.getPrecioUnitario());
            setNullableInteger(statement, 4, detalleCarrito.getProductoIdProducto());
            setNullableInteger(statement, 5, detalleCarrito.getCarritoIdCarrito());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalleCarrito.setIdDetalleCarrito(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(DetalleCarrito detalleCarrito) throws SQLException {
        String sql = """
                UPDATE detalle_carrito SET subtotal = ?, cantidad = ?, precio_unitario = ?,
                                           producto_idproducto = ?, carrito_idcarrito = ?
                WHERE iddetalle_carrito = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, detalleCarrito.getSubtotal());
            statement.setInt(2, detalleCarrito.getCantidad());
            statement.setDouble(3, detalleCarrito.getPrecioUnitario());
            setNullableInteger(statement, 4, detalleCarrito.getProductoIdProducto());
            setNullableInteger(statement, 5, detalleCarrito.getCarritoIdCarrito());
            statement.setInt(6, detalleCarrito.getIdDetalleCarrito());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM detalle_carrito WHERE iddetalle_carrito = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private DetalleCarrito mapRow(ResultSet resultSet) throws SQLException {
        DetalleCarrito detalleCarrito = new DetalleCarrito();
        detalleCarrito.setIdDetalleCarrito(resultSet.getInt("iddetalle_carrito"));
        detalleCarrito.setSubtotal(resultSet.getDouble("subtotal"));
        detalleCarrito.setCantidad(resultSet.getInt("cantidad"));
        detalleCarrito.setPrecioUnitario(resultSet.getDouble("precio_unitario"));
        int productoId = resultSet.getInt("producto_idproducto");
        if (!resultSet.wasNull()) {
            detalleCarrito.setProductoIdProducto(productoId);
        }
        int carritoId = resultSet.getInt("carrito_idcarrito");
        if (!resultSet.wasNull()) {
            detalleCarrito.setCarritoIdCarrito(carritoId);
        }
        return detalleCarrito;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
