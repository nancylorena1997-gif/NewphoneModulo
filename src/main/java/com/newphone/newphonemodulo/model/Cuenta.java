package com.newphone.newphonemodulo.model;

public class Cuenta {

    private Integer idCuenta;
    private String contrasena;
    private String email;

    public Cuenta() {
    }

    public Cuenta(Integer idCuenta, String contrasena, String email) {
        this.idCuenta = idCuenta;
        this.contrasena = contrasena;
        this.email = email;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
