
package com.proyecto.serviasociados.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CitasProgramadasController {

    @FXML
    private ComboBox<?> cmbEstados;

    @FXML
    private TableView<?> tblCita;

    @FXML
    void actualizarEstado(ActionEvent event) {

    }

    @FXML
    void buscarCitaProgramada(ActionEvent event) {

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
