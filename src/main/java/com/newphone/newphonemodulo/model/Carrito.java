package com.newphone.newphonemodulo.model;

public class Carrito {

    private Integer idCarrito;
    private String fecha;
    private Integer clienteCedula;

    public Carrito() {
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(Integer clienteCedula) {
        this.clienteCedula = clienteCedula;
    }
}
