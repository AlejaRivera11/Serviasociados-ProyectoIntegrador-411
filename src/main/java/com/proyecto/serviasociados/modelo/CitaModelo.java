package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

import com.proyecto.serviasociados.services.ConexionBDD;

public class CitaModelo {

    private int citaId;
    private LocalDate fechaRegistro;
    private LocalTime horaRegistro;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private int estadoId;
    private String placa;
    private int usuarioId;
    private String nombreEstado;
    private String nombreCliente;
    private String nombreServicio;
    private String nombreMecanico;
    private String usuario;

    public CitaModelo() {
    }

    public CitaModelo(int citaId, LocalDate fechaRegistro, LocalTime horaRegistro, LocalDate fechaCita,
            LocalTime horaCita, int estadoId, String placa, int usuarioId) {
        this.citaId = citaId;
        this.fechaRegistro = fechaRegistro;
        this.horaRegistro = horaRegistro;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estadoId = estadoId;
        this.placa = placa;
        this.usuarioId = usuarioId;
    }

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

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public void setNombreMecanico(String nombreMecanico) {
        this.nombreMecanico = nombreMecanico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Método para agendar una cita
    public boolean agendarCita(LocalDate fechaCita, LocalTime horaCita, String placa, int usuarioId, int mecanicoId, int servicioId) throws SQLException {
        String sql = "{CALL sp_agendar_cita(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {

            cs.setDate(1, java.sql.Date.valueOf(fechaCita));
            cs.setTime(2, java.sql.Time.valueOf(horaCita));
            cs.setString(3, placa);
            cs.setInt(4, usuarioId);
            cs.setInt(5, mecanicoId);
            cs.setInt(6, servicioId);
            cs.execute();
            return true;

        }
    }

    // Método para actualizar el estado de una cita
    public boolean actualizarEstadoCita(int citaId, String nuevoEstado) throws SQLException {
        String sql = "{CALL sp_actualizar_estado_cita(?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, citaId);
            cs.setString(2, nuevoEstado);
            cs.execute();

            return true;
        }
    }

    // Método para buscar cita por placa
    public static List<CitaModelo> buscarCitaPorPlaca(String placa) throws SQLException {
        List<CitaModelo> lista = new ArrayList<>();
        String sql = "{CALL sp_buscar_cita_por_placa(?)}";

        try (Connection con = ConexionBDD.getConnection();
            CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, placa);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    CitaModelo cita = new CitaModelo();
                    cita.setCitaId(rs.getInt("Cita_Id"));
                    cita.setFechaCita(rs.getDate("Fecha_Cita").toLocalDate());
                    cita.setHoraCita(rs.getTime("Hora_Cita").toLocalTime());
                    cita.setNombreEstado(rs.getString("Nombre_Estado"));
                    cita.setPlaca(rs.getString("Placa"));
                    cita.setNombreCliente(rs.getString("Nombre_Cliente"));
                    lista.add(cita);
                }
            }
        }
        return lista;
    }

    // Método para obtener todas las citas (Programadas y en Proceso)
    public ArrayList<CitaModelo> obtenerCitasProgramadas() throws SQLException {
        ArrayList<CitaModelo> listaCitas = new ArrayList<>();
        String sql = "{CALL sp_citas_programadas()}";

        try (Connection con = ConexionBDD.getConnection();
            CallableStatement cs = con.prepareCall(sql);
            ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                CitaModelo cita = new CitaModelo();
                cita.setCitaId(rs.getInt("N_Cita"));
                cita.setFechaCita(rs.getDate("Fecha").toLocalDate());
                cita.setHoraCita(rs.getTime("Hora").toLocalTime());
                cita.setPlaca(rs.getString("Placa"));
                cita.setNombreCliente(rs.getString("Cliente"));
                cita.setNombreServicio(rs.getString("Servicio"));
                cita.setNombreMecanico(rs.getString("Mecanico"));
                cita.setUsuario(rs.getString("Usuario"));
                cita.setNombreEstado(rs.getString("Estado"));

                listaCitas.add(cita);
            }
        }
        return listaCitas;
    }

    // Método para obtener el historial de todas las citas (Finalizadas y Canceladas)
    public ArrayList<CitaModelo> obtenerHistorialCitas() throws SQLException {
        ArrayList<CitaModelo> listaHistorialCitas = new ArrayList<>();
        String sql = "{CALL sp_consultar_historial_citas()}";

        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                CitaModelo cita = new CitaModelo();
                cita.setCitaId(rs.getInt("N_Cita"));
                cita.setFechaCita(rs.getDate("Fecha").toLocalDate());
                cita.setHoraCita(rs.getTime("Hora").toLocalTime());
                cita.setPlaca(rs.getString("Placa"));
                cita.setNombreCliente(rs.getString("Cliente"));
                cita.setNombreServicio(rs.getString("Servicio"));
                cita.setNombreMecanico(rs.getString("Mecanico"));
                cita.setUsuario(rs.getString("Usuario"));
                cita.setNombreEstado(rs.getString("Estado"));

                listaHistorialCitas.add(cita);
            }
        }
        return listaHistorialCitas;
    }

}