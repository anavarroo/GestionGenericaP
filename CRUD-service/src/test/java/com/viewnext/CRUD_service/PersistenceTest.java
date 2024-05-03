package com.viewnext.CRUD_service;

import com.viewnext.CRUD_service.persistence.dto.UserDto;
import com.viewnext.CRUD_service.persistence.model.Role;
import com.viewnext.CRUD_service.persistence.model.User;
import com.viewnext.CRUD_service.persistence.repository.UserRepositoryI;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//UserDto
class UserDtoTest {

    @Test
    void gettersAndSetters() {
        // Crear una instancia de UserDto
        UserDto userDto = new UserDto();

        // Establecer valores usando los setters
        userDto.setNombre("John");
        userDto.setApellidos("Doe");
        userDto.setEdad(30);
        userDto.setCorreo("john.doe@example.com");
        userDto.setDireccion("123 Street");
        userDto.setTelefono(123456789);

        // Verificar si los getters devuelven los valores correctos
        assertEquals("John", userDto.getNombre());
        assertEquals("Doe", userDto.getApellidos());
        assertEquals(30, userDto.getEdad());
        assertEquals("john.doe@example.com", userDto.getCorreo());
        assertEquals("123 Street", userDto.getDireccion());
        assertEquals(123456789, userDto.getTelefono());
    }

    @Test
    void toStringTest() {
        // Crear una instancia de UserDto
        UserDto userDto = new UserDto("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789);

        // Verificar si el método toString devuelve el formato esperado
        String expectedToString = "UserDto(nombre=John, apellidos=Doe, edad=30, correo=john.doe@example.com, direccion=123 Street, telefono=123456789)";
        assertEquals(expectedToString, userDto.toString());
    }

    @Nested
    class UserTest {

        @Test
        void testUserDetailsMethods() {
            User user = new User();
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
