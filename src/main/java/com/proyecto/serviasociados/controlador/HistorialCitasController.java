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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.CitaModelo;

public class HistorialCitasController {

    @FXML
    private TableColumn<CitaModelo, String> colCliente;

    @FXML
    private TableColumn<CitaModelo, String> colEstado;

    @FXML
    private TableColumn<CitaModelo, Time> colHora;

    @FXML
    private TableColumn<CitaModelo, String> colMecanico;

    @FXML
    private TableColumn<CitaModelo, String> colPlaca;

    @FXML
    private TableColumn<CitaModelo, String> colServicio;

    @FXML
    private TableColumn<CitaModelo, Date> colFecha;

    @FXML
    private TableColumn<CitaModelo, Integer> colIdCita;

    @FXML
    private TableColumn<CitaModelo, String> colUsuario;

    @FXML
    private TableView<CitaModelo> tblHistorialCitas;

    @FXML
    private TextField txtPlaca;

    //Funcion: Almacenar los datos completos de las citas para facilitar la busqueda
    private ObservableList<CitaModelo> masterData;

    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    final private static Alert alerta = new Alert(Alert.AlertType.INFORMATION);


    public void initialize() {
 
        colIdCita.setCellValueFactory(new PropertyValueFactory<>("citaId"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCita"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("horaCita"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        colMecanico.setCellValueFactory(new PropertyValueFactory<>("nombreMecanico"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("nombreEstado"));

        try {
            List<CitaModelo> citas = AppServices.getCitaModelo().obtenerHistorialCitas();
            masterData = FXCollections.observableArrayList(citas);
            tblHistorialCitas.setItems(masterData); 
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Conexión");
            errorAlert.setContentText("Fallo al conectar con la base de datos: " + e.getMessage());
            errorAlert.show();
        }  
    }

    @FXML
    void buscarCita(ActionEvent event) {
        String placaBuscar = txtPlaca.getText().trim().toUpperCase();

        if (masterData == null) {
            return;
        }

        if (placaBuscar.isEmpty()) {
            tblHistorialCitas.setItems(masterData);
            return;
        }

        //Funcion: Filtrar los datos de la tabla en base a la placa ingresada
        ObservableList<CitaModelo> filteredData = FXCollections.observableArrayList();

        for (CitaModelo cita : masterData) {
            if (cita.getPlaca().equalsIgnoreCase(placaBuscar)) {
                filteredData.add(cita);
            }
        }
        tblHistorialCitas.setItems(filteredData);

        if (filteredData.isEmpty()) {
            alerta.setHeaderText("Búsqueda sin resultados");
            alerta.setContentText("No se encontraron citas activas para la placa: " + placaBuscar);
            alerta.showAndWait();
        }
    }

    @FXML
    void actualizarTabla(ActionEvent event) {
        try {
            List<CitaModelo> citas = AppServices.getCitaModelo().obtenerHistorialCitas();
            masterData = FXCollections.observableArrayList(citas);
            tblHistorialCitas.setItems(masterData); 
            limpiarCampo();
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Conexión");
            errorAlert.setContentText("Fallo al conectar con la base de datos: " + e.getMessage());
            errorAlert.show();
        }
    }

    public void limpiarCampo() {
        txtPlaca.clear();
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
