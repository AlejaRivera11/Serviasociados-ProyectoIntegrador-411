package com.proyecto.serviasociados.modelo;

public class ServicioModelo {
    
    private int servicioId;
    private String nomServicio;
    private String tiempo;

    //constructores 

    public ServicioModelo() {
    }

    public ServicioModelo(int servicioId, String nomServicio, String tiempo) {
        this.servicioId = servicioId;
        this.nomServicio = nomServicio;
        this.tiempo = tiempo;
    }

    //getters y setters

    public int getServicioId() {
        return servicioId;
    }
    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }
    public String getNomServicio() {
        return nomServicio;
    }
    public void setNomServicio(String nomServicio) {
        this.nomServicio = nomServicio;
    }
    public String getTiempo() {
        return tiempo;
    }
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    
}
