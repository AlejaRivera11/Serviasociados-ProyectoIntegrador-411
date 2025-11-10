package com.proyecto.serviasociados.controlador;


import com.proyecto.serviasociados.modelo.MecanicoModelo;
import com.proyecto.serviasociados.modelo.ServicioModelo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class ServicioMecanicoController {

    // Tabla Mecanicos
    @FXML
    private TableView<MecanicoModelo> tblMecanicos;

    @FXML
    private TableColumn<MecanicoModelo, Integer> colDocumento;

    @FXML
    private TableColumn<MecanicoModelo, String> colNombre;

    @FXML
    private TableColumn<MecanicoModelo, String> ColTelefono;

    @FXML
    private TableColumn<MecanicoModelo, String> colDireccion;

    // Tabla Servicios
    @FXML
    private TableView<ServicioModelo> tblServicios;

    @FXML
    private TableColumn<ServicioModelo, String> colNombreServicio;

    @FXML
    private TableColumn<ServicioModelo, Integer> colServicioId;

    @FXML
    private TableColumn<ServicioModelo, String> colTiempo;

    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);


    public void initialize() {
        //Cargar Tabla Mecancico
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("idMecanico"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreMecanico"));
        ColTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoMecanico"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionMecanico"));

        try {
            tblMecanicos.getItems().setAll(MecanicoModelo.obtenerMecanicos());
        } catch (Exception e) {
            errorAlert.setHeaderText("Error al cargar los mecánicos");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
        
        
        //Cargar Tabla Servicio
        colServicioId.setCellValueFactory(new PropertyValueFactory<>("servicioId"));
        colNombreServicio.setCellValueFactory(new PropertyValueFactory<>("nomServicio"));
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));

        try {
            tblServicios.getItems().setAll(ServicioModelo.obtenerServicios());
        } catch (Exception e) {
            errorAlert.setHeaderText("Error al cargar los servicios");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
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
    



}

