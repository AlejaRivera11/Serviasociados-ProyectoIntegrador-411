package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyecto.serviasociados.services.ConexionBDD;

import java.sql.CallableStatement;

public class VehiculoModelo {

    private String placa;
    private String marca;
    private int modelo;
    private int kilometraje;
    private String color;
    private int idCliente;  
    //contructores
    public VehiculoModelo() {
    }

    public VehiculoModelo(String placa, String marca, int modelo, int kilometraje, String color) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.color = color;
    }

    //getters y setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //otros métodos 

    // Método para registrar un nuevo vehículo

     public boolean registrarVehiculo() {
        String sql = "{CALL sp_insertar_vehiculo(?, ?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
                cs.setString(1, placa);
                cs.setString(2, marca);
                cs.setInt(3, modelo);
                cs.setString(4, color);
                cs.setInt(5, kilometraje);
                cs.setInt(6, idCliente);
                cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar vehículo: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar vehículo
    public boolean actualizarVehiculo() {
        String sql = "{CALL sp_actualizar_vehiculo(?, ?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
                cs.setString(1, placa);
                cs.setString(2, marca);
                cs.setInt(3, modelo);
                cs.setString(4, color);
                cs.setInt(5, kilometraje);
                cs.setInt(6, idCliente);
                cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar vehículo    
    public boolean eliminarVehiculo() {
        String sql = "{CALL sp_eliminar_vehiculo(?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
                cs.setString(1, placa);
                cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }

    // Método para consultar vehículos  
    public static List<VehiculoModelo> consultarVehiculos() {
        List<VehiculoModelo> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_vehiculos()}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                VehiculoModelo v = new VehiculoModelo(
                    rs.getString("placa"),
                    rs.getString("marca"),
                    rs.getInt("modelo"),
                    rs.getInt("kilometraje"),
                    rs.getString("color")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar vehículos: " + e.getMessage());
        }
        return lista;
    }

}


