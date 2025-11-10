package com.proyecto.serviasociados.config;

import com.proyecto.serviasociados.modelo.CitaModelo;
import com.proyecto.serviasociados.modelo.ClienteModelo;
import com.proyecto.serviasociados.modelo.UsuarioAccesoModelo;
import com.proyecto.serviasociados.modelo.VehiculoModelo;

public class AppServices {

    private static final UsuarioAccesoModelo USUARIO = new UsuarioAccesoModelo();
    private static final ClienteModelo CLIENTE = new ClienteModelo();
    private static final VehiculoModelo VEHICULO = new VehiculoModelo();
    private static final CitaModelo CITA = new CitaModelo();

    public static UsuarioAccesoModelo getUsuarioAccesoModelo() {
        return USUARIO;
    }

    public static ClienteModelo getClienteModelo() {
        return CLIENTE;
    }

    public static VehiculoModelo getVehiculoModelo() {
        return VEHICULO;
    }

    public static CitaModelo getCitaModelo() {
        return CITA;
    }
}
