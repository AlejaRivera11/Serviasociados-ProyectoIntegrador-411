package com.proyecto.serviasociados.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.CallableStatement;
import com.proyecto.serviasociados.services.ConexionBDD;

public class UsuarioAccesoModelo {

    private int idUsuario;
    private String usuario;
    private String contrasena;
    private String rol;
    private String estado;

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


    // Metodo para Crear un usuario con procedimiento almacenado
    public boolean crearUsuario() throws SQLException {
        String sql = "{CALL sp_insertar_usuario(?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            cs.setString(2, usuario);
            cs.setString(3, contrasena);
            cs.setString(4, rol);
            cs.setString(5, estado);
            cs.execute();
            return true;
        }

    }

    // Metodo para buscar usuario por id con procedimiento almacenado
    public UsuarioAccesoModelo buscarUsuario(int idUsuario) throws SQLException {
        UsuarioAccesoModelo usuario = null;
        String sql = "{CALL sp_buscar_usuario(?)}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    usuario = new UsuarioAccesoModelo();
                    usuario.setIdUsuario(rs.getInt("Usuario_Id"));
                    usuario.setUsuario(rs.getString("Usuario"));
                    usuario.setContrasena(rs.getString("Contrasena"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setEstado(rs.getString("Estado"));
                }
            }
        }
        return usuario;
    }

    // Metodo para actualizar usuario con procedimiento almacenado
    public boolean actualizarUsuario() throws SQLException {
        String sql = "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?)}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            cs.setString(2, usuario);
            cs.setString(3, contrasena);
            cs.setString(4, rol);
            cs.setString(5, estado);
            cs.execute();
            return true;
        }
    }

    // Metodo para obtener todos los usuarios con procedimiento almacenado
    public ArrayList<UsuarioAccesoModelo> obtenerUsuarios() throws SQLException {
        ArrayList<UsuarioAccesoModelo> usuarios = new ArrayList<>();
        String sql = "{CALL sp_consultar_usuarios()}";
        try (Connection con = ConexionBDD.getConnection();
                CallableStatement cs = con.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                UsuarioAccesoModelo usuario = new UsuarioAccesoModelo();
                usuario.setIdUsuario(rs.getInt("Usuario_Id"));
                usuario.setUsuario(rs.getString("Usuario"));
                usuario.setContrasena(rs.getString("Contrasena"));
                usuario.setRol(rs.getString("Rol"));
                usuario.setEstado(rs.getString("Estado"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    // Metodo para validar usuario con procedimiento almacenado
    public String validarUsuario(String usuario, String contrasena) throws SQLException {
        String sql = "{CALL sp_validar_usuario(?, ?)}";
        try (Connection connection = ConexionBDD.getConnection();
                CallableStatement cs = connection.prepareCall(sql)) {

            cs.setString(1, usuario);
            cs.setString(2, contrasena);

            try (ResultSet resultSet = cs.executeQuery()) {
                if (resultSet.next()) {
                    String rol = resultSet.getString("Rol");
                    String estado = resultSet.getString("Estado");

                    if (estado.equalsIgnoreCase("Activo")) {
                        return rol;
                    } else {
                        return "INACTIVO";
                    }
                } else {
                    return "NO_EXISTE";
                }
            }
        }
    }

}