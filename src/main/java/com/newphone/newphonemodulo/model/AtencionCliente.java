package com.newphone.newphonemodulo.model;

public class AtencionCliente {

    private Integer idTicket;
    private String mensaje;
    private String fecha;
    private String respuesta;
    private Integer clienteCedula;

    public AtencionCliente() {
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Integer getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(Integer clienteCedula) {
        this.clienteCedula = clienteCedula;
    }
}
