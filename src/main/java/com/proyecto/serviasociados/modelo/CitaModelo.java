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

    //constructor
    public CitaModelo() {
    }
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

    //otros métodos

    // Método para registrar una nueva cita
    public boolean registrarCita() {
        String sql = "{CALL sp_insertar_cita(?, ?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = (CallableStatement) con.prepareCall(sql)) {
                cs.setInt(1, citaId);
                cs.setDate(2, java.sql.Date.valueOf(fechaRegistro));
                cs.setTime(3, java.sql.Time.valueOf(horaRegistro));
                cs.setDate(4, java.sql.Date.valueOf(fechaCita));
                cs.setTime(5, java.sql.Time.valueOf(horaCita));
                cs.setInt(6, estadoId);
                cs.setString(7, placa);
                cs.setInt(8, usuarioId);
                cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar una cita
    public boolean actualizarCita() {
        String sql = "{CALL sp_actualizar_cita(?, ?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = (CallableStatement) con.prepareCall(sql)) {
                cs.setInt(1, citaId);
                cs.setDate(2, java.sql.Date.valueOf(fechaRegistro));
                cs.setTime(3, java.sql.Time.valueOf(horaRegistro));
                cs.setDate(4, java.sql.Date.valueOf(fechaCita));
                cs.setTime(5, java.sql.Time.valueOf(horaCita));
                cs.setInt(6, estadoId);
                cs.setString(7, placa);
                cs.setInt(8, usuarioId);
                cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar una cita
    public boolean eliminarCita() {
        String sql = "{CALL sp_eliminar_cita(?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = (CallableStatement) con.prepareCall(sql)) {
                cs.setInt(1, citaId);
                cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    // Método para consultar una cita
     public static List<CitaModelo> consultarCitas() {
        List<CitaModelo> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_citas()}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = (CallableStatement) con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                CitaModelo c = new CitaModelo(
                    rs.getInt("Cita_Id"),
                    rs.getDate("Fecha_Registro").toLocalDate(),
                    rs.getTime("Hora_Registro").toLocalTime(),
                    rs.getDate("Fecha_Cita").toLocalDate(),
                    rs.getTime("Hora_Cita").toLocalTime(),
                    rs.getInt("Estado_Id"),
                    rs.getString("Placa"),
                    rs.getInt("Usuario_Id")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar citas: " + e.getMessage());
        }
        return lista;
    }


} 