
package com.proyecto.serviasociados.controlador;

import com.proyecto.serviasociados.config.AppServices;
import com.proyecto.serviasociados.modelo.CitaModelo;
import com.proyecto.serviasociados.modelo.EstadoModelo;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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

public class CitasProgramadasController {

    @FXML
    private TextField txtPlaca;

    @FXML
    private TableColumn<CitaModelo, String> ColCliente;

    @FXML
    private TableColumn<CitaModelo, String> ColEstado;

    @FXML
    private TableColumn<CitaModelo, Time> ColHora;

    @FXML
    private TableColumn<CitaModelo, String> ColMecanico;

    @FXML
    private TableColumn<CitaModelo, String> ColPlaca;

    @FXML
    private TableColumn<CitaModelo, String> ColServicio;

    @FXML
    private TableColumn<CitaModelo, Date> colFecha;

    @FXML
    private TableColumn<CitaModelo, Integer> colIdCita;

    @FXML
    private TableColumn<CitaModelo, String> colUsuario;

    @FXML
    private ComboBox<EstadoModelo> cmbEstados;

    @FXML
    private TableView<CitaModelo> tblCita;

    //Funcion: almacenar todas las citas obtenidas de la base de datos
    private ObservableList<CitaModelo> masterData;

    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    final private static Alert alerta = new Alert(Alert.AlertType.INFORMATION);


    public void cargarEstados() {
        try {
            cmbEstados.getItems().clear();
            cmbEstados.getItems().addAll(EstadoModelo.obtenerEstados());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actualizarEstado(ActionEvent event) {

        CitaModelo citaSeleccionada = tblCita.getSelectionModel().getSelectedItem();
        EstadoModelo nuevoEstado = cmbEstados.getSelectionModel().getSelectedItem();

        if (citaSeleccionada == null) {
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, seleccione una cita de la tabla.");
            alerta.showAndWait();
            return;
        }

        if (nuevoEstado == null) {
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, seleccione un estado.");
            alerta.showAndWait();
            return;
        }

        try {
            int citaId = citaSeleccionada.getCitaId();
            String nombreNuevoEstado = nuevoEstado.toString();

            boolean exito = AppServices.getCitaModelo().actualizarEstadoCita(citaId, nombreNuevoEstado);

            if (exito) {
                citaSeleccionada.setNombreEstado(nombreNuevoEstado);
                alerta.setHeaderText(null);
                alerta.setContentText("Estado de la cita N° " + citaId + " actualizado a: " + nombreNuevoEstado);
                alerta.showAndWait();
                recargarCitas(); 
                limpiarFiltros();

            } else {
                alerta.setHeaderText("Error de Actualización");
                alerta.setContentText("No se pudo actualizar el estado de la cita N° " + citaId);
                alerta.showAndWait();

            }

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Base de Datos");
            errorAlert.setContentText("Fallo al actualizar el estado: " + e.getMessage());
            errorAlert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buscarCitaProgramada(ActionEvent event) {
        String placaBuscar = txtPlaca.getText().trim().toUpperCase();

        if (masterData == null) {
            return;
        }

        if (placaBuscar.isEmpty()) {
            tblCita.setItems(masterData);
            return;
        }

        //Funcion: Filtrar las citas por placa
        ObservableList<CitaModelo> filteredData = FXCollections.observableArrayList();

        for (CitaModelo cita : masterData) {
            if (cita.getPlaca().equalsIgnoreCase(placaBuscar)) {
                filteredData.add(cita);
            }
        }

        tblCita.setItems(filteredData);

        if (filteredData.isEmpty()) {

            alerta.setHeaderText("Búsqueda sin resultados");
            alerta.setContentText("No se encontraron citas activas para la placa: " + placaBuscar);
            alerta.showAndWait();
        }
    }

    public void initialize() {
 
        colIdCita.setCellValueFactory(new PropertyValueFactory<>("citaId"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCita"));
        ColHora.setCellValueFactory(new PropertyValueFactory<>("horaCita"));
        ColPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        ColCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        ColServicio.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        ColMecanico.setCellValueFactory(new PropertyValueFactory<>("nombreMecanico"));
        ColEstado.setCellValueFactory(new PropertyValueFactory<>("nombreEstado"));

        try {
            List<CitaModelo> citas = AppServices.getCitaModelo().obtenerCitasProgramadas();
            masterData = FXCollections.observableArrayList(citas);
            tblCita.setItems(masterData); 
        } catch (SQLException e) {
            errorAlert.setTitle("Error de Conexión");
            errorAlert.setContentText("Fallo al conectar con la base de datos: " + e.getMessage());
            errorAlert.show();
        }

        cargarEstados();
    }

    @FXML
    void volverMenuPrincipal(ActionEvent event) throws IOException {
        String rol = UsuarioAccesoModelo.getRolActivo();
        String fxmlPath; // Ruta del archivo FXML a cargar
        String stageTitle;

        if ("ADMINISTRADOR".equalsIgnoreCase(rol)) { 
            fxmlPath = "/vista/MenuAdministradorVista.fxml";
            stageTitle = "Panel de Administrador";
        } else if ("RECEPCION".equalsIgnoreCase(rol)) {
            fxmlPath = "/vista/MenuRecepcionVista.fxml";
            stageTitle = "Panel de Recepción";
        } else {
            System.err.println("Error: Rol de usuario no reconocido.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(stageTitle);
        stage.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void recargarCitas() {
        try {
            List<CitaModelo> citas = AppServices.getCitaModelo().obtenerCitasProgramadas();
            masterData = FXCollections.observableArrayList(citas);
            tblCita.setItems(masterData);

        } catch (SQLException e) {
            errorAlert.setTitle("Error de Conexión");
            errorAlert.setContentText("Fallo al recargar las citas: " + e.getMessage());
            errorAlert.show();
        }
    }

    public void limpiarFiltros() {
        txtPlaca.clear();
        cmbEstados.getSelectionModel().clearSelection();
    }
}
