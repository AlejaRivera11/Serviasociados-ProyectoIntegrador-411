package com.proyecto.serviasociados.controlador;


import javafx.collections.FXCollections;
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
import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.ClientesModelo;


public class ClientesController {

    @FXML private TableColumn<?, ?> colCorreo;
    @FXML private TableColumn<?, ?> colDireccion;
    @FXML private TableColumn<?, ?> colDocumento;
    @FXML private TableColumn<?, ?> colNombre;
    @FXML private TableColumn<?, ?> colTelefono;
    @FXML private TableView<ClientesModelo> tblClientes;
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
             
            AppServices.getClientesModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));
            AppServices.getClientesModelo().setNombreCliente(txtNombre.getText());
            AppServices.getClientesModelo().setTelefonoCliente(txtTelefono.getText());
            AppServices.getClientesModelo().setCorreoCliente(txtCorreo.getText());
            AppServices.getClientesModelo().setDireccionCliente(txtDireccion.getText());

            AppServices.getClientesModelo().registrarCliente(
            );
            
            confirmationAlert.setTitle("Registro exitoso");
            confirmationAlert.setContentText("Cliente registrado con éxito.");
            confirmationAlert.show();
            limpiarCampos();
            // actualizarTabla();

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
            AppServices.getClientesModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));
            AppServices.getClientesModelo().setNombreCliente(txtNombre.getText());
            AppServices.getClientesModelo().setTelefonoCliente(txtTelefono.getText());
            AppServices.getClientesModelo().setCorreoCliente(txtCorreo.getText());
            AppServices.getClientesModelo().setDireccionCliente(txtDireccion.getText());

            // Llamar al método que ejecuta el procedimiento almacenado
            AppServices.getClientesModelo().actualizarCliente();

            limpiarCampos();
            System.out.println("Cliente actualizado correctamente.");

        } else {
            warningAlert.setContentText("Por favor, completa todos los campos antes de actualizar.");
            warningAlert.show();
        }
    }

    
    @FXML void buscarCliente(ActionEvent event) {
        
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



        tblClientes.getItems().addAll(AppServices.getClientesModelo().consultarClientes());
        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                txtDocumento.setText(String.valueOf(((ClientesModelo) newValue).getIdCliente()));
                txtNombre.setText(((ClientesModelo) newValue).getNombreCliente());
                txtTelefono.setText(((ClientesModelo) newValue).getTelefonoCliente());
                txtCorreo.setText(((ClientesModelo) newValue).getCorreoCliente());
                txtDireccion.setText(((ClientesModelo) newValue).getDireccionCliente());
            }
        });
    }

    public void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
    }





}
