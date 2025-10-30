package com.proyecto.serviasociados.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClienteRecepcionController {

    @FXML private TableColumn<?, ?> colCorreo;
    @FXML private TableColumn<?, ?> colDireccion;
    @FXML private TableColumn<?, ?> colDocumento;
    @FXML private TableColumn<?, ?> colNombre;
    @FXML private TableColumn<?, ?> colTelefono;
    @FXML private TableView<?> tblClientes;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;

    @FXML void actualizarCliente(ActionEvent event) {

    }

    @FXML void buscarCliente(ActionEvent event) {

    }

    @FXML void insertarCliente(ActionEvent event) {

    }

    @FXML void volverMenuPrincipal(ActionEvent event) {

    }

    //Para cargar la informacion a la tabla de la vista
    public void initialize (){

    }

    public void actualizarTabla (){

    }

    public void  limpiarCampos (){

    }
}