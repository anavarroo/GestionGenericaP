package com.viewnext.CRUD_service.controllers;


import com.viewnext.crud_service.exceptionhandler.CustomExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {

    @Test
     void testHandleUsernameNotFoundException() {
        // Arrange
        CustomExceptionHandler handler = new CustomExceptionHandler();
        UsernameNotFoundException ex = new UsernameNotFoundException("Usuario no encontrado");

        // Act
        ResponseEntity<String> responseEntity = handler.handleUsernameNotFoundException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Usuario no encontrado", responseEntity.getBody());
    }
}

