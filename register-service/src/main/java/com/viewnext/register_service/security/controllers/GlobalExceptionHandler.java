package com.viewnext.register_service.security.controllers;

import com.viewnext.register_service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la aplicación.
 * Maneja excepciones personalizadas y proporciona respuestas HTTP apropiadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja UserNotFoundException y devuelve una respuesta 404 Not Found.
     *
     * @param ex la UserNotFoundException
     * @return un ResponseEntity con el mensaje de error y el estado HTTP
     */
    @ExceptionHandler(UsuarioNoEncontrado.class)
    public ResponseEntity<String> handleUsuarioNoEncontradoException(UsuarioNoEncontrado ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja UsernameAlreadyExistsException y devuelve una respuesta 409 Conflict.
     *
     * @param ex la UsernameAlreadyExistsException
     * @return un ResponseEntity con el mensaje de error y el estado HTTP
     */
    @ExceptionHandler(UsuarioYaExiste.class)
    public ResponseEntity<String> handleUsuarioYaExisteException(UsuarioYaExiste ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Maneja InvalidCredentialsException y devuelve una respuesta 401 Unauthorized.
     *
     * @param ex la InvalidCredentialsException
     * @return un ResponseEntity con el mensaje de error y el estado HTTP
     */
    @ExceptionHandler(DatosIncorrectos.class)
    public ResponseEntity<String> handleDatosIncorrectosException(DatosIncorrectos ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja InvalidCredentialsException y devuelve una respuesta 401 Unauthorized.
     *
     * @param ex la InvalidCredentialsException
     * @return un ResponseEntity con el mensaje de error y el estado HTTP
     */
    @ExceptionHandler(UsuarioNoHabilitadoExeption.class)
    public ResponseEntity<String> handleUsuarioNoHabilitado(UsuarioNoHabilitadoExeption ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja EmptyFieldException y devuelve una respuesta 400 Bad Request.
     *
     * @param ex la EmptyFieldException
     * @return un ResponseEntity con el mensaje de error y el estado HTTP
     */
    @ExceptionHandler(DatosIncompletos.class)
    public ResponseEntity<String> handleDatosIncompletosException(DatosIncompletos ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones generales y devuelve una respuesta 500 Internal Server Error.
     *
     * @param ex la excepción general
     * @return un ResponseEntity con el mensaje de error y el estado HTTP
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
