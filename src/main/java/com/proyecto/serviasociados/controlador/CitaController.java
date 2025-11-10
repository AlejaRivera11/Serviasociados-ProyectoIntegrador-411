package com.proyecto.serviasociados.controlador;

import com.proyecto.serviasociados.modelo.CitaModelo;
import com.proyecto.serviasociados.modelo.MecanicoModelo;
import com.proyecto.serviasociados.modelo.ServicioModelo;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;

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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CitaController {

    @FXML
    private DatePicker calentadario;

    @FXML
    private ComboBox<String> cmbHora;

    @FXML
    private ComboBox<MecanicoModelo> cmbMecanicos;

    @FXML
    private ComboBox<ServicioModelo> cmbServicios;

    @FXML
    private TextField txtPlaca;

    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    // Funcion para cargar las horas en el ComboBox
    private final List<String> HORAS_BASE = List.of(
            "07:00", "08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00");

    public void cargarServicios() {
        try {

            List<ServicioModelo> listaServicios = ServicioModelo.obtenerServicios();
            cmbServicios.setItems(FXCollections.observableArrayList(listaServicios));

        } catch (Exception e) {
            errorAlert.setHeaderText("Error al cargar los servicios");

        }
    }

    public void cargarMecanicos() {
        try {

            List<MecanicoModelo> listaMecanicos = MecanicoModelo.obtenerMecanicos();
            cmbMecanicos.setItems(FXCollections.observableArrayList(listaMecanicos));

        } catch (Exception e) {
            errorAlert.setHeaderText("Error al cargar los mecánicos");

        }
    }

    public void cargarHorasBase() {
        cmbHora.setItems(FXCollections.observableArrayList(HORAS_BASE));
    }

    @FXML
    void agendarCita(ActionEvent event) {
        LocalDate fechaCita = calentadario.getValue();
        String horaCitaStr = cmbHora.getValue();
        String placa = txtPlaca.getText();
        MecanicoModelo mecanicoSeleccionado = cmbMecanicos.getValue();
        ServicioModelo servicioSeleccionado = cmbServicios.getValue();

        // Funcion: Validacion de Domingo
        if (fechaCita != null && fechaCita.getDayOfWeek() == DayOfWeek.SUNDAY) {
            errorAlert.setHeaderText("Agendamiento no permitido.");
            errorAlert.setContentText("No se pueden agendar citas los domingos.");
            errorAlert.showAndWait();
            return;
        }

        // Funcion: Validacion de usuario activo
        int usuarioAgendadorId = UsuarioAccesoModelo.getIdUsuarioActivo();
        if (usuarioAgendadorId == 0) {
            errorAlert.setHeaderText("Error de Sesión");
            errorAlert.setContentText(
                    "No se pudo identificar al usuario que agenda la cita. Por favor, inicie sesión nuevamente.");
            errorAlert.showAndWait();
            return;
        }

        if (fechaCita == null || horaCitaStr == null || placa.isEmpty()
                || mecanicoSeleccionado == null
                || servicioSeleccionado == null) {
            errorAlert.setHeaderText("Por favor, complete todos los campos.");
            errorAlert.showAndWait();
            return;
        }

        try {
            LocalTime horaCita = LocalTime.parse(horaCitaStr);
            CitaModelo citaModelo = new CitaModelo();
            boolean exito = citaModelo.agendarCita(
                    fechaCita,
                    horaCita,
                    placa,
                    usuarioAgendadorId,
                    mecanicoSeleccionado.getIdMecanico(),
                    servicioSeleccionado.getServicioId());

            if (exito) {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Cita agendada exitosamente.");
                infoAlert.showAndWait();
                limpiarFormulario();
            }

        } catch (java.sql.SQLException e) {
            if ("45000".equals(e.getSQLState())) {

                errorAlert.setHeaderText("Mecánico no disponible.");
                errorAlert.setContentText("El mecánico ya tiene una cita en ese horario.");

            } else {
                errorAlert.setHeaderText("Error de Base de Datos.");
                errorAlert.setContentText(e.getMessage());
            }

            errorAlert.showAndWait();

        } catch (Exception e) {
            errorAlert.setHeaderText("Error General del Sistema.");
            errorAlert.setContentText("Error Detalle técnico: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        cargarServicios();
        cargarMecanicos();
        cargarHorasBase();

    }


    public void limpiarFormulario() {
        calentadario.setValue(null);
        cmbHora.setValue(null);
        txtPlaca.clear();
        cmbMecanicos.setValue(null);
        cmbServicios.setValue(null);
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

}
