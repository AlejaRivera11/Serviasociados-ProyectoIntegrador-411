package com.proyecto.serviasociados.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyecto.serviasociados.services.ConexionBDD;


public class ServicioModelo {
    
    private int servicioId;
    private String nomServicio;
    private String tiempo;

    public ServicioModelo() {
    }

    public ServicioModelo(int servicioId, String nomServicio, String tiempo) {
        this.servicioId = servicioId;
        this.nomServicio = nomServicio;
        this.tiempo = tiempo;
    }

    public int getServicioId() {
        return servicioId;
    }

    public String getNomServicio() {
        return nomServicio;
    }
   
    public String getTiempo() {
        return tiempo;
    }

    //Funcion: pPra mostrar el nombre del servicio en el JComboBox.
    @Override
    public String toString() {
        return nomServicio;
    }

    public static List<ServicioModelo> obtenerServicios() throws SQLException {
        List<ServicioModelo> servicios = new ArrayList<>();
        String sql = "{Call sp_listar_servicios()}";

        try (Connection con = ConexionBDD.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int servicioId = rs.getInt("servicio_id");
                String nomServicio = rs.getString("nom_servicio");
                String tiempo = rs.getString("tiempo");
                servicios.add(new ServicioModelo(servicioId, nomServicio, tiempo));
            }
        } 
        return servicios;
    }
}
