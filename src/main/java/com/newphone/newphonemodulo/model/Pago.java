package com.newphone.newphonemodulo.model;

public class Pago {

    private Integer idPagos;
    private String metodo;
    private String estadoPago;
    private Integer pedidoIdPedido;

    public Pago() {
    }

    public Integer getIdPagos() {
        return idPagos;
    }

    public void setIdPagos(Integer idPagos) {
        this.idPagos = idPagos;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Integer getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(Integer pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }
}
