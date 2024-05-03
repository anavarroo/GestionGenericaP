package com.viewnext.register_service.exceptionhandler;

public class UsuarioNoHabilitadoExeption extends RuntimeException{
    
    public UsuarioNoHabilitadoExeption(String mensaje) {
        super(mensaje);
    }
}
