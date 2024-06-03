package com.viewnext.register_service.exceptions;

public class UsuarioYaExiste extends RuntimeException {

    public UsuarioYaExiste(String mensaje) {
        super(mensaje);
    }

}
