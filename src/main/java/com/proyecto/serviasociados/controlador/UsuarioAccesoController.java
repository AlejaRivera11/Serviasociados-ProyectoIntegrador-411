package com.proyecto.serviasociados.controlador;

import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.proyecto.serviasociados.config.AppServices;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioAccesoController {

    @FXML
    private TableView<UsuarioAccesoModelo> tblUsuarios;
    @FXML
    private TableColumn<UsuarioAccesoModelo, Integer> colID;
    @FXML
    private TableColumn<UsuarioAccesoModelo, String> colUsuario;
    @FXML
    private TableColumn<UsuarioAccesoModelo, String> colContrasena;
    @FXML
    private TableColumn<UsuarioAccesoModelo, String> colRol;
    @FXML
    private TableColumn<UsuarioAccesoModelo, String> colEstado;
    @FXML
    private TextField txtContrasena;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private ComboBox<String> cmbRol;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtUsuario;

    final private Alert warningAlert = new Alert(Alert.AlertType.WARNING);
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void crearUsuario(ActionEvent event) {

        if (txtId.getText().isEmpty() || txtUsuario.getText().isEmpty() ||
                txtContrasena.getText().isEmpty() || cmbRol.getValue() == null ||
                cmbEstado.getValue() == null) {

            warningAlert.setTitle("Información Incompleta");
            warningAlert.setContentText("Complete todos los campos, incluyendo la selección de Rol y Estado.");
            warningAlert.show();
            return;
        }

        try {

            AppServices.getUsuarioAccesoModelo().setIdUsuario(Integer.parseInt(txtId.getText()));
            AppServices.getUsuarioAccesoModelo().setUsuario(txtUsuario.getText());
            AppServices.getUsuarioAccesoModelo().setContrasena(txtContrasena.getText());
            AppServices.getUsuarioAccesoModelo().setRol(cmbRol.getValue());
            AppServices.getUsuarioAccesoModelo().setEstado(cmbEstado.getValue());

            if (AppServices.getUsuarioAccesoModelo().crearUsuario()) {
                confirmationAlert.setTitle("Registro Exitoso");
                confirmationAlert.setContentText("Usuario Creado con éxito.");
                confirmationAlert.show();
                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo ID debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Registro");
            if (e.getErrorCode() == 1062) {
                errorAlert.setContentText("El ID o Nombre de Usuario ya está registrado.");
            } else {
                errorAlert.setContentText("Error al crear el usuario: " + e.getMessage());
            }
            errorAlert.show();
        }
    }

    @FXML
    public void actualizarUsuario(ActionEvent event) {

        if (txtId.getText().isEmpty()) {
            warningAlert.setContentText("Ingrese un ID de usuario para actualizar.");
            warningAlert.show();
            return;
        }

        // Validación de campos obligatorios para actualizar (incluye ComboBox)
        if (txtUsuario.getText().isEmpty() || txtContrasena.getText().isEmpty() ||
                cmbRol.getValue() == null || cmbEstado.getValue() == null) {

            warningAlert
                    .setContentText("Complete todos los campos necesarios para actualizar, incluyendo Rol y Estado.");
            warningAlert.show();
            return;
        }

        try {
            AppServices.getUsuarioAccesoModelo().setIdUsuario(Integer.parseInt(txtId.getText()));
            AppServices.getUsuarioAccesoModelo().setUsuario(txtUsuario.getText());
            AppServices.getUsuarioAccesoModelo().setContrasena(txtContrasena.getText());
            AppServices.getUsuarioAccesoModelo().setRol(cmbRol.getValue());
            AppServices.getUsuarioAccesoModelo().setEstado(cmbEstado.getValue());

            if (AppServices.getUsuarioAccesoModelo().actualizarUsuario()) {
                confirmationAlert.setTitle("Actualización Exitosa");
                confirmationAlert.setContentText("Usuario actualizado con éxito.");
                confirmationAlert.show();
                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo ID debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Actualización");
            errorAlert.setContentText("Error al actualizar el usuario. Verifique el ID: " + e.getMessage());
            errorAlert.show();
        }
    }

    @FXML
    void buscarUsuario(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            warningAlert.setContentText("Ingrese el ID del usuario a buscr.");
            warningAlert.show();
            return;
        }

        try {

            UsuarioAccesoModelo usuario = AppServices.getUsuarioAccesoModelo()
                    .buscarUsuario(Integer.parseInt(txtId.getText()));

            if (usuario != null) {
                txtId.setText(String.valueOf(usuario.getIdUsuario()));
                txtUsuario.setText(usuario.getUsuario());
                txtContrasena.setText(usuario.getContrasena());
                cmbRol.setValue(usuario.getRol());
                cmbEstado.setValue(usuario.getEstado());

            } else {
                warningAlert.setContentText("El usuario no existe.");
                warningAlert.show();
            }

        } catch (NumberFormatException e) {
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo ID debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Búsqueda");
            errorAlert.setContentText("Ocurrió un error al buscar el usuario: " + e.getMessage());
            errorAlert.show();
        }
    }

    @FXML
    public void initialize() {

        colID.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Configuración de ComboBox con opciones predefinidas
        ObservableList<String> roles = FXCollections.observableArrayList(
                "ADMINISTRADOR",
                "RECEPCION");
        cmbRol.setItems(roles);

        ObservableList<String> estados = FXCollections.observableArrayList(
                "ACTIVO",
                "INACTIVO");
        cmbEstado.setItems(estados);

        try {
            List<UsuarioAccesoModelo> lista = AppServices.getUsuarioAccesoModelo().obtenerUsuarios();
            ObservableList<UsuarioAccesoModelo> observableLista = FXCollections.observableArrayList(lista);
            tblUsuarios.setItems(observableLista);
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Carga Inicial");
            errorAlert.setContentText("No se pudo cargar la lista de usuarios: " + e.getMessage());
            errorAlert.show();
        }

    }

    private void actualizarTabla() {
        tblUsuarios.getItems().clear();
        try {
            tblUsuarios.getItems().addAll(AppServices.getUsuarioAccesoModelo().obtenerUsuarios());
        } catch (SQLException e) {
            errorAlert.setTitle("Error al Actualizar Tabla");
            errorAlert.setContentText("No se pudo recargar la lista de usuarios: " + e.getMessage());
            errorAlert.show();
        }
    }

    @FXML
    void volverMenuPrincipal(ActionEvent event) throws IOException {
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
        cmbRol.setValue(null);
        cmbEstado.setValue(null);
    }
}