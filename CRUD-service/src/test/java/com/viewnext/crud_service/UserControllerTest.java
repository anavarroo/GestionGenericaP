package com.viewnext.CRUD_service;

import com.viewnext.CRUD_service.controllers.UserController;
import com.viewnext.CRUD_service.persistence.dto.UserDto;
import com.viewnext.CRUD_service.persistence.model.User;
import com.viewnext.CRUD_service.services.UserServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceI userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void crearUsuario() {
        User user = new User();
        UserDto userDto = new UserDto();
        when(userService.crearUsuario(any(User.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.crearUsuario(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).crearUsuario(user);
    }

    @Test
    void actualizarUsuario() {
        String correo = "example@example.com";
        UserDto userDto = new UserDto();
        when(userService.actualizarUsuario(anyString(), any(UserDto.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.actualizarUsuario(correo, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).actualizarUsuario(correo, userDto);
    }


    @Test
    void borrarUsuarioPorEmail() {
        String correo = "example@example.com";

        userController.borrarUsuarioPorEmail(correo);

        verify(userService, times(1)).borrarUsuarioPorEmail(correo);
    }

    @Test
    void consultarUsuarioPorNombre() {
        String nombre = "John Doe";
        List<UserDto> userDtoList = Collections.singletonList(new UserDto());
        when(userService.consultarUsuarioPorNombre(nombre)).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.consultarUsuarioPorNombre(nombre);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
        verify(userService, times(1)).consultarUsuarioPorNombre(nombre);
    }

    @Test
    void consultarUsuarioPorApellidos() {
        String apellidos = "Doe";
        List<UserDto> userDtoList = Collections.singletonList(new UserDto());
        when(userService.consultarUsuarioPorApellidos(apellidos)).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.consultarUsuarioPorApellidos(apellidos);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
        verify(userService, times(1)).consultarUsuarioPorApellidos(apellidos);
    }

    @Test
    void consultarUsuarioPorEdad() {
        int edad = 30;
        List<UserDto> userDtoList = Collections.singletonList(new UserDto());
        when(userService.consultarUsuarioPorEdad(edad)).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.consultarUsuarioPorEdad(edad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
        verify(userService, times(1)).consultarUsuarioPorEdad(edad);
    }

    @Test
    void consultarUsuarioPorCorreo() {
        String correo = "example@example.com";
        List<UserDto> userDtoList = Collections.singletonList(new UserDto());
        when(userService.consultarUsuarioPorCorreo(correo)).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.consultarUsuarioPorCorreo(correo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
        verify(userService, times(1)).consultarUsuarioPorCorreo(correo);
    }

    @Test
    void consultarUsuarioPorDireccion() {
        String direccion = "123 Street";
        List<UserDto> userDtoList = Collections.singletonList(new UserDto());
        when(userService.consultarUsuarioPorDireccion(direccion)).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.consultarUsuarioPorDireccion(direccion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
        verify(userService, times(1)).consultarUsuarioPorDireccion(direccion);
    }

    @Test
    void consultarUsuarioPorTelefono() {
        int telefono = 123456789;
        List<UserDto> userDtoList = Collections.singletonList(new UserDto());
        when(userService.consultarUsuarioPorTelefono(telefono)).thenReturn(userDtoList);

        ResponseEntity<List<UserDto>> response = userController.consultarUsuarioPorTelefono(telefono);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
        verify(userService, times(1)).consultarUsuarioPorTelefono(telefono);
    }

    @Test
    void confirmarUsuario() {
        String correo = "example@example.com";
        boolean estado = true;

        userController.confirmarUsuario(correo, estado);

        verify(userService, times(1)).aprobarRegistro(correo, estado);
    }
}

