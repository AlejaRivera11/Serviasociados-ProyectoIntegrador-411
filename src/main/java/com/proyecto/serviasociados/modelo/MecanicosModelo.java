package com.proyecto.serviasociados.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyecto.serviasociados.services.ConexionBDD;

public class MecanicosModelo {

    private int idMecanico;
    private String nombreMecanico;
    private String telefonoMecanico;
    private String direccionMecanico;

    //Constructores
    public MecanicosModelo() {
    }

    public MecanicosModelo(int idMecanico, String nombreMecanico, String telefonoMecanico, String direccionMecanico) {
        this.idMecanico = idMecanico;
        this.nombreMecanico = nombreMecanico;
        this.telefonoMecanico = telefonoMecanico;
        this.direccionMecanico = direccionMecanico;
    }

    //Getters y Setters
    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
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

    //Otros métodos

    // Método para registrar un nuevo mecánico
    public boolean registrarMecanico() {
        String sql = "{CALL sp_insertar_mecanico(?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idMecanico);
            cs.setString(2, nombreMecanico);
            cs.setString(3, telefonoMecanico);
            cs.setString(4, direccionMecanico);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar mecánico: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar mecánico
    public boolean actualizarMecanico() {
        String sql = "{CALL sp_actualizar_mecanico(?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idMecanico);
            cs.setString(2, nombreMecanico);
            cs.setString(3, telefonoMecanico);
            cs.setString(4, direccionMecanico);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar mecánico: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar mecánico    
    public boolean eliminarMecanico() {
        String sql = "{CALL sp_eliminar_mecanico(?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idMecanico);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar mecánico: " + e.getMessage());
            return false;
        }
    }

    // Método para consultar mecánicos
    public static List<MecanicosModelo> consultarMecanicos() {
        List<MecanicosModelo> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_mecanicos()}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                MecanicosModelo m = new MecanicosModelo(
                    rs.getInt("Mecanico_Id"),
                    rs.getString("Nombre_Mecanico"),
                    rs.getString("Especialidad_Mecanico"),
                    rs.getString("Telefono_Mecanico")
                );
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar mecánicos: " + e.getMessage());
        }
        return lista;
    }
    

}
