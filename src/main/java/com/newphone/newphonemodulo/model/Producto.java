package com.newphone.newphonemodulo.model;

public class Producto {

    private Integer idProducto;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String descripcion;
    private byte[] imagen;
    private Integer administradorCedula;
    private Integer categoriaIdCategoria;

    public Producto() {
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getAdministradorCedula() {
        return administradorCedula;
    }

    public void setAdministradorCedula(Integer administradorCedula) {
        this.administradorCedula = administradorCedula;
    }

    public Integer getCategoriaIdCategoria() {
        return categoriaIdCategoria;
    }

    public void setCategoriaIdCategoria(Integer categoriaIdCategoria) {
        this.categoriaIdCategoria = categoriaIdCategoria;
    }
}
