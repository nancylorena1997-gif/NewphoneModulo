package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao implements CrudDao<Categoria> {

    @Override
    public List<Categoria> findAll() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre FROM categoria ORDER BY id_categoria";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                categorias.add(mapRow(resultSet));
            }
        }
        return categorias;
    }

    @Override
    public Categoria findById(Object id) throws SQLException {
        String sql = "SELECT id_categoria, nombre FROM categoria WHERE id_categoria = ?";
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
    public void insert(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, categoria.getNombre());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setIdCategoria(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET nombre = ? WHERE id_categoria = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoria.getNombre());
            statement.setInt(2, categoria.getIdCategoria());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private Categoria mapRow(ResultSet resultSet) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(resultSet.getInt("id_categoria"));
        categoria.setNombre(resultSet.getString("nombre"));
        return categoria;
    }
}
