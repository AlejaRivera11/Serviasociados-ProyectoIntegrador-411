package com.proyecto.serviasociados.config;

import com.proyecto.serviasociados.modelo.ClientesModelo;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;

public class AppServices {

    private static final UsuarioAccesoModelo USUARIO = new UsuarioAccesoModelo();
    private static final ClientesModelo CLIENTE = new ClientesModelo();

    public static UsuarioAccesoModelo getUsuarioAccesoModelo() {
        return USUARIO;
    }

    public static ClientesModelo getClientesModelo() {
        return CLIENTE;
    }
}
