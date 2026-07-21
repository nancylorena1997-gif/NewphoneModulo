package com.newphone.newphonemodulo.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final Path DATABASE_PATH = Path.of("database", "newphone.db");
    private static final String JDBC_URL = "jdbc:sqlite:" + DATABASE_PATH.toAbsolutePath();

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL);
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        return connection;
    }

    public static Path getDatabasePath() {
        return DATABASE_PATH.toAbsolutePath();
    }
}
