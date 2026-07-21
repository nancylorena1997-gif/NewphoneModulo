package com.newphone.newphonemodulo.model;

public class ReporteVenta {

    private Integer idReportesVenta;
    private String fechaInicial;
    private String fechaFinal;
    private String tipoReporte;
    private Double totalVentas;
    private Integer cantidadPedidos;
    private String archivoDescargable;
    private String analisisVenta;
    private Integer administradorCedula;

    public ReporteVenta() {
    }

    public Integer getIdReportesVenta() {
        return idReportesVenta;
    }

    public void setIdReportesVenta(Integer idReportesVenta) {
        this.idReportesVenta = idReportesVenta;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public Integer getCantidadPedidos() {
        return cantidadPedidos;
    }

    public void setCantidadPedidos(Integer cantidadPedidos) {
        this.cantidadPedidos = cantidadPedidos;
    }

    public String getArchivoDescargable() {
        return archivoDescargable;
    }

    public void setArchivoDescargable(String archivoDescargable) {
        this.archivoDescargable = archivoDescargable;
    }

    public String getAnalisisVenta() {
        return analisisVenta;
    }

    public void setAnalisisVenta(String analisisVenta) {
        this.analisisVenta = analisisVenta;
    }

    public Integer getAdministradorCedula() {
        return administradorCedula;
    }

    public void setAdministradorCedula(Integer administradorCedula) {
        this.administradorCedula = administradorCedula;
    }
}
