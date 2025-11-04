package com.proyecto.serviasociados.controlador;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuAdministradorController {

    final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    void abrirUsuariosAcceso(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/UsuarioAccesoVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("UsuariosAcceso");
        stageEst.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML void cerrarSesion(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/LoginVista.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Inicio de sesión");
            stage.show();


            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setContentText("Error al cerrar sesión: " + e.getMessage());
            alert.show();
        }
    }

    @FXML void abrirCliente(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/ClienteAdministradorVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Clientes");
        stageEst.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML void abrirVehiculo(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/VehiculoAdministradorVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Vehiculos");
        stageEst.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }

    @FXML void abrirMecanico(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/MecanicoVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Mecanicos");
        stageEst.show();
    }

    @FXML
    void abrirHistorialCitas(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/HistorialCitasVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Historial Citas");
        stageEst.show();
    }

    @FXML void abrirAgendaCita(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/CitaVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Historial Citas");
        stageEst.show();
    }

    @FXML void abrirCitasProgramas(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/CitasProgramadasVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Citas programadas");
        stageEst.show();
    }



}