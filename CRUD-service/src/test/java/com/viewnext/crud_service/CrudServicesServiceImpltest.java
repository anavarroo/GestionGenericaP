package com.viewnext.crud_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import com.viewnext.crud_service.services.UserServiceImp;

public class CrudServicesServiceImpltest {
    
     @Mock
    private UserRepositoryI userRepositoryI;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    public void crearUsuarioTest() {
        // Arrange
        String correo = "test@test";
        String nombre = "John";
        String apellidos = "Doe";
        int edad = 25;
        String direccion = "Somewhere";
        int telefono = 123456789;
        String contrasena = "test1234";
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = encoder.encode(contrasena);
        user.setContrasena(contrasenaEncriptada);
        UserDto expectedUserDto = new UserDto();

        // Act
        userServiceImp.crearUsuario(user);
        UserDto actualUserDto = userServiceImp.consultarUsuarioPorCorreo(correo);

        // Assert
        assertEquals(expectedUserDto, actualUserDto);
    }
}

//     @Test
// public void whenConvertingUserToUserDto_thenAllFieldsAreCopied() {
//     // given
//     User user = new User();

//     // when
//     UserDto userDto = userService.convertToDto(user);

//     // then
//     assertEquals(userDto.getNombre()).isEqualTo(user.getNombre());
//     assertEquals(userDto.getApellidos()).isEqualTo(user.getApellidos());
//     assertEquals(userDto.getCorreo()).isEqualTo(user.getCorreo());
//     assertEquals(userDto.getEdad()).isEqualTo(user.getEdad());
//     assertEquals(userDto.getDireccion()).isEqualTo(user.getDireccion());
//     assertEquals(userDto.getTelefono()).isEqualTo(user.getTelefono());
// }
// }
