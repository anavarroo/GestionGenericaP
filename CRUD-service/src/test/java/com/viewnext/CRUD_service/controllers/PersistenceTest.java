package com.viewnext.CRUD_service.controllers;


import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.model.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//UserDto
class UserDtoTest {

    @Test
    void toStringTest() {
        // Crear una instancia de UserDto
        UserDto userDto = new UserDto("John", "Doe", 30, "john.doe@example.com", "123 Street", "+34123456789");

        // Verificar si el método toString devuelve el formato esperado
        String expectedToString = "UserDto(nombre=John, apellidos=Doe, edad=30, correo=john.doe@example.com, direccion=123 Street, telefono=+34123456789)";
        assertEquals(expectedToString, userDto.toString());
    }

    @Nested
    class UserTest {

        @Test
        void testUserDetailsMethods() {
            User user = new User(1, "Alejandro", "Ramirez", 30, "ale@example.com", "123 street","+34123456788", true, "password");
            user.setCorreo("example@example.com");
            user.setContrasena("password");

            // Verificar métodos de UserDetails
            assertEquals("example@example.com", user.getUsername());
            assertEquals("password", user.getPassword());
            assertTrue(user.isAccountNonExpired());
            assertTrue(user.isAccountNonLocked());
            assertTrue(user.isCredentialsNonExpired());
            assertTrue(user.isEnabled());
        }
    }
}
