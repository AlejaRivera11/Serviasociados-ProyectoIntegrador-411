package com.proyecto.serviasociados.config;

import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;
public class AppServices {

    private static final UsuarioAccesoModelo USUARIO = new UsuarioAccesoModelo();

    public static UsuarioAccesoModelo getUsuarioAccesoModelo() {
        return USUARIO;
    }
}
