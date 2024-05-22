package com.viewnext.crud_service.controllers;


import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.dto.UserDtoRegister;
import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin
public class UserController {

    private final UserServiceI userServiceI;

    @Autowired
    public UserController(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    /**
     * Crea un nuevo usuario en el sistema utilizando los datos proporcionados en el cuerpo de la solicitud.
     *
     * @param user El objeto User que contiene los datos del nuevo usuario a crear.
     * @return Un objeto ResponseEntity que contiene el nuevo usuario creado y el estado HTTP 201 Created.
     */
    @PostMapping("/crear")
    public ResponseEntity<UserDto> crearUsuario(@RequestBody User user, Authentication authentication) {

        String correo = authentication.getName();

        UserDto nuevoUsuario = userServiceI.crearUsuario(user, correo);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    /**
     * Edita los datos de un usuario.
     *
     * @param correo Correo del usuario.
     * @param userDto Objeto UserDto con la nueva información del usuario.
     * @return ResponseEntity con el objeto UserDto actualizado.
     */
    @PutMapping("/editar/{correo}")
    public ResponseEntity<UserDto> actualizarUsuario(
            @PathVariable String correo,
            @RequestBody UserDto userDto,
            Authentication authentication) {
        String correoAutor = authentication.getName();
        UserDto usuarioActualizado = userServiceI.actualizarUsuario(correo, userDto, correoAutor);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Elimina un usuario de la base de datos utilizando su direccion de correo electrónico como identificador único.
     *
     * @param correo La dirección de correo electrónico del usuario que se va a eliminar.
     */
    @DeleteMapping("borrar/{correo}")
    public void borrarUsuarioPorEmail(@PathVariable String correo, Authentication authentication) {
        String correoAutor = authentication.getName();
        userServiceI.borrarUsuarioPorEmail(correo, correoAutor);
    }

    /**
     * Consultar un usuario por su correo.
     *
     * @param nombre Nombre del usuario.
     * @return ResponseEntity con el objeto UserDto.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<UserDto>> consultarUsuarioPorNombre(
            @PathVariable String nombre, Authentication authentication) {
        String correoAutor = authentication.getName();
        List<UserDto> userDtos = userServiceI.consultarUsuarioPorNombre(nombre, correoAutor);
        return ResponseEntity.ok(userDtos);
    }

    /**
     * Consultar un usuario por su correo.
     *
     * @param apellidos Apellidos del usuario.
     * @return ResponseEntity con el objeto UserDto.
     */
    @GetMapping("/apellidos/{apellidos}")
    public ResponseEntity<List<UserDto>> consultarUsuarioPorApellidos(
            @PathVariable String apellidos, Authentication authentication) {
        String correoAutor = authentication.getName();
        List<UserDto> userDtos = userServiceI.consultarUsuarioPorApellidos(apellidos, correoAutor);
        return ResponseEntity.ok(userDtos);
    }

    /**
     * Consultar un usuario por su correo.
     *
     * @param edad Edad del usuario.
     * @return ResponseEntity con el objeto UserDto.
     */
    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<UserDto>> consultarUsuarioPorEdad(
            @PathVariable int edad, Authentication authentication) {
        String correoAutor = authentication.getName();
        List<UserDto> userDtos = userServiceI.consultarUsuarioPorEdad(edad, correoAutor);
        return ResponseEntity.ok(userDtos);
    }

    /**
     * Consultar un usuario por su correo.
     *
     * @param correo Correo del usuario.
     * @return ResponseEntity con el objeto UserDto.
     */
    @GetMapping("/correo/{correo}")
    public ResponseEntity<List<UserDto>> consultarUsuarioPorCorreo(
            @PathVariable String correo, Authentication authentication) {
        String correoAutor = authentication.getName();
        List<UserDto> userDto = userServiceI.consultarUsuarioPorCorreo(correo, correoAutor);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Consultar un usuario por su correo.
     *
     * @param direccion Direccion del usuario.
     * @return ResponseEntity con el objeto UserDto.
     */
    @GetMapping("/direccion/{direccion}")
    public ResponseEntity<List<UserDto>> consultarUsuarioPorDireccion(
            @PathVariable String direccion, Authentication authentication) {
        String correoAutor = authentication.getName();
        List<UserDto> userDtos = userServiceI.consultarUsuarioPorDireccion(direccion, correoAutor);
        return ResponseEntity.ok(userDtos);
    }

    /**
     * Consultar un usuario por su correo.
     *
     * @param telefono Telefono del usuario.
     * @return ResponseEntity con el objeto UserDto.
     */
    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<List<UserDto>> consultarUsuarioPorTelefono(
            @PathVariable String telefono, Authentication authentication) {
        String correoAutor = authentication.getName();
        List<UserDto> userDto = userServiceI.consultarUsuarioPorTelefono(telefono, correoAutor);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Aprueba el registro de un usuario cambiando el estado a true.
     *
     * @param correo La dirección de correo electrónico del usuario a aprobar.
     * @param estado Estado del usuario a aprobar
     */
    @PutMapping("/aprobar/{correo}")
    public void confirmarUsuario (@PathVariable String correo, @RequestParam boolean estado, Authentication authentication){
        String correoAutor = authentication.getName();
        userServiceI.aprobarRegistro(correo,estado, correoAutor);
    }

    /**
     * Muestra los usuarios con estado = false.
     *
     * @return Lista de DTOs de los usuarios encontrados.
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<UserDtoRegister>> encontrarConEstadoFalse() {
        return ResponseEntity.ok(userServiceI.devolverUsuariosConEstadoFalse());
    }
}
