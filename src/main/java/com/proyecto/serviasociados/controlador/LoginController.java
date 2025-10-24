package com.proyecto.serviasociados.controlador;

import com.proyecto.serviasociados.config.AppServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private TextField txtContrasena;

    final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            alert.setTitle("Advertencia");
            alert.setContentText("Por favor ingrese el usuario y la contraseña.");
            alert.show();
            return;
        }

        UsuarioAccesoModelo modelo = AppServices.getUsuarioAccesoModelo();
        String resultado = modelo.validarUsuario(usuario, contrasena);

        switch (resultado) {
            case "Administrador":
                abrirVentana("/vista/MenuAdministradorVista.fxml", "Menú Administrador");
                break;
            case "Recepcion":
                abrirVentana("/vista/MenuRecepcionVista.fxml", "Menú Recepción");
                break;
            case "Inactivo":
                errorAlert.setContentText("El usuario no está activo.");
                errorAlert.show();
                break;
            case "NoExiste":
                errorAlert.setContentText("Usuario o contraseña incorrectos.");
                errorAlert.show();
                break;
            default:
                errorAlert.setContentText("Ocurrió un error al validar el usuario.");
                errorAlert.show();
                break;
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

        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Error al abrir la ventana: " + e.getMessage());
            errorAlert.show();
        }
    }
}
