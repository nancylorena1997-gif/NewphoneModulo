package com.newphone.newphonemodulo.model;

public class Cliente {

    private Integer cedula;
    private String nombre;
    private String telefono;
    private String registro;
    private Integer cuentaIdCuenta;

    public Cliente() {
    }

    public Cliente(Integer cedula, String nombre, String telefono, String registro, Integer cuentaIdCuenta) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.registro = registro;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Integer getCuentaIdCuenta() {
        return cuentaIdCuenta;
    }

    public void setCuentaIdCuenta(Integer cuentaIdCuenta) {
        this.cuentaIdCuenta = cuentaIdCuenta;
    }
}
