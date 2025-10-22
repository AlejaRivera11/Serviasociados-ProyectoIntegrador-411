package com.proyecto.serviasociados;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader loaderProf = new FXMLLoader(getClass().getResource("/vista/LoginVista.fxml"));
        FXMLLoader loaderProf = new FXMLLoader(getClass().getResource("/vista/MenuRecepcionVista.fxml"));
        Stage stageProf = new Stage();
        stageProf.setScene(new Scene(loaderProf.load()));
        stageProf.setTitle("Serviasociados");
        stageProf.show();


    }

    public static void main(String[] args)  {

        launch();
    }
}
