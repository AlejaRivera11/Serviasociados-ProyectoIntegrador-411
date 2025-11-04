package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.CallableStatement;
import com.proyecto.serviasociados.services.ConexionBDD;

public class EstadoModelo {

    private int idEstado;
    private String nombreEstado;

   
    public EstadoModelo() {
    }

    public EstadoModelo(int idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    
    public int getIdEstado() {
        return idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    // Sirve para mostrar el nombre del estado en el JComboBox.
    @Override
    public String toString() {
        return nombreEstado;
    }

    public static List<EstadoModelo> obtenerEstados() throws SQLException {
        List<EstadoModelo> estados = new ArrayList<>();
        String sql = "{Call sp_listar_estados()}";

        try (Connection con = ConexionBDD.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idEstado = rs.getInt("id_estado");
                String nombreEstado = rs.getString("nombre_estado");
                estados.add(new EstadoModelo(idEstado, nombreEstado));
            }
        } 
        return estados;
    }

    
}
