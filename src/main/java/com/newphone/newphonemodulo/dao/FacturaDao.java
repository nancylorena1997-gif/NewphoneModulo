package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao implements CrudDao<Factura> {

    @Override
    public List<Factura> findAll() throws SQLException {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT idfactura, fecha, iva, descuento, total, pedido_idpedido FROM factura ORDER BY idfactura";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                facturas.add(mapRow(resultSet));
            }
        }
        return facturas;
    }

    @Override
    public Factura findById(Object id) throws SQLException {
        String sql = "SELECT idfactura, fecha, iva, descuento, total, pedido_idpedido FROM factura WHERE idfactura = ?";
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
    public void insert(Factura factura) throws SQLException {
        String sql = "INSERT INTO factura (fecha, iva, descuento, total, pedido_idpedido) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, factura.getFecha());
            statement.setDouble(2, factura.getIva());
            statement.setDouble(3, factura.getDescuento());
            statement.setDouble(4, factura.getTotal());
            setNullableInteger(statement, 5, factura.getPedidoIdPedido());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    factura.setIdFactura(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Factura factura) throws SQLException {
        String sql = "UPDATE factura SET fecha = ?, iva = ?, descuento = ?, total = ?, pedido_idpedido = ? WHERE idfactura = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, factura.getFecha());
            statement.setDouble(2, factura.getIva());
            statement.setDouble(3, factura.getDescuento());
            statement.setDouble(4, factura.getTotal());
            setNullableInteger(statement, 5, factura.getPedidoIdPedido());
            statement.setInt(6, factura.getIdFactura());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM factura WHERE idfactura = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Factura mapRow(ResultSet resultSet) throws SQLException {
        Factura factura = new Factura();
        factura.setIdFactura(resultSet.getInt("idfactura"));
        factura.setFecha(resultSet.getString("fecha"));
        factura.setIva(resultSet.getDouble("iva"));
        factura.setDescuento(resultSet.getDouble("descuento"));
        factura.setTotal(resultSet.getDouble("total"));
        int pedidoId = resultSet.getInt("pedido_idpedido");
        if (!resultSet.wasNull()) {
            factura.setPedidoIdPedido(pedidoId);
        }
        return factura;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
