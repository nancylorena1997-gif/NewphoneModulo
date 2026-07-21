package com.newphone.newphonemodulo.model;

public class Envio {

    private Integer idEnvio;
    private String direccion;
    private String fechaEnvio;
    private String estadoEnvio;
    private Integer pedidoIdPedido;

    public Envio() {
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public Integer getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(Integer pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }
}
