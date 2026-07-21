package com.newphone.newphonemodulo.model;

public class ProductoFavorito {

    private Integer idProductoFavorito;
    private String fechaAgregado;
    private Integer productoIdProducto;
    private Integer clienteCedula;

    public ProductoFavorito() {
    }

    public Integer getIdProductoFavorito() {
        return idProductoFavorito;
    }

    public void setIdProductoFavorito(Integer idProductoFavorito) {
        this.idProductoFavorito = idProductoFavorito;
    }

    public String getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(String fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public Integer getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(Integer productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    public Integer getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(Integer clienteCedula) {
        this.clienteCedula = clienteCedula;
    }
}
