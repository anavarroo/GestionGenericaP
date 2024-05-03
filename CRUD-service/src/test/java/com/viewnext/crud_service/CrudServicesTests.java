package com.viewnext.CRUD_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.viewnext.CRUD_service.persistence.model.Role;
import com.viewnext.CRUD_service.persistence.model.User;
import com.viewnext.CRUD_service.persistence.repository.UserRepositoryI;
import com.viewnext.CRUD_service.services.UserServiceImp;

public class CrudServicesTests {

    @Mock
    private UserRepositoryI userRepository;
    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
 		Mockito.mock(UserRepositoryI.class);
        userService = new UserServiceImp(userRepository);
    }

	@Test
	void ContraseñaEncriptadaTest() {
        String contraseñaSinEncriptar = "user1234";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String contraseñaEncriptada = encoder.encode(contraseñaSinEncriptar);
		
		assertNotEquals(contraseñaEncriptada,contraseñaSinEncriptar);
    }

	@Test
	void crearUsuarioTest() {
		String nombre = "Juan";
		String apellidos = "Perez";
		int edad = 25;
		String correo = "J.Perez@email.com";
		String direccion ="Rua del Percebe, 3";
		int telefono = 123456789;
		String contrasena="12345678";
		Role role = Role.USER;

		User user = new User(nombre, apellidos, edad, correo, direccion, telefono, contrasena, role);
		
		assertEquals(nombre, user.getNombre());
		assertEquals(apellidos, user.getApellidos());
		assertEquals(edad, user.getEdad());
		assertEquals(correo, user.getCorreo());
		assertEquals(direccion, user.getDireccion());
		assertEquals(telefono, user.getTelefono());
		assertEquals(contrasena, user.getContrasena());
		assertEquals(role, user.getRole());
    	}

	@Test
	void GetAuthoritiesIsCalled_ReturnEmptyList() {
    // Arrange
    User user = new User();

    // Act
    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

    // Assert
    assertEquals("[]",authorities.toString());
}

@Test
void getPassword_ReturnContrasenatest() {
	// Arrange
	String contrasena = "contrasena";
	User user = new User("Nombre", "Apellidos", 25, "ejemplo@email.com", "Direccion", 12345678, contrasena, Role.USER);

	// Act
	String actual = user.getPassword();

	// Assert
	Assertions.assertEquals(contrasena, actual);
}

@Test
    void getUsername_ReturnEmailtest() {
        // Arrange
        User user = new User("John", "Doe", 25, "ejemplo@email.com", "Somewhere", 12345678, "password", Role.USER);

        // Act
        String correo = user.getUsername();

        // Assert
        Assertions.assertEquals("ejemplo@email.com", correo);
    }

	@Test
    void AccountNonExpiredtest() {
        User user = new User();
        boolean result = user.isAccountNonExpired();


        assertTrue(result);
    }

	@Test
	void AccountNonLockedtest() {
		User user = new User();
		boolean result = user.isAccountNonLocked();

		assertTrue(result);
	}

	@Test
	void CredentialsNonExpiredtest() {
		User user = new User();
        boolean result = user.isCredentialsNonExpired();

		assertTrue(result);
	}

	@Test
	void Enabledtest() {
		User user = new User();
        boolean result = user.isEnabled();

		assertTrue(result);
	}
    
}
