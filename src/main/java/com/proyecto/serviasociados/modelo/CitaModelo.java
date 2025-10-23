package com.proyecto.serviasociados.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaModelo {

    private int citaId;
    private LocalDate fechaRegistro;
    private LocalTime horaRegistro;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private int estadoId;
    private String placa;
    private int usuarioId;

    //constructor

    public CitaModelo(int citaId, LocalDate fechaRegistro, LocalTime horaRegistro, LocalDate fechaCita, LocalTime horaCita, int estadoId, String placa, int usuarioId) {
        this.citaId = citaId;
        this.fechaRegistro = fechaRegistro;
        this.horaRegistro = horaRegistro;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estadoId = estadoId;
        this.placa = placa;
        this.usuarioId = usuarioId;
    }

    //getters y setters         
    
    public int getCitaId() {
        return citaId;
    }

    public void setCitaId(int citaId) {
        this.citaId = citaId;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(LocalTime horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }   


} 