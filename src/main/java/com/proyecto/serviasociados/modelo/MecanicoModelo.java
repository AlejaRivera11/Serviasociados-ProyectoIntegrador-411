package com.proyecto.serviasociados.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.proyecto.serviasociados.services.ConexionBDD;

public class MecanicoModelo {

    private int idMecanico;
    private String nombreMecanico;
    private String telefonoMecanico;
    private String direccionMecanico;

    //Constructores
    public MecanicoModelo() {
    }

    public MecanicoModelo(int idMecanico, String nombreMecanico, String telefonoMecanico, String direccionMecanico) {
        this.idMecanico = idMecanico;
        this.nombreMecanico = nombreMecanico;
        this.telefonoMecanico = telefonoMecanico;
        this.direccionMecanico = direccionMecanico;
    }


    public int getIdMecanico() {
        return idMecanico;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public String getTelefonoMecanico() {
        return telefonoMecanico;
    }

    public String getDireccionMecanico() {
        return direccionMecanico;
    }

    // Sirve para mostrar el nombre del mecánico en el JComboBox.
    @Override
    public String toString() {
        return nombreMecanico;
    }

    public static List<MecanicoModelo> obtenerMecanicos() throws SQLException {
        List<MecanicoModelo> mecanicos = new ArrayList<>();
        String sql = "{Call sp_listar_mecanicos()}";

        try (Connection con = ConexionBDD.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idMecanico = rs.getInt("Mecanico_Id");
                String nombreMecanico = rs.getString("Nom_Mecanico");
                String telefonoMecanico = rs.getString("Telf_Mecanico");
                String direccionMecanico = rs.getString("Direc_Mecanico");
                mecanicos.add(new MecanicoModelo(idMecanico, nombreMecanico, telefonoMecanico, direccionMecanico));
            }
        } 
        return mecanicos;
    }
    

}
