package com.viewnext.crud_service.services;



import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.dto.UserDtoRegister;
import com.viewnext.crud_service.persistence.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserServiceI {

    /**
     * Muestra un usuario segun su correo.
     *
     * @param nombre Nombre del usuario que se busca.
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDto> consultarUsuarioPorNombre(String nombre, String correoAutor);

    /**
     * Muestra un usuario segun su correo.
     *
     * @param apellidos Apellidos del usuario que se busca.
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDto> consultarUsuarioPorApellidos(String apellidos, String correoAutor);

    /**
     * Muestra un usuario segun su correo.
     *
     * @param edad Edad del usuario que se busca.
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDto> consultarUsuarioPorEdad(int edad, String correoAutor);

    /**
     * Muestra un usuario segun su correo.
     *
     * @param correo Correo del usuario que se busca.
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDto> consultarUsuarioPorCorreo(String correo, String correoAutor);

    /**
     * Muestra un usuario segun su correo.
     *
     * @param direccion Direccion del usuario que se busca.
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDto> consultarUsuarioPorDireccion(String direccion, String correoAutor);

    /**
     * Muestra un usuario segun su correo.
     *
     * @param telefono Telefono del usuario que se busca.
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDto> consultarUsuarioPorTelefono(String telefono, String correoAutor);

    /**
     * Actualiza la descripción de un usuario.
     *
     * @param correo Correo del usuario a actualizar.
     * @param userDto DTO del usuario con la nueva información.
     * @return DTO del usuario actualizado.
     */
    UserDto actualizarUsuario(String correo, UserDto userDto, String correoAutor);

    /**
     * Crea un nuevo usuario en el sistema, encriptando la contraseña antes de guardarla en la base de datos.
     *
     * @param user El objeto User que representa al nuevo usuario a crear.
     * @return El objeto User creado y guardado en la base de datos.
     */
    UserDto crearUsuario(User user, String correoAutor);

    /**
     * Elimina un usuario de la base de datos por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a eliminar.
     */
    void borrarUsuarioPorEmail(String email, String correoAutor);

    /**
     * Muestra los usuarios con estado = false.
     *
     * @return Lista de DTOs de los usuarios encontrados.
     */
    List<UserDtoRegister> devolverUsuariosConEstadoFalse();

    /**
     * Aprueba el registro de un usuario cambiando el estado a true.
     *
     * @param correo La dirección de correo electrónico del usuario a aprobar.
     * @param estado Estado del usuario a aprobar
     */
    void aprobarRegistro(String correo, boolean estado, String correoAutor);
}
