package com.newphone.newphonemodulo.model;

public class Resena {

    private Integer idResena;
    private String comentario;
    private Integer calificacion;
    private byte[] imagen;
    private Integer clienteCedula;
    private Integer productoIdProducto;

    public Resena() {
    }

    public Integer getIdResena() {
        return idResena;
    }

    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(Integer clienteCedula) {
        this.clienteCedula = clienteCedula;
    }

    public Integer getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(Integer productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }
}
