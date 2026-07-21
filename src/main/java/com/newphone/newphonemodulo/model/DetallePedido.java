package com.newphone.newphonemodulo.model;

public class DetallePedido {

    private Integer idDetallePedido;
    private Integer cantidad;
    private Double subtotal;
    private Integer pedidoIdPedido;
    private Integer productoIdProducto;

    public DetallePedido() {
    }

    public Integer getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(Integer pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }

    public Integer getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(Integer productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }
}
