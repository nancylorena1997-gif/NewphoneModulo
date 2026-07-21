package com.newphone.newphonemodulo.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {
        try {
            Path databaseDirectory = DatabaseConnection.getDatabasePath().getParent();
            if (databaseDirectory != null) {
                Files.createDirectories(databaseDirectory);
            }

            if (!databaseExists()) {
                executeScript("database/schema.sql");
                executeScript("database/seed.sql");
            }
        } catch (IOException | SQLException exception) {
            throw new IllegalStateException("No se pudo inicializar la base de datos SQLite.", exception);
        }
    }

    private static boolean databaseExists() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT name FROM sqlite_master WHERE type='table' AND name='cuenta'")) {
            return resultSet.next();
        }
    }

    private static void executeScript(String resourcePath) throws IOException, SQLException {
        String script = readResource(resourcePath);
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            for (String command : script.split(";")) {
                String trimmedCommand = command.trim();
                if (!trimmedCommand.isEmpty()) {
                    statement.execute(trimmedCommand);
                }
            }
        }
    }

    private static String readResource(String resourcePath) throws IOException {
        try (InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Recurso no encontrado: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
