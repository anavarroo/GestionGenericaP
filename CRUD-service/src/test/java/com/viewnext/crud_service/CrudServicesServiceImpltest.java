package com.viewnext.crud_service;

<<<<<<< HEAD:CRUD-service/src/test/java/com/viewnext/CRUD_service/CrudServicesServiceImpltest.java
import com.viewnext.CRUD_service.persistence.dto.UserDto;
import com.viewnext.CRUD_service.persistence.model.Role;
import com.viewnext.CRUD_service.persistence.model.User;
import com.viewnext.CRUD_service.persistence.repository.UserRepositoryI;
import com.viewnext.CRUD_service.services.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
=======
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import com.viewnext.crud_service.services.UserServiceImp;
>>>>>>> origin/feature-ale-v.2:CRUD-service/src/test/java/com/viewnext/crud_service/CrudServicesServiceImpltest.java

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
    void consultarUsuarioPorNombre() {
        List<User> users = new ArrayList<>();
        users.add(new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER));
        users.add(new User("John", "Smith", 40, "john.smith@example.com", "456 Street", 987654321, "password", Role.USER));

        when(userRepositoryI.findByNombre(anyString())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorNombre("John");

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals("John", userDtos.get(0).getNombre());
        assertEquals("John", userDtos.get(1).getNombre());
    }

    @Test
    void consultarUsuarioPorApellidos() {
        List<User> users = new ArrayList<>();
        users.add(new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER));
        users.add(new User("Jane", "Doe", 40, "jane.doe@example.com", "456 Street", 987654321, "password", Role.USER));

        when(userRepositoryI.findByApellidos(anyString())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorApellidos("Doe");

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals("Doe", userDtos.get(0).getApellidos());
        assertEquals("Doe", userDtos.get(1).getApellidos());
    }

    @Test
    void consultarUsuarioPorEdad() {
        List<User> users = new ArrayList<>();
        users.add(new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER));
        users.add(new User("Jane", "Doe", 30, "jane.doe@example.com", "456 Street", 987654321, "password", Role.USER));

        when(userRepositoryI.findByEdad(anyInt())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorEdad(30);

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals(30, userDtos.get(0).getEdad());
        assertEquals(30, userDtos.get(1).getEdad());
    }

    @Test
    void consultarUsuarioPorCorreo() {
        User user = new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER);

        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);

        List<UserDto> userDtos = userService.consultarUsuarioPorCorreo("john.doe@example.com");

        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals("john.doe@example.com", userDtos.get(0).getCorreo());
    }

    @Test
    void consultarUsuarioPorDireccion() {
        List<User> users = new ArrayList<>();
        users.add(new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER));
        users.add(new User("Jane", "Smith", 40, "jane.smith@example.com", "123 Street", 987654321, "password", Role.USER));

        when(userRepositoryI.findByDireccion(anyString())).thenReturn(users);

        List<UserDto> userDtos = userService.consultarUsuarioPorDireccion("123 Street");

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals("123 Street", userDtos.get(0).getDireccion());
        assertEquals("123 Street", userDtos.get(1).getDireccion());
    }

    @Test
    void consultarUsuarioPorTelefono() {
        User user = new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER);

        when(userRepositoryI.findByTelefono(anyInt())).thenReturn(user);

        List<UserDto> userDtos = userService.consultarUsuarioPorTelefono(123456789);

        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals(123456789, userDtos.get(0).getTelefono());
    }
    @Test
    void crearUsuario() {
        User user = new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER);
        when(userRepositoryI.save(any(User.class))).thenReturn(user);

        UserDto userDto = userService.crearUsuario(user);

        assertNotNull(userDto);
        assertEquals("John", userDto.getNombre());
        assertEquals("Doe", userDto.getApellidos());
        assertEquals("john.doe@example.com", userDto.getCorreo());
    }
    @Test
    void actualizarUsuario() {
        User user = new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER);
        UserDto userDto = new UserDto("John", "Updated Doe", 35, "john.doe@example.com", "123 Updated Street", 987654321);

        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);
        when(userRepositoryI.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userService.actualizarUsuario("john.doe@example.com", userDto);

        assertNotNull(updatedUserDto);
        assertEquals("Updated Doe", updatedUserDto.getApellidos());
        assertEquals(35, updatedUserDto.getEdad());
        assertEquals("123 Updated Street", updatedUserDto.getDireccion());
        assertEquals(987654321, updatedUserDto.getTelefono());
    }

    @Test
    void borrarUsuarioPorEmail() {
        User user = new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER);
        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);

        assertDoesNotThrow(() -> userService.borrarUsuarioPorEmail("john.doe@example.com"));

        verify(userRepositoryI, times(1)).delete(user);
    }
    @Test
    void borrarUsuarioPorEmail_NotFound() {
        when(userRepositoryI.findByCorreo(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.borrarUsuarioPorEmail("john.doe@example.com"));
    }
    @Test
    void aprobarRegistro() {
        User user = new User("John", "Doe", 30, "john.doe@example.com", "123 Street", 123456789, "password", Role.USER);
        when(userRepositoryI.findByCorreo(anyString())).thenReturn(user);
        when(userRepositoryI.save(any(User.class))).thenReturn(user);

        assertDoesNotThrow(() -> userService.aprobarRegistro("john.doe@example.com", true));

        assertTrue(user.isEstado());
    }


}
