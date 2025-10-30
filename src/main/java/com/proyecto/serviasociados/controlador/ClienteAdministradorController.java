package com.proyecto.serviasociados.controlador;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.ClienteModelo;


public class ClienteAdministradorController {

    @FXML private TableColumn<?, ?> colCorreo;
    @FXML private TableColumn<?, ?> colDireccion;
    @FXML private TableColumn<?, ?> colDocumento;
    @FXML private TableColumn<?, ?> colNombre;
    @FXML private TableColumn<?, ?> colTelefono;
    @FXML private TableView<ClienteModelo> tblClientes;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);

    @FXML void insertarCliente(ActionEvent event) {
        if (!txtDocumento.getText().isEmpty() &&
                !txtNombre.getText().isEmpty() &&
                !txtTelefono.getText().isEmpty() &&
                !txtCorreo.getText().isEmpty() &&
                !txtDireccion.getText().isEmpty()) {
             
            AppServices.getClienteModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));
            AppServices.getClienteModelo().setNombreCliente(txtNombre.getText());
            AppServices.getClienteModelo().setTelefonoCliente(txtTelefono.getText());
            AppServices.getClienteModelo().setCorreoCliente(txtCorreo.getText());
            AppServices.getClienteModelo().setDireccionCliente(txtDireccion.getText());

            AppServices.getClienteModelo().registrarCliente(
            );
            
            confirmationAlert.setTitle("Registro exitoso");
            confirmationAlert.setContentText("Cliente registrado con éxito.");
            confirmationAlert.show();
            limpiarCampos();
            actualizarTabla();

        } else if ( txtNombre.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                txtCorreo.getText().isEmpty() ||
                txtDireccion.getText().isEmpty()) {

            warningAlert.setContentText("Complete la información necesaria para registrar el cliente.");
            warningAlert.show();

        } else {
            warningAlert.setContentText("Limpie los campos para registrar el cliente.");
            warningAlert.show();

        }
    }

    @FXML void actualizarCliente(ActionEvent event) {
        if (!txtDocumento.getText().isEmpty() &&
                !txtNombre.getText().isEmpty() &&
                !txtTelefono.getText().isEmpty() &&
                !txtCorreo.getText().isEmpty() &&
                !txtDireccion.getText().isEmpty()) {

            // Pasar valores al modelo
            AppServices.getClienteModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));
            AppServices.getClienteModelo().setNombreCliente(txtNombre.getText());
            AppServices.getClienteModelo().setTelefonoCliente(txtTelefono.getText());
            AppServices.getClienteModelo().setCorreoCliente(txtCorreo.getText());
            AppServices.getClienteModelo().setDireccionCliente(txtDireccion.getText());

            // Llamar al método que ejecuta el procedimiento almacenado
            AppServices.getClienteModelo().actualizarCliente();

            limpiarCampos();
            actualizarTabla();

            confirmationAlert.setTitle("Actualización exitosa");
            confirmationAlert.setContentText("Cliente actualizado con éxito.");
            confirmationAlert.show();
            

        } else {
            warningAlert.setContentText("Por favor, completa todos los campos antes de actualizar.");
            warningAlert.show();
        }
    }

    
    @FXML void buscarCliente(ActionEvent event) {
        if (!txtDocumento.getText().isEmpty()) {
            ClienteModelo cliente = AppServices.getClienteModelo().buscarCliente(Integer.parseInt(txtDocumento.getText()));
            if (cliente != null) {
                txtNombre.setText(cliente.getNombreCliente());
                txtTelefono.setText(cliente.getTelefonoCliente());
                txtCorreo.setText(cliente.getCorreoCliente());
                txtDireccion.setText(cliente.getDireccionCliente());
            } else {
                warningAlert.setContentText("Cliente no encontrado.");
                warningAlert.show();
            }
        } else {
            warningAlert.setContentText("Por favor, ingrese un ID de cliente para buscar.");
            warningAlert.show();
        }
        
    }

    @FXML void eliminarCliente(ActionEvent event) {
        if (!txtDocumento.getText().isEmpty()) {
            AppServices.getClienteModelo().eliminarCliente(Integer.parseInt(txtDocumento.getText()));
            confirmationAlert.setTitle("Eliminación exitosa");
            confirmationAlert.setContentText("Cliente eliminado con éxito.");
            confirmationAlert.show();
            limpiarCampos();
            actualizarTabla();
        } else {
            warningAlert.setContentText("Por favor, ingrese un ID de cliente para eliminar.");
            warningAlert.show();
        }

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

    public void initialize() {
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoCliente"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));

        List<ClienteModelo> lista = AppServices.getClienteModelo().consultarClientes();
        ObservableList<ClienteModelo> observableLista = FXCollections.observableArrayList(lista);
        tblClientes.setItems(observableLista);

        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
             txtDocumento.setText(String.valueOf(newSelection.getIdCliente()));
             txtNombre.setText(newSelection.getNombreCliente());
             txtTelefono.setText(newSelection.getTelefonoCliente());
             txtCorreo.setText(newSelection.getCorreoCliente());
             txtDireccion.setText(newSelection.getDireccionCliente());
          }
        });
    }

    public void actualizarTabla() {
        tblClientes.getItems().clear();
        tblClientes.getItems().addAll(AppServices.getClienteModelo().consultarClientes());  
    }



    public void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
    }





}
