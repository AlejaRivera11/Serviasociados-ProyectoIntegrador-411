package com.proyecto.serviasociados.controlador;

import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContrasena;

    final private static Alert warningAlert = new Alert(Alert.AlertType.WARNING);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            warningAlert.setTitle("Advertencia");
            warningAlert.setContentText("Por favor, ingrese el usuario y la contraseña.");
            warningAlert.show();
            return;
        }

        try {
            UsuarioAccesoModelo modelo = AppServices.getUsuarioAccesoModelo();
            UsuarioAccesoModelo usuarioLogueado = modelo.validarUsuario(usuario, contrasena);

            if (usuarioLogueado == null) {
                errorAlert.setTitle("Acceso Denegado");
                errorAlert.setContentText("Usuario o contraseña incorrectos.");
                errorAlert.show();
            } else if (usuarioLogueado.getEstado().equalsIgnoreCase("INACTIVO")) {
                errorAlert.setTitle("Acceso Denegado");
                errorAlert.setContentText("El usuario no está activo en el sistema. Contacte al administrador.");
                errorAlert.show();
            } else {
                UsuarioAccesoModelo.iniciarSesion(
                        usuarioLogueado.getIdUsuario(),
                        usuarioLogueado.getUsuario(),
                        usuarioLogueado.getRol() 
                );
                String rol = usuarioLogueado.getRol();
                switch (rol) {
                    case "ADMINISTRADOR":
                        abrirVentana("/vista/MenuAdministradorVista.fxml", "Menú Administrador");
                        break;
                    case "RECEPCION":
                        abrirVentana("/vista/MenuRecepcionVista.fxml", "Menú Recepción");
                        break;
                    default:
                        errorAlert.setTitle("Error de Rol");
                        errorAlert.setContentText("Rol de usuario no reconocido.");
                        errorAlert.show();
                        break;
                }
            }
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Conexión");
            errorAlert.setContentText("Fallo al conectar con la base de datos: " + e.getMessage());
            errorAlert.show();
        } catch (Exception e) {
            errorAlert.setTitle("Error Desconocido");
            errorAlert.setContentText("Ocurrió un error inesperado: " + e.getMessage());
            errorAlert.show();
        }
    }

    private void abrirVentana(String rutaFXML, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(tituloVentana);
            stage.show();

            Stage currentStage = (Stage) txtUsuario.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            errorAlert.setTitle("Error de Carga de Vista");
            errorAlert.setContentText("No se pudo cargar la ventana: " + rutaFXML + ". Detalle: " + e.getMessage());
            errorAlert.show();
        } catch (Exception e) {
            errorAlert.setTitle("Error Fatal");
            errorAlert.setContentText("Ocurrió un error inesperado al iniciar la ventana: " + e.getMessage());
            errorAlert.show();
        }
    }
}
