package com.viewnext.register_service.security.controllers;

import com.viewnext.register_service.persistence.dto.UserDto;
import com.viewnext.register_service.security.model.AuthResponse;
import com.viewnext.register_service.security.model.LoginRequest;
import com.viewnext.register_service.security.model.RegisterRequest;
import com.viewnext.register_service.security.services.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Order(1)
public class AuthController {

    private final AuthServiceImpl authMngm;

    @Autowired
    public AuthController(AuthServiceImpl authMngm) {
        this.authMngm = authMngm;
    }

    /**
     * Endpoint para el registro de usuarios.
     *
     * @param request Datos de registro del usuario.
     * @return Respuesta con el token de autenticación.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authMngm.register(request));
    }

    /**
     * Endpoint para la autenticación de usuarios.
     *
     * @param request Datos de inicio de sesión del usuario.
     * @return Respuesta con el token de autenticación.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authMngm.login(request));
    }
}
