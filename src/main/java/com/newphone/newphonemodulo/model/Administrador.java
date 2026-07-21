package com.newphone.newphonemodulo.model;

public class Administrador {

    private Integer cedula;
    private String nombre;
    private String permisos;
    private String telefono;
    private Integer cuentaIdCuenta;

    public Administrador() {
    }

    public Administrador(Integer cedula, String nombre, String permisos, String telefono, Integer cuentaIdCuenta) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.permisos = permisos;
        this.telefono = telefono;
        this.cuentaIdCuenta = cuentaIdCuenta;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getCuentaIdCuenta() {
        return cuentaIdCuenta;
    }

    public void setCuentaIdCuenta(Integer cuentaIdCuenta) {
        this.cuentaIdCuenta = cuentaIdCuenta;
    }
}
