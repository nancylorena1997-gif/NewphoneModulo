package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Resena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResenaDao implements CrudDao<Resena> {

    @Override
    public List<Resena> findAll() throws SQLException {
        List<Resena> resenas = new ArrayList<>();
        String sql = """
                SELECT id_resena, comentario, calificacion, imagen, cliente_cedula, producto_idproducto
                FROM resena ORDER BY id_resena
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                resenas.add(mapRow(resultSet));
            }
        }
        return resenas;
    }

    @Override
    public Resena findById(Object id) throws SQLException {
        String sql = """
                SELECT id_resena, comentario, calificacion, imagen, cliente_cedula, producto_idproducto
                FROM resena WHERE id_resena = ?
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
    public void insert(Resena resena) throws SQLException {
        String sql = """
                INSERT INTO resena (comentario, calificacion, imagen, cliente_cedula, producto_idproducto)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindResena(statement, resena);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    resena.setIdResena(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Resena resena) throws SQLException {
        String sql = """
                UPDATE resena SET comentario = ?, calificacion = ?, imagen = ?,
                                  cliente_cedula = ?, producto_idproducto = ?
                WHERE id_resena = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, resena.getComentario());
            statement.setInt(2, resena.getCalificacion());
            if (resena.getImagen() == null) {
                statement.setNull(3, java.sql.Types.BLOB);
            } else {
                statement.setBytes(3, resena.getImagen());
            }
            setNullableInteger(statement, 4, resena.getClienteCedula());
            setNullableInteger(statement, 5, resena.getProductoIdProducto());
            statement.setInt(6, resena.getIdResena());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM resena WHERE id_resena = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private void bindResena(PreparedStatement statement, Resena resena) throws SQLException {
        statement.setString(1, resena.getComentario());
        statement.setInt(2, resena.getCalificacion());
        if (resena.getImagen() == null) {
            statement.setNull(3, java.sql.Types.BLOB);
        } else {
            statement.setBytes(3, resena.getImagen());
        }
        setNullableInteger(statement, 4, resena.getClienteCedula());
        setNullableInteger(statement, 5, resena.getProductoIdProducto());
    }

    private Resena mapRow(ResultSet resultSet) throws SQLException {
        Resena resena = new Resena();
        resena.setIdResena(resultSet.getInt("id_resena"));
        resena.setComentario(resultSet.getString("comentario"));
        resena.setCalificacion(resultSet.getInt("calificacion"));
        resena.setImagen(resultSet.getBytes("imagen"));
        int clienteCedula = resultSet.getInt("cliente_cedula");
        if (!resultSet.wasNull()) {
            resena.setClienteCedula(clienteCedula);
        }
        int productoId = resultSet.getInt("producto_idproducto");
        if (!resultSet.wasNull()) {
            resena.setProductoIdProducto(productoId);
        }
        return resena;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
