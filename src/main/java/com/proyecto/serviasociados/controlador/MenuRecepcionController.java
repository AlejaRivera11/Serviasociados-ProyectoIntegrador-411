package com.proyecto.serviasociados.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuRecepcionController {

    final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML void abrirAgendaCitas(ActionEvent event) throws IOException {
        FXMLLoader loaderEst = new FXMLLoader(getClass().getResource("/vista/ClienteRecepcionVista.fxml"));
        Stage stageEst = new Stage();
        stageEst.setScene(new Scene(loaderEst.load()));
        stageEst.setTitle("Clientes");
        stageEst.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML void abrirCitasProgramas(ActionEvent event) {

    }

    @FXML void abrirCliente(ActionEvent event) {

    }

    @FXML void abrirVehiculo(ActionEvent event) {

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
}
