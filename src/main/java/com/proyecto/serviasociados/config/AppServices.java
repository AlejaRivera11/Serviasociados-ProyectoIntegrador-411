package com.proyecto.serviasociados.config;

import com.proyecto.serviasociados.modelo.ClienteModelo;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;

public class AppServices {

    private static final UsuarioAccesoModelo USUARIO = new UsuarioAccesoModelo();
    private static final ClienteModelo CLIENTE = new ClienteModelo();

    public static UsuarioAccesoModelo getUsuarioAccesoModelo() {
        return USUARIO;
    }

    public static ClienteModelo getClienteModelo() {
        return CLIENTE;
    }
}
