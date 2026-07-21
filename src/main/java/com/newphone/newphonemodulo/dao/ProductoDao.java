package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao implements CrudDao<Producto> {

    @Override
    public List<Producto> findAll() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = """
                SELECT idproducto, nombre, precio, stock, descripcion, imagen,
                       administrador_cedula, categoria_id_categoria
                FROM producto ORDER BY idproducto
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                productos.add(mapRow(resultSet));
            }
        }
        return productos;
    }

    @Override
    public Producto findById(Object id) throws SQLException {
        String sql = """
                SELECT idproducto, nombre, precio, stock, descripcion, imagen,
                       administrador_cedula, categoria_id_categoria
                FROM producto WHERE idproducto = ?
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
    public void insert(Producto producto) throws SQLException {
        String sql = """
                INSERT INTO producto (nombre, precio, stock, descripcion, imagen,
                                      administrador_cedula, categoria_id_categoria)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindProducto(statement, producto);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setIdProducto(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Producto producto) throws SQLException {
        String sql = """
                UPDATE producto SET nombre = ?, precio = ?, stock = ?, descripcion = ?, imagen = ?,
                                    administrador_cedula = ?, categoria_id_categoria = ?
                WHERE idproducto = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setString(4, producto.getDescripcion());
            if (producto.getImagen() == null) {
                statement.setNull(5, java.sql.Types.BLOB);
            } else {
                statement.setBytes(5, producto.getImagen());
            }
            setNullableInteger(statement, 6, producto.getAdministradorCedula());
            setNullableInteger(statement, 7, producto.getCategoriaIdCategoria());
            statement.setInt(8, producto.getIdProducto());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM producto WHERE idproducto = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private void bindProducto(PreparedStatement statement, Producto producto) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setDouble(2, producto.getPrecio());
        statement.setInt(3, producto.getStock());
        statement.setString(4, producto.getDescripcion());
        if (producto.getImagen() == null) {
            statement.setNull(5, java.sql.Types.BLOB);
        } else {
            statement.setBytes(5, producto.getImagen());
        }
        setNullableInteger(statement, 6, producto.getAdministradorCedula());
        setNullableInteger(statement, 7, producto.getCategoriaIdCategoria());
    }

    private Producto mapRow(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(resultSet.getInt("idproducto"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getDouble("precio"));
        producto.setStock(resultSet.getInt("stock"));
        producto.setDescripcion(resultSet.getString("descripcion"));
        producto.setImagen(resultSet.getBytes("imagen"));
        int administradorCedula = resultSet.getInt("administrador_cedula");
        if (!resultSet.wasNull()) {
            producto.setAdministradorCedula(administradorCedula);
        }
        int categoriaId = resultSet.getInt("categoria_id_categoria");
        if (!resultSet.wasNull()) {
            producto.setCategoriaIdCategoria(categoriaId);
        }
        return producto;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
