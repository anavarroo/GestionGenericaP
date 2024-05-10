package com.viewnext.CRUD_service.controllers;


import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import com.viewnext.crud_service.services.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepositoryI userRepositoryI;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void consultarUsuarioPorApellidos() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password"));
        users.add(new User(2, "pepe", "Ramirez",30, "john.smith@example.com", "123 Street", "+34123456789",true, "password"));

        when(userRepositoryI.findByApellidos(anyString())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorApellidos("Ramirez");

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals("Ramirez", userDtos.get(0).getApellidos());
        assertEquals("Ramirez", userDtos.get(1).getApellidos());
    }

    @Test
    void consultarUsuarioPorEdad() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password"));
        users.add(new User(2, "pepe", "Ramirez",30, "john.smith@example.com", "123 Street", "+34123456789",true, "password"));
        when(userRepositoryI.findByEdad(anyInt())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorEdad(30);

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals(30, userDtos.get(0).getEdad());
        assertEquals(30, userDtos.get(1).getEdad());
    }

    @Test
    void consultarUsuarioPorDireccion() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password"));
        users.add(new User(2, "pepe", "Ramirez",30, "john.smith@example.com", "123 Street", "+34123456789",true, "password"));

        when(userRepositoryI.findByDireccion(anyString())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorDireccion("123 Street");

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals("123 Street", userDtos.get(0).getDireccion());
        assertEquals("123 Street", userDtos.get(1).getDireccion());
    }
    void crearUsuario() {
        User user = new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password");
        when(userRepositoryI.save(any(User.class))).thenReturn(user);

        UserDto userDto = userService.crearUsuario(user);

        assertNotNull(userDto);
        assertEquals("Alejandro", userDto.getNombre());
        assertEquals("Ramirez", userDto.getApellidos());
        assertEquals("ale@example.com", userDto.getCorreo());
    }
    @Test
    void actualizarUsuario() {
        User user = new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password");
        UserDto userDto = new UserDto("John", "Updated Doe", 35, "john.doe@example.com", "123 Updated Street", "+34987654321");

        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);
        when(userRepositoryI.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userService.actualizarUsuario("ale@example.com", userDto);

        assertNotNull(updatedUserDto);
        assertEquals("Updated Doe", updatedUserDto.getApellidos());
        assertEquals(35, updatedUserDto.getEdad());
        assertEquals("123 Updated Street", updatedUserDto.getDireccion());
        assertEquals("+34987654321", updatedUserDto.getTelefono());
    }

    @Test
    void borrarUsuarioPorEmail() {
        User user = new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password");
        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);

        assertDoesNotThrow(() -> userService.borrarUsuarioPorEmail("ale@example.com"));

        verify(userRepositoryI, times(1)).delete(user);
    }
    @Test
    void borrarUsuarioPorEmail_NotFound() {
        when(userRepositoryI.findByCorreo(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.borrarUsuarioPorEmail("ale@example.com"));
    }
    @Test
    void aprobarRegistro() {
        User user = new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 Street","+34123456788", true, "password");
        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);
        when(userRepositoryI.save(any(User.class))).thenReturn(user);

        assertDoesNotThrow(() -> userService.aprobarRegistro("ale@example.com", true));

        assertTrue(user.isEstado());
    }


}
