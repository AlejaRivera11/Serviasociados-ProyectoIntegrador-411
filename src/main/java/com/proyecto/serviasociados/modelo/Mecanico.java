package com.proyecto.serviasociados.modelo;

public class Mecanico {

    private long idMecanico;
    private String nombreMecanico;
    private String telefonoMecanico;
    private String direccionMecanico;

    //Constructores
    public Mecanico() {
    }

    public Mecanico(long idMecanico, String nombreMecanico, String telefonoMecanico, String direccionMecanico) {
        this.idMecanico = idMecanico;
        this.nombreMecanico = nombreMecanico;
        this.telefonoMecanico = telefonoMecanico;
        this.direccionMecanico = direccionMecanico;
    }

    //Getters y Setters
    public long getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(long idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public void setNombreMecanico(String nombreMecanico) {
        this.nombreMecanico = nombreMecanico;
    }

    public String getTelefonoMecanico() {
        return telefonoMecanico;
    }

    public void setTelefonoMecanico(String telefonoMecanico) {
        this.telefonoMecanico = telefonoMecanico;
    }

    public String getDireccionMecanico() {
        return direccionMecanico;
    }

    public void setDireccionMecanico(String direccionMecanico) {
        this.direccionMecanico = direccionMecanico;
    }

}
