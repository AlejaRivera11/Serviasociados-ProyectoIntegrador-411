package com.proyecto.serviasociados.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.VehiculoModelo;
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

public class VehiculoRecepcionController {
    @FXML
    private TableColumn<VehiculoModelo, String> colColor;
    @FXML
    private TableColumn<VehiculoModelo, String> colPlaca;
    @FXML
    private TableColumn<VehiculoModelo, Integer> colDocumento;
    @FXML
    private TableColumn<VehiculoModelo, Integer> colKilometraje;
    @FXML
    private TableColumn<VehiculoModelo, String> colMarca;
    @FXML
    private TableColumn<VehiculoModelo, Integer> colModelo;
    @FXML
    private TableView<VehiculoModelo> tblVehiculos;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtKilometraje;

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void insertarVehiculo(ActionEvent event) {
        if (txtPlaca.getText().isEmpty() ||
            txtMarca.getText().isEmpty() ||
            txtModelo.getText().isEmpty() ||
            txtColor.getText().isEmpty() ||
            txtKilometraje.getText().isEmpty() ||
            txtDocumento.getText().isEmpty()) {

            warningAlert.setTitle("Información Incompleta");
            warningAlert.setContentText("Por favor complete todos los campos para registrar el vehículo.");
            warningAlert.show();
            return;
        }

        try {
            AppServices.getVehiculoModelo().setPlaca(txtPlaca.getText());
            AppServices.getVehiculoModelo().setMarca(txtMarca.getText());
            AppServices.getVehiculoModelo().setModelo(Integer.parseInt(txtModelo.getText()));
            AppServices.getVehiculoModelo().setColor(txtColor.getText());
            AppServices.getVehiculoModelo().setKilometraje(Integer.parseInt(txtKilometraje.getText()));
            AppServices.getVehiculoModelo().setIdCliente(Integer.parseInt(txtDocumento.getText()));

            if (AppServices.getVehiculoModelo().registrarVehiculo()) {
                confirmationAlert.setTitle("Registro Exitoso");
                confirmationAlert.setContentText("Vehículo registrado con éxito.");
                confirmationAlert.show();

                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("Verifique que los campos Modelo, Kilometraje y Documento contengan números válidos.");
            errorAlert.show();

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Base de Datos");
            if (e.getErrorCode() == 1062) {
                errorAlert.setContentText("El vehiculo ya existe en la base de datos.");
            } else {
                errorAlert.setContentText("Error al registrar el vehículo: " + e.getMessage());
            }
            errorAlert.show();
        }
    }

    @FXML
    void buscarVehiculo(ActionEvent event) {
        if (txtPlaca.getText().isEmpty()) {
            warningAlert.setTitle("Información Requerida");
            warningAlert.setContentText("Por favor, ingrese la placa del vehículo a buscar.");
            warningAlert.show();
            return;
        }

        limpiarCamposBusqueda();

        try {
            VehiculoModelo vehiculo = AppServices.getVehiculoModelo().buscarVehiculoPorPlaca(txtPlaca.getText());

            if (vehiculo != null) {
                txtMarca.setText(vehiculo.getMarca());
                txtModelo.setText(String.valueOf(vehiculo.getModelo()));
                txtColor.setText(vehiculo.getColor());
                txtKilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
                txtDocumento.setText(String.valueOf(vehiculo.getIdCliente()));

            } else {

                warningAlert.setTitle("Vehículo No Encontrado");
                warningAlert.setContentText("No se encontró un vehículo con la placa: " + txtPlaca.getText());
                warningAlert.show();
            }

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Base de Datos");
            errorAlert.setContentText("Ocurrió un error al buscar el vehículo: " + e.getMessage());
            errorAlert.show();
        }
    }

    @FXML
    void actualizarVehiculo(ActionEvent event) {
        if (txtPlaca.getText().isEmpty() ||
                txtMarca.getText().isEmpty() ||
                txtModelo.getText().isEmpty() ||
                txtColor.getText().isEmpty() ||
                txtKilometraje.getText().isEmpty() ||
                txtDocumento.getText().isEmpty()) {

            warningAlert.setTitle("Información Requerida");
            warningAlert.setContentText("Complete todos los campos para actualizar el vehiculo.");
            warningAlert.show();
            return;
        }

        try {
            AppServices.getVehiculoModelo().setPlaca(txtPlaca.getText());
            AppServices.getVehiculoModelo().setColor(txtColor.getText());
            AppServices.getVehiculoModelo().setKilometraje(Integer.parseInt(txtKilometraje.getText()));


            if (AppServices.getVehiculoModelo().actualizarVehiculo()) {

                confirmationAlert.setTitle("Actualización Exitosa");
                confirmationAlert.setContentText("Vehículo actualizado con éxito.");
                confirmationAlert.show();

                limpiarCampos();
                actualizarTabla();
            }

        } catch (NumberFormatException e) {
            errorAlert.setTitle("Error de Formato");
            errorAlert.setContentText("Verifique que el campo Kilometraje contenga un número válido.");
            errorAlert.show();

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Base de Datos");
            errorAlert.setContentText("Error al actualizar el vehículo: " + e.getMessage());
            errorAlert.show();
        }
    }

    public void initialize() {

        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colKilometraje.setCellValueFactory(new PropertyValueFactory<>("kilometraje"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        try {
            List<VehiculoModelo> lista = AppServices.getVehiculoModelo().consultarVehiculos();
            ObservableList<VehiculoModelo> observableLista = FXCollections.observableArrayList(lista);
            tblVehiculos.setItems(observableLista);

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Carga de Datos");
            errorAlert.setHeaderText("No se pudo cargar la lista de vehículos.");
            errorAlert.setContentText("Verifique la conexión a la base de datos. Detalle: " + e.getMessage());
            errorAlert.show();
        }

    }

    @FXML
    void volverMenuPrincipal(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuRecepcionVista.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel de administrador");
        stage.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }
    
    public void actualizarTabla() {
        tblVehiculos.getItems().clear();

        try {
            tblVehiculos.getItems().addAll(AppServices.getVehiculoModelo().consultarVehiculos());
        
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Actualización");
            errorAlert.setHeaderText("No se pudo recargar la lista de vehículos.");
            errorAlert.setContentText("Verifique la conexión a la base de datos. Detalle: " + e.getMessage());
            errorAlert.show();
        }
    }

    public void limpiarCampos() {
        txtPlaca.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtColor.clear();
        txtKilometraje.clear();
        txtDocumento.clear();

    }

    //Funcion: Para limpiar los campos de busqueda excepto la placa
    public void limpiarCamposBusqueda() {
        txtMarca.clear();
        txtModelo.clear();
        txtColor.clear();
        txtKilometraje.clear();
        txtDocumento.clear();
    }

}
