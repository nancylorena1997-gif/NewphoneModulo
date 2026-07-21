package com.newphone.newphonemodulo.model;

public class Factura {

    private Integer idFactura;
    private String fecha;
    private Double iva;
    private Double descuento;
    private Double total;
    private Integer pedidoIdPedido;

    public Factura() {
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(Integer pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }
}
