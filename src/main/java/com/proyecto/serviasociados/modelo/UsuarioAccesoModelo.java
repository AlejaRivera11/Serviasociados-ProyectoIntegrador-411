package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.proyecto.serviasociados.services.ConexionBDD;
import javafx.scene.control.Alert;

public class UsuarioAccesoModelo {

    private int idUsuario;
    private String usuario;
    private String contrasena;
    private String rol;
    private String estado;
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);



    public UsuarioAccesoModelo() {
    }

    public UsuarioAccesoModelo(int idUsuario, String usuario, String contrasena, String rol, String estado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    //Metodo para Crear un usuario

    public void crearUsuario(int idUsuario, String usuario, String contrasena, String rol, String estado) {
    String verificarSQL = "SELECT COUNT(*) FROM Usuarios WHERE Usuario = ?";
    String insertarSQL = "INSERT INTO Usuarios (Usuario_Id, Usuario, contrasena, Rol, Estado) VALUES (?, ?, ?, ?, ?)";

    try {
        Connection connection = ConexionBDD.getConnection();
        
        PreparedStatement verificarStmt = connection.prepareStatement(verificarSQL);
        verificarStmt.setString(1, usuario);
        ResultSet resultSet = verificarStmt.executeQuery();
        resultSet.next();
        int existe = resultSet.getInt(1);
        resultSet.close();
        verificarStmt.close();

        if (existe > 0) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("El usuario ya existe. Por favor, eliga otro.");
            errorAlert.show();
            return;
        }

    
        PreparedStatement insertarStmt = connection.prepareStatement(insertarSQL);
        insertarStmt.setInt(1, idUsuario);
        insertarStmt.setString(2, usuario);
        insertarStmt.setString(3, contrasena);
        insertarStmt.setString(4, rol);
        insertarStmt.setString(5, estado);
        insertarStmt.executeUpdate();
        insertarStmt.close();

        confirmationAlert.setTitle("Éxito");
        confirmationAlert.setContentText("Usuario creado correctamente.");
        confirmationAlert.show();

    } catch (Exception e) {
        errorAlert.setTitle("Error");
        errorAlert.setContentText("No fue posible crear el usuario.");
        errorAlert.show();
        e.printStackTrace();
    }
}


    public ArrayList <UsuarioAccesoModelo> obtenerUsuarios() {
        String sql = "SELECT * FROM Usuarios";
        ArrayList<UsuarioAccesoModelo> usuarios = new ArrayList<>();

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                UsuarioAccesoModelo usuario = new UsuarioAccesoModelo();
                usuario.setIdUsuario(resultSet.getInt("Usuario_Id"));
                usuario.setUsuario(resultSet.getString("Usuario"));
                usuario.setContrasena(resultSet.getString("Contrasena"));
                usuario.setRol(resultSet.getString("Rol"));
                usuario.setEstado(resultSet.getString("Estado"));
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible obtener los Usuarios.");
            errorAlert.show();
        }

        return usuarios;
    }

    
    public void actualizarUsuario(int idUsuario, String contrasena, String rol, String estado) {
        String sql = "UPDATE Usuarios SET Contrasena = ?, Rol = ?, Estado = ? WHERE Usuario_Id = ?";

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, contrasena);
            ps.setString(2, rol);
            ps.setString(3, estado);
            ps.setInt(4, idUsuario);

            ps.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Usuario actualizado correctamente.");
            confirmationAlert.show();
        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible actualizar el usuario.");
            errorAlert.show();
        }
    }

    // Buscar usuario por nombre de id
    public UsuarioAccesoModelo buscarUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM Usuarios WHERE Usuario_Id = ?";
        UsuarioAccesoModelo usuario = null;

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                usuario = new UsuarioAccesoModelo();
                usuario.setIdUsuario(resultSet.getInt("Usuario_Id"));
                usuario.setUsuario(resultSet.getString("Usuario"));
                usuario.setContrasena(resultSet.getString("Contrasena"));
                usuario.setRol(resultSet.getString("Rol"));
                usuario.setEstado(resultSet.getString("Estado"));
            }
        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible buscar el usuario.");
            errorAlert.show();
        }

        return usuario;
    }
    

    public String validarUsuario(String usuario, String contrasena) {
        String sql = "SELECT rol, estado FROM Usuarios WHERE usuario = ? AND contrasena = ?";

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet resultSet = ps.executeQuery(); //

            if (resultSet.next()) {
                String rol = resultSet.getString("rol");
                String estado = resultSet.getString("estado");

                if (estado.equalsIgnoreCase("Activo")) {
                    return rol; // Devuelve el rol si está activo
                } else {
                    return "Inactivo";
                }
            } else {
                return "NoExiste";
            }

        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
            return "Error";
        }
    }

    



}