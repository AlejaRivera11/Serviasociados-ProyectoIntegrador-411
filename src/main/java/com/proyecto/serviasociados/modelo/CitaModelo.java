package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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

    //otros métodos

    // Método para registrar una nueva cita
    public boolean agendarCitaCompleta(Date fecha, Time hora, int clienteId, int vehiculoId, int servicioId) {
        String sql = "{CALL sp_agendar_cita_completa(?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = (CallableStatement) con.prepareCall(sql)) {

        // Parámetros de entrada del procedimiento almacenado
             cs.setDate(1, fecha);          // p_Fecha
             cs.setTime(2, hora);           // p_Hora
             cs.setInt(3, clienteId);       // p_Cliente_Id
             cs.setInt(4, vehiculoId);      // p_Vehiculo_Id
             cs.setInt(5, servicioId);      // p_Servicio_Id

        // Ejecutar el procedimiento
             cs.execute();

             return true; // Éxito
        } catch (SQLException e) {
             System.err.println("Error al agendar cita completa: " + e.getMessage());
             return false; // Fallo
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

    // Método para consultar las citas desde el SP sp_listar_citas
    public static List<CitaModelo> consultarCitas() {
        List<CitaModelo> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_citas()}";

        try (Connection con = ConexionBDD.getConnection();
            CallableStatement cs = con.prepareCall(sql);
            ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                CitaModelo c = new CitaModelo();

                    c.setCitaId(rs.getInt("Cita_Id"));
                    c.setFechaCita(rs.getDate("Fecha_Cita").toLocalDate());
                    c.setHoraCita(rs.getTime("Hora_Cita").toLocalTime());
                    c.setPlaca(rs.getString("Placa"));

            // Agrega estos campos en tu modelo si aún no los tienes
                    c.setNombreEstado(rs.getString("Nombre_Estado"));
                    c.setNombreCliente(rs.getString("Nombre_Cliente"));

            lista.add(c);
        }

    } catch (SQLException e) {
        System.err.println("Error al consultar citas: " + e.getMessage());
    }

    return lista;
    }

    // Método para actualizar el estado de una cita y registrar fechas según corresponda
    public boolean actualizarEstadoCita(String nuevoEstado) {
        String sql = "{CALL sp_actualizar_estado_cita(?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
        CallableStatement cs = (CallableStatement) con.prepareCall(sql)) {

        // Parámetros de entrada del procedimiento almacenado
        cs.setInt(1, citaId);        // p_cita_id
        cs.setString(2, nuevoEstado); // p_nuevo_estado

        // Ejecutar el procedimiento
        cs.execute();

        return true;
         } catch (SQLException e) {
         System.err.println("Error al actualizar estado de la cita: " + e.getMessage());
         return false;
         }
    }

    // Método para buscar citas por placa
    public static List<CitaModelo> buscarCitaPorPlaca(String placa) {
    List<CitaModelo> lista = new ArrayList<>();
    String sql = "{CALL sp_buscar_cita_por_placa(?)}";

    try (Connection con = ConexionBDD.getConnection();
         CallableStatement cs = con.prepareCall(sql)) {
        
        cs.setString(1, placa);

        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                CitaModelo c = new CitaModelo();
                c.setCitaId(rs.getInt("Cita_Id"));
                c.setFechaCita(rs.getDate("Fecha_Cita").toLocalDate());
                c.setHoraCita(rs.getTime("Hora_Cita").toLocalTime());
                c.setNombreEstado(rs.getString("Nombre_Estado"));
                c.setPlaca(rs.getString("Placa"));
                c.setNombreCliente(rs.getString("Nombre_Cliente"));
                
                lista.add(c);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al consultar cita por placa: " + e.getMessage());
    }

    return lista;
    }

} 