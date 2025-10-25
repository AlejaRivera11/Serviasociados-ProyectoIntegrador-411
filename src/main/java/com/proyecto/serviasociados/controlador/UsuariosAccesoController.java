package com.proyecto.serviasociados.controlador;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.proyecto.serviasociados.config.AppServices;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;


public class UsuariosAccesoController {

    @FXML private TableView<UsuarioAccesoModelo> tblUsuarios;
    @FXML private TableColumn<UsuarioAccesoModelo, Integer> colID;
    @FXML private TableColumn<UsuarioAccesoModelo, String> colUsuario;
    @FXML private TableColumn<UsuarioAccesoModelo, String> colContrasena;
    @FXML private TableColumn<UsuarioAccesoModelo, String> colRol;
    @FXML private TableColumn<UsuarioAccesoModelo, String> colEstado;
    @FXML private TextField txtContrasena;
    @FXML private TextField txtEstado;
    @FXML private TextField txtId;
    @FXML private TextField txtRol;
    @FXML private TextField txtUsuario;

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    @FXML void crearUsuario(ActionEvent event) {
        if (!txtId.getText().isEmpty() &&
                !txtUsuario.getText().isEmpty() &&
                !txtContrasena.getText().isEmpty() &&
                !txtRol.getText().isEmpty() &&
                !txtEstado.getText().isEmpty()) {

            AppServices.getUsuarioAccesoModelo().crearUsuario(
                    Integer.parseInt(txtId.getText()),
                    txtUsuario.getText(),
                    txtContrasena.getText(),
                    txtRol.getText(),
                    txtEstado.getText()
            );
            limpiarCampos();
            actualizarTabla();

        } else if (txtUsuario.getText().isEmpty() ||
                txtContrasena.getText().isEmpty() ||
                txtRol.getText().isEmpty() ||
                txtEstado.getText().isEmpty()) {

            warningAlert.setContentText("Complete la información necesaria para registrar el usuario.");
            warningAlert.show();

        } else {
            warningAlert.setContentText("Limpie los campos para registrar el usuario.");
            warningAlert.show();
        }
        
    }

    public void actualizarUsuario(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            AppServices.getUsuarioAccesoModelo().actualizarUsuario(
                    Integer.parseInt(txtId.getText()),
                    txtContrasena.getText(),
                    txtRol.getText(),
                    txtEstado.getText()
            );
            actualizarTabla();
        } else {
            warningAlert.setContentText("Seleccione o busque un usuario para actualizar.");
            warningAlert.show();
        }
    }


    private void actualizarTabla() {
        tblUsuarios.getItems().clear();
        tblUsuarios.getItems().addAll(AppServices.getUsuarioAccesoModelo().obtenerUsuarios());   
    }

    @FXML void buscarUsuario(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            UsuarioAccesoModelo usuario = AppServices.getUsuarioAccesoModelo().buscarUsuarioPorId(Integer.parseInt(txtId.getText()));
            if (usuario != null) {
                txtId.setText(String.valueOf(usuario.getIdUsuario()));
                txtUsuario.setText(usuario.getUsuario());
                txtContrasena.setText(usuario.getContrasena());
                txtRol.setText(usuario.getRol());
                txtEstado.setText(usuario.getEstado());
            } else {
                warningAlert.setContentText("El usuario no existe.");
                warningAlert.show();
            }
        } else {
            warningAlert.setContentText("Ingrese un Id de usuario para buscar.");
            warningAlert.show();
        }

    }

    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        
        tblUsuarios.getItems().addAll(AppServices.getUsuarioAccesoModelo().obtenerUsuarios());

        tblUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                txtId.setText(String.valueOf(newValue.getIdUsuario()));
                txtUsuario.setText(newValue.getUsuario());
                txtContrasena.setText(newValue.getContrasena());
                txtRol.setText(newValue.getRol());
                txtEstado.setText(newValue.getEstado());
            }
        });
    }

    @FXML void volverMenuPrincipal(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuAdministradorVista.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel de administrador");
        stage.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void limpiarCampos() {
        txtId.clear();
        txtUsuario.clear();
        txtContrasena.clear();
        txtRol.clear();
        txtEstado.clear();
    }




}
