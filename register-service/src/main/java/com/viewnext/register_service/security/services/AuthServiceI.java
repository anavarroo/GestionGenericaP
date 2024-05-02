package com.viewnext.register_service.security.services;

import com.viewnext.register_service.persistence.dto.UserDto;
import com.viewnext.register_service.persistence.dto.UserDtoRegister;
import com.viewnext.register_service.security.model.AuthResponse;
import com.viewnext.register_service.security.model.LoginRequest;
import com.viewnext.register_service.security.model.RegisterRequest;

public interface AuthServiceI {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request La solicitud de registro.
     * @return La respuesta de autenticación.
     */
    UserDtoRegister register(RegisterRequest request);

    /**
     * Inicia sesión en el sistema.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta de autenticación.
     */
    AuthResponse login(LoginRequest request);

}
