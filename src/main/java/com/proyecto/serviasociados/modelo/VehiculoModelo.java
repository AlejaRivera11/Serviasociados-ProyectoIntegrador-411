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

    public VehiculoModelo() {
    }

    public VehiculoModelo(String placa, String marca, int modelo, int kilometraje, String color, int idCliente) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.color = color;
        this.idCliente = idCliente;
    }

    // getters y setters
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Método para registrar un nuevo vehículo
    public boolean registrarVehiculo() throws SQLException {
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
        }
    }

    // Método para actualizar vehículo
    public boolean actualizarVehiculo() throws SQLException {
        String sql = "{CALL sp_actualizar_vehiculo(?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, placa);
            cs.setString(2, color);
            cs.setInt(3, kilometraje);
            cs.execute();
            return true;
        }
    }

    // Método para eliminar vehículo
    public boolean eliminarVehiculo() throws SQLException {
        String sql = "{CALL sp_eliminar_vehiculo(?)}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, placa);
            cs.execute();
            return true;
        }
    }

    // Metodo para buscar vehículo por placa
    public VehiculoModelo buscarVehiculoPorPlaca(String placa) throws SQLException {
        VehiculoModelo vehiculo = null;
        String sql = "{CALL sp_buscar_vehiculo(?)}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, placa);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    vehiculo = new VehiculoModelo(
                            rs.getString("Placa"),
                            rs.getString("Marca"),
                            rs.getInt("Modelo"),
                            rs.getInt("Kilometraje"),
                            rs.getString("Color"),
                            rs.getInt("Cliente_Id"));
                }
            }
        }
        return vehiculo;

    }

    // Método para consultar vehículos
    public List<VehiculoModelo> consultarVehiculos() throws SQLException {
        List<VehiculoModelo> lista = new ArrayList<>();
        String sql = "{CALL sp_consultar_vehiculos()}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                VehiculoModelo v = new VehiculoModelo(
                        rs.getString("Placa"),
                        rs.getString("Marca"),
                        rs.getInt("Modelo"),
                        rs.getInt("Kilometraje"),
                        rs.getString("Color"),
                        rs.getInt("Cliente_Id"));
                lista.add(v);
            }
        }
        return lista;
    }

}
