package com.proyecto.serviasociados.controlador;

import com.proyecto.serviasociados.modelo.MecanicoModelo;
import com.proyecto.serviasociados.modelo.ServicioModelo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CitaController {

    @FXML
    private DatePicker calentadario;

    @FXML
    private ComboBox<?> cmbHora;

    @FXML
    private ComboBox<MecanicoModelo> cmbMecanicos;

    @FXML
    private ComboBox<ServicioModelo> cmbServicios;

    @FXML
    private TextField txtPlaca;

    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void cargarServicios() {
        try {
            
            List<ServicioModelo> listaServicios = ServicioModelo.obtenerServicios();

            cmbServicios.setItems(FXCollections.observableArrayList(listaServicios)); // Asignar la lista al ComboBox el FxCollections sirve para convertir la lista a un formato que el ComboBox pueda manejar

        } catch (Exception e) {
            errorAlert.setHeaderText("Error al cargar los servicios");

        }
    }

    private void cargarMecanicos() {
        try {
            
            List<MecanicoModelo> listaMecanicos = MecanicoModelo.obtenerMecanicos();

            cmbMecanicos.setItems(FXCollections.observableArrayList(listaMecanicos));

        } catch (Exception e) {
            errorAlert.setHeaderText("Error al cargar los mecánicos");
            
        }
    }

    @FXML
    void agendarCita(ActionEvent event) {

    }

    @FXML
    void cancelarVolverInicio(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuAdministradorVista.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel de administrador");
        stage.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    

    @FXML
    public void initialize() {
        cargarServicios();
        cargarMecanicos();
    }


}

