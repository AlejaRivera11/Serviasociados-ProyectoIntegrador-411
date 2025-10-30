package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

import com.proyecto.serviasociados.services.ConexionBDD;


public class ClienteModelo {
    private int idCliente;
    private String nombreCliente;
    private String telefonoCliente;
    private String correoCliente;
    private String direccionCliente;



    // Constructores
    public ClienteModelo() {
    }

    public ClienteModelo(int idCliente, String nombreCliente, String telefonoCliente, String correoCliente, String direccionCliente) {
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
            System.err.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

     //Método para actualizar Cliente
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
    public boolean eliminarCliente(int idCliente) {
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


    //Metodod para buscar un cliente por su id
    public ClienteModelo buscarCliente(int idCliente) {
        ClienteModelo cliente = null;
        String sql = "{CALL sp_buscar_cliente(?)}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idCliente);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    cliente = new ClienteModelo();
                    cliente.setIdCliente(rs.getInt("Cliente_Id"));
                    cliente.setNombreCliente(rs.getString("Nombre_Cliente"));
                    cliente.setTelefonoCliente(rs.getString("Telefono_Cliente"));
                    cliente.setCorreoCliente(rs.getString("Correo_Cliente"));
                    cliente.setDireccionCliente(rs.getString("Direccion_Cliente"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    //Metodo para consultar todos los clientes
    public List<ClienteModelo> consultarClientes() {
        List<ClienteModelo> clientes = new ArrayList<>();
        String sql = "{CALL sp_consultar_clientes()}";
        try (Connection con = ConexionBDD.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ClienteModelo cliente = new ClienteModelo();
                cliente.setIdCliente(rs.getInt("Cliente_Id"));
                cliente.setNombreCliente(rs.getString("Nombre_Cliente"));
                cliente.setTelefonoCliente(rs.getString("Telefono_Cliente"));
                cliente.setCorreoCliente(rs.getString("Correo_Cliente"));
                cliente.setDireccionCliente(rs.getString("Direccion_Cliente"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar clientes: " + e.getMessage());
        }
        return clientes;
    }




}