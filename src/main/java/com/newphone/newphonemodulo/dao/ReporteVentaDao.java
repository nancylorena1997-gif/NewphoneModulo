package com.newphone.newphonemodulo.dao;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.model.ReporteVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReporteVentaDao implements CrudDao<ReporteVenta> {

    @Override
    public List<ReporteVenta> findAll() throws SQLException {
        List<ReporteVenta> reportes = new ArrayList<>();
        String sql = """
                SELECT idreportes_venta, fecha_inicial, fecha_final, tipo_reporte, total_ventas,
                       cantidad_pedidos, archivo_descargable, analisis_venta, administrador_cedula
                FROM reportes_venta ORDER BY idreportes_venta
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                reportes.add(mapRow(resultSet));
            }
        }
        return reportes;
    }

    @Override
    public ReporteVenta findById(Object id) throws SQLException {
        String sql = """
                SELECT idreportes_venta, fecha_inicial, fecha_final, tipo_reporte, total_ventas,
                       cantidad_pedidos, archivo_descargable, analisis_venta, administrador_cedula
                FROM reportes_venta WHERE idreportes_venta = ?
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
    public void insert(ReporteVenta reporteVenta) throws SQLException {
        String sql = """
                INSERT INTO reportes_venta (fecha_inicial, fecha_final, tipo_reporte, total_ventas,
                                          cantidad_pedidos, archivo_descargable, analisis_venta, administrador_cedula)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindReporte(statement, reporteVenta);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reporteVenta.setIdReportesVenta(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(ReporteVenta reporteVenta) throws SQLException {
        String sql = """
                UPDATE reportes_venta SET fecha_inicial = ?, fecha_final = ?, tipo_reporte = ?, total_ventas = ?,
                                          cantidad_pedidos = ?, archivo_descargable = ?, analisis_venta = ?,
                                          administrador_cedula = ?
                WHERE idreportes_venta = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reporteVenta.getFechaInicial());
            statement.setString(2, reporteVenta.getFechaFinal());
            statement.setString(3, reporteVenta.getTipoReporte());
            statement.setDouble(4, reporteVenta.getTotalVentas());
            statement.setInt(5, reporteVenta.getCantidadPedidos());
            statement.setString(6, reporteVenta.getArchivoDescargable());
            statement.setString(7, reporteVenta.getAnalisisVenta());
            setNullableInteger(statement, 8, reporteVenta.getAdministradorCedula());
            statement.setInt(9, reporteVenta.getIdReportesVenta());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Object id) throws SQLException {
        String sql = "DELETE FROM reportes_venta WHERE idreportes_venta = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
        }
    }

    private void bindReporte(PreparedStatement statement, ReporteVenta reporteVenta) throws SQLException {
        statement.setString(1, reporteVenta.getFechaInicial());
        statement.setString(2, reporteVenta.getFechaFinal());
        statement.setString(3, reporteVenta.getTipoReporte());
        statement.setDouble(4, reporteVenta.getTotalVentas());
        statement.setInt(5, reporteVenta.getCantidadPedidos());
        statement.setString(6, reporteVenta.getArchivoDescargable());
        statement.setString(7, reporteVenta.getAnalisisVenta());
        setNullableInteger(statement, 8, reporteVenta.getAdministradorCedula());
    }

    private ReporteVenta mapRow(ResultSet resultSet) throws SQLException {
        ReporteVenta reporteVenta = new ReporteVenta();
        reporteVenta.setIdReportesVenta(resultSet.getInt("idreportes_venta"));
        reporteVenta.setFechaInicial(resultSet.getString("fecha_inicial"));
        reporteVenta.setFechaFinal(resultSet.getString("fecha_final"));
        reporteVenta.setTipoReporte(resultSet.getString("tipo_reporte"));
        reporteVenta.setTotalVentas(resultSet.getDouble("total_ventas"));
        reporteVenta.setCantidadPedidos(resultSet.getInt("cantidad_pedidos"));
        reporteVenta.setArchivoDescargable(resultSet.getString("archivo_descargable"));
        reporteVenta.setAnalisisVenta(resultSet.getString("analisis_venta"));
        int administradorCedula = resultSet.getInt("administrador_cedula");
        if (!resultSet.wasNull()) {
            reporteVenta.setAdministradorCedula(administradorCedula);
        }
        return reporteVenta;
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
