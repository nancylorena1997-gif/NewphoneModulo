package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.ProductoFavorito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoFavoritoDao implements CrudDao<ProductoFavorito> {

    @Override
    public List<ProductoFavorito> findAll() throws SQLException {
        List<ProductoFavorito> favoritos = new ArrayList<>();
        String sql = """
                SELECT idproducto_favorito, fecha_agregado, producto_idproducto, cliente_cedula
                FROM producto_favorito ORDER BY idproducto_favorito
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                favoritos.add(mapRow(resultSet));
            }
        }
        return favoritos;
    }

    @Override
    public ProductoFavorito findById(Object id) throws SQLException {
        String sql = """
                SELECT idproducto_favorito, fecha_agregado, producto_idproducto, cliente_cedula
                FROM producto_favorito WHERE idproducto_favorito = ?
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
    public void insert(ProductoFavorito productoFavorito) throws SQLException {
        String sql = """
                INSERT INTO producto_favorito (fecha_agregado, producto_idproducto, cliente_cedula)
                VALUES (?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, productoFavorito.getFechaAgregado());
            setNullableInteger(statement, 2, productoFavorito.getProductoIdProducto());
            setNullableInteger(statement, 3, productoFavorito.getClienteCedula());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    productoFavorito.setIdProductoFavorito(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(ProductoFavorito productoFavorito) throws SQLException {
        String sql = """
                UPDATE producto_favorito SET fecha_agregado = ?, producto_idproducto = ?, cliente_cedula = ?
                WHERE idproducto_favorito = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productoFavorito.getFechaAgregado());
            setNullableInteger(statement, 2, productoFavorito.getProductoIdProducto());
            setNullableInteger(statement, 3, productoFavorito.getClienteCedula());
            statement.setInt(4, productoFavorito.getIdProductoFavorito());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM producto_favorito WHERE idproducto_favorito = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private ProductoFavorito mapRow(ResultSet resultSet) throws SQLException {
        ProductoFavorito productoFavorito = new ProductoFavorito();
        productoFavorito.setIdProductoFavorito(resultSet.getInt("idproducto_favorito"));
        productoFavorito.setFechaAgregado(resultSet.getString("fecha_agregado"));
        int productoId = resultSet.getInt("producto_idproducto");
        if (!resultSet.wasNull()) {
            productoFavorito.setProductoIdProducto(productoId);
        }
        int clienteCedula = resultSet.getInt("cliente_cedula");
        if (!resultSet.wasNull()) {
            productoFavorito.setClienteCedula(clienteCedula);
        }
        return productoFavorito;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
