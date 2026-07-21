package com.newphone.newphonemodulo.model;

public class DetalleCarrito {

    private Integer idDetalleCarrito;
    private Double subtotal;
    private Integer cantidad;
    private Double precioUnitario;
    private Integer productoIdProducto;
    private Integer carritoIdCarrito;

    public DetalleCarrito() {
    }

    public Integer getIdDetalleCarrito() {
        return idDetalleCarrito;
    }

    public void setIdDetalleCarrito(Integer idDetalleCarrito) {
        this.idDetalleCarrito = idDetalleCarrito;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(Integer productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    public Integer getCarritoIdCarrito() {
        return carritoIdCarrito;
    }

    public void setCarritoIdCarrito(Integer carritoIdCarrito) {
        this.carritoIdCarrito = carritoIdCarrito;
    }
}
