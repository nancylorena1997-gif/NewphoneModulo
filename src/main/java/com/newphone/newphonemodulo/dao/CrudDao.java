package com.newphone.newphonemodulo.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {

    List<T> findAll() throws SQLException;

    T findById(Object id) throws SQLException;

    void insert(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(Object id) throws SQLException;
}
