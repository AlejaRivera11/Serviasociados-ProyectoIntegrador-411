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
import java.sql.SQLException;
import java.util.List;
import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.ClienteModelo;

public class ClienteAdministradorController {

    @FXML
    private TableColumn<ClienteModelo, String> colCorreo;
    @FXML
    private TableColumn<ClienteModelo, String> colDireccion;
    @FXML
    private TableColumn<ClienteModelo, Integer> colDocumento;
    @FXML
    private TableColumn<ClienteModelo, String> colNombre;
    @FXML
    private TableColumn<ClienteModelo, String> colTelefono;
    @FXML
    private TableView<ClienteModelo> tblClientes;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void insertarCliente(ActionEvent event) {
        // 1. Validación inicial de campos vacíos
        if (txtDocumento.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                txtDireccion.getText().isEmpty()) {

            warningAlert.setTitle("Información Incompleta");
            warningAlert.setContentText("Complete todos los campos para registrar el cliente.");
            warningAlert.show();
            return;
        }

        try {
            // Asignación y conversión de datos
            AppServices.getClienteModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));
            AppServices.getClienteModelo().setNombreCliente(txtNombre.getText());
            AppServices.getClienteModelo().setTelefonoCliente(txtTelefono.getText());
            AppServices.getClienteModelo().setCorreoCliente(txtCorreo.getText());
            AppServices.getClienteModelo().setDireccionCliente(txtDireccion.getText());

            // Ejecución de la operación (puede lanzar SQLException)
            if (AppServices.getClienteModelo().registrarCliente()) {
                // 4. Éxito: Mostrar alerta y actualizar UI
                confirmationAlert.setTitle("Registro Exitoso");
                confirmationAlert.setContentText("Cliente registrado con éxito.");
                confirmationAlert.show();
                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            // Manejo de error de formato (si el documento no es número)
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo Documento debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            // Manejo de error de base de datos
            errorAlert.setTitle("Error de Registro");
            if (e.getErrorCode() == 1062) {
                errorAlert.setContentText("El ID de Cliente ya está registrado.");
            } else {
                errorAlert.setContentText("Error al registrar el cliente: " + e.getMessage());
            }
            errorAlert.show();
        }
    }

    @FXML
    void actualizarCliente(ActionEvent event) {
        // Validación de campos vacíos
        if (txtDocumento.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                txtDireccion.getText().isEmpty()) {

            warningAlert.setTitle("Información Requerida");
            warningAlert.setContentText("Complete todos los campos para actualizar el cliente.");
            warningAlert.show();
            return;
        }

        try {
            // Asignación y conversión de datos
            AppServices.getClienteModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));
            AppServices.getClienteModelo().setNombreCliente(txtNombre.getText());
            AppServices.getClienteModelo().setTelefonoCliente(txtTelefono.getText());
            AppServices.getClienteModelo().setCorreoCliente(txtCorreo.getText());
            AppServices.getClienteModelo().setDireccionCliente(txtDireccion.getText());

            // Ejecución de la operación
            if (AppServices.getClienteModelo().actualizarCliente()) {
                // 4. Éxito
                confirmationAlert.setTitle("Actualización Exitosa");
                confirmationAlert.setContentText("Cliente actualizado con éxito.");
                confirmationAlert.show();
                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            // Manejo de error de formato
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo Documento debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            // Manejo de error de base de datos
            errorAlert.setTitle("Error de Actualización");
            errorAlert.setContentText(
                    "Error al actualizar el cliente. Verifique que el numero de documento exista: " + e.getMessage());
            errorAlert.show();
        }
    }

    @FXML
    void buscarCliente(ActionEvent event) {
        // Validación de placa
        if (txtDocumento.getText().isEmpty()) {
            warningAlert.setTitle("Información Requerida");
            warningAlert.setContentText("Por favor, ingrese el Documento de cliente a buscar.");
            warningAlert.show();
            return;
        }

        limpiarCamposSinDocumento(); // su funcion limpia los campos excepto el de documento para evitar confusiones
                                     // al buscar

        try {
            // Conversión y ejecución de búsqueda
            int id = Integer.parseInt(txtDocumento.getText());
            ClienteModelo cliente = AppServices.getClienteModelo().buscarCliente(id);

            // Manejo de resultado
            if (cliente != null) {
                txtNombre.setText(cliente.getNombreCliente());
                txtTelefono.setText(cliente.getTelefonoCliente());
                txtCorreo.setText(cliente.getCorreoCliente());
                txtDireccion.setText(cliente.getDireccionCliente());

            } else {
                warningAlert.setTitle("Cliente No Encontrado");
                warningAlert
                        .setContentText("No se encontró ningún cliente con el documento: " + txtDocumento.getText());
                warningAlert.show();
            }

        } catch (NumberFormatException e) {
            // Manejo de error de formato
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo Documento debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            // Manejo de error de base de datos
            errorAlert.setTitle("Error de Búsqueda");
            errorAlert.setContentText("Ocurrió un error al buscar el cliente: " + e.getMessage());
            errorAlert.show();
        }
    }

    @FXML
    void eliminarCliente(ActionEvent event) {
        // Validación de campo ID
        if (txtDocumento.getText().isEmpty()) {
            warningAlert.setContentText("Por favor, ingrese el numero de documento del cliente para eliminar.");
            warningAlert.show();
            return;
        }

        try {
            // Conversión y ejecución de la operación
            int id = Integer.parseInt(txtDocumento.getText());

            if (AppServices.getClienteModelo().eliminarCliente(id)) {
                confirmationAlert.setTitle("Eliminación Exitosa");
                confirmationAlert.setContentText("Cliente eliminado con éxito.");
                confirmationAlert.show();
                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            // Manejo de error de formato
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("El campo Documento debe contener un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            // Manejo de error de base de datos
            errorAlert.setTitle("Error de Eliminación");
            errorAlert.setContentText("Error al eliminar el cliente: " + e.getMessage());
            errorAlert.show();
        }
    }

    public void initialize() {
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoCliente"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));

        try {
            List<ClienteModelo> lista = AppServices.getClienteModelo().consultarClientes();
            ObservableList<ClienteModelo> observableLista = FXCollections.observableArrayList(lista);
            tblClientes.setItems(observableLista);
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Carga Inicial");
            errorAlert
                    .setContentText("No se pudo cargar la lista de clientes desde la base de datos: " + e.getMessage());
            errorAlert.show();
        }

    }

    public void actualizarTabla() {
        tblClientes.getItems().clear();
        try {
            tblClientes.getItems().addAll(AppServices.getClienteModelo().consultarClientes());
        } catch (SQLException e) {
            errorAlert.setTitle("Error al Actualizar Tabla");
            errorAlert.setContentText("No se pudo recargar la lista de clientes: " + e.getMessage());
            errorAlert.show();
        }
    }

    public void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
    }

    public void limpiarCamposSinDocumento() {
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
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
}