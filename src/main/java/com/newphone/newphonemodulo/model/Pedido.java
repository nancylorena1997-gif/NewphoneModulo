package com.newphone.newphonemodulo.model;

public class Pedido {

    private Integer idPedido;
    private String fecha;
    private String estado;
    private Double total;
    private Integer clienteCedula;

    public Pedido() {
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(Integer clienteCedula) {
        this.clienteCedula = clienteCedula;
    }
}
