package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

import com.proyecto.serviasociados.services.ConexionBDD;

import javafx.scene.control.Alert;
public class ClientesModelo {
    private int idCliente;
    private String nombreCliente;
    private String telefonoCliente;
    private String correoCliente;
    private String direccionCliente;

    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    // Constructores
    public ClientesModelo() {
    }

    public ClientesModelo(int idCliente, String nombreCliente, String telefonoCliente, String correoCliente, String direccionCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
        this.direccionCliente = direccionCliente;
    }

    // Getters y Setters
    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }


    // Otros métodos usando procedimientos almacenados

    // Método para registrar un nuevo cliente
    public boolean registrarCliente() { 
        String sql = "{CALL sp_insertar_cliente(?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idCliente);
            cs.setString(2, nombreCliente);
            cs.setString(3, telefonoCliente);
            cs.setString(4, correoCliente);
            cs.setString(5, direccionCliente);
            cs.execute();
            return true;
        } catch (SQLException e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Error al registrar el cliente: " + e.getMessage());
            errorAlert.show();
            return false;
        }
    }

     // Método para actualizar cliente
     public boolean actualizarCliente() {
        String sql = "{CALL sp_actualizar_cliente(?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idCliente);
            cs.setString(2, nombreCliente);
            cs.setString(3, telefonoCliente);
            cs.setString(4, correoCliente);
            cs.setString(5, direccionCliente);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar cliente
    public boolean eliminarCliente() {
        String sql = "{CALL sp_eliminar_cliente(?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idCliente);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    //Método para consultar clientes
    public List<ClientesModelo> consultarClientes() {
        List<ClientesModelo> clientes = new ArrayList<>();
        String sql = "{CALL sp_consultar_cliente()}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ClientesModelo cliente = new ClientesModelo();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombreCliente(rs.getString("nombreCliente"));
                cliente.setTelefonoCliente(rs.getString("telefonoCliente"));
                cliente.setCorreoCliente(rs.getString("correoCliente"));
                cliente.setDireccionCliente(rs.getString("direccionCliente"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar clientes: " + e.getMessage());
        }
        return clientes;
    }


}