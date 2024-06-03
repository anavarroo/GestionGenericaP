package com.viewnext.crud_service.services;


import com.viewnext.crud_service.exceptions.DatosIncompletos;
import com.viewnext.crud_service.exceptions.UsuarioNoEncontrado;
import com.viewnext.crud_service.persistence.model.AuditingData;
import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.dto.UserDtoRegister;
import com.viewnext.crud_service.persistence.model.ExceptionHandler;
import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import com.viewnext.crud_service.published.RabbitMQExceptionProducer;
import com.viewnext.crud_service.published.RabbitMQJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImp implements UserServiceI {

    private final UserRepositoryI userRepositoryI;

    private final RabbitMQJsonProducer rabbitMQProducer;

    private final RabbitMQExceptionProducer rabbitMQExceptionProducer;

    @Autowired
    public UserServiceImp(UserRepositoryI userRepositoryI,
                          RabbitMQJsonProducer rabbitMQProducer,
                          RabbitMQExceptionProducer rabbitMQExceptionProducer) {
        this.userRepositoryI = userRepositoryI;
        this.rabbitMQProducer = rabbitMQProducer;
        this.rabbitMQExceptionProducer = rabbitMQExceptionProducer;
    }

    /**
     * Crea un nuevo usuario en el sistema, encriptando la contraseña antes de guardarla en la base de datos.
     *
     * @param user El objeto User que representa al nuevo usuario a crear.
     * @return El objeto User creado y guardado en la base de datos.
     */
    @Override
    public UserDto crearUsuario(User user, String correoAutor) {

        if (user.getCorreo().isEmpty() || user.getCorreo().isBlank()
        || user.getNombre().isEmpty() || user.getNombre().isBlank()
        || user.getApellidos().isEmpty() || user.getApellidos().isBlank()
        || user.getDireccion().isEmpty() || user.getDireccion().isBlank()
        || user.getTelefono().isEmpty() || user.getTelefono().isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/crear/" + user.getCorreo());
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        String contrasenaSinEncriptar = user.getContrasena();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = encoder.encode(contrasenaSinEncriptar);

        user.setContrasena(contrasenaEncriptada);

        userRepositoryI.save(user);

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/crear/" + user.getCorreo());

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return convertToDto(user);
    }

    /**
     * Actualiza la informacion de un usuario y devuelve su DTO actualizado.
     *
     * @param correo Correo del usuario cuya informacion se actualizará.
     * @param userDto DTO del usuario con la nueva informacion.
     * @return DTO del usuario actualizado.
     */
    @Override
    public UserDto actualizarUsuario(String correo, UserDto userDto, String correoAutor) {

        if (userDto.getCorreo().isEmpty() || userDto.getCorreo().isBlank()
                || userDto.getNombre().isEmpty() || userDto.getNombre().isBlank()
                || userDto.getApellidos().isEmpty() || userDto.getApellidos().isBlank()
                || userDto.getDireccion().isEmpty() || userDto.getDireccion().isBlank()
                || userDto.getTelefono().isEmpty() || userDto.getTelefono().isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/editar/" + userDto.getCorreo());
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        if (!userRepositoryI.existsByCorreo(correo)) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/editar/" + correo);
            exceptionHandler.setMessage("El usuario no esta registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("El usuario no esta registrado");
        }

        User user = userRepositoryI.findByCorreo(correo);

        user.setNombre(userDto.getNombre());
        user.setApellidos(userDto.getApellidos());
        user.setEdad(userDto.getEdad());
        user.setDireccion(userDto.getDireccion());
        user.setTelefono(userDto.getTelefono());
        user.setEditionDate(LocalDateTime.now());

        User usuarioActualizado = userRepositoryI.save(user);

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/editar/" + user.getCorreo());

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return convertToDto(usuarioActualizado);
    }


    /**
     * Elimina un usuario de la base de datos utilizando su dirección de
     * correo electrónico como identificador único.
     *
     * @param correo La dirección de correo electrónico del usuario que
     *               se va a eliminar.
     * @throws UsernameNotFoundException Si no se encuentra ningún usuario con
     *              la dirección de correo electrónico proporcionada.
     */
    @Override
    public void borrarUsuarioPorEmail(String correo, String correoAutor) {
        if (correo.isEmpty() || correo.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/borrar/" + correo);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        if (!userRepositoryI.existsByCorreo(correo)) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/borrar/" + correo);
            exceptionHandler.setMessage("El usuario no esta registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("El usuario no esta registrado");
        }

        User user = userRepositoryI.findByCorreo(correo);

        userRepositoryI.delete(user);

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/borrar/" + user.getCorreo());

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

    }

    /**
     * Aprueba el registro de un usuario cambiando el estado a true.
     *
     * @param correo La dirección de correo electrónico del usuario a aprobar.
     * @param estado Estado del usuario a aprobar
     */
    @Override
    public void aprobarRegistro(String correo, boolean estado, String correoAutor) {
        if (correo.isEmpty() || correo.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/aprobar/" + correo);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        if (!userRepositoryI.existsByCorreo(correo)) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/aprobar/" + correo);
            exceptionHandler.setMessage("El usuario no esta registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("El usuario no esta registrado");
        }

        User usuarioMod = userRepositoryI.findByCorreo(correo);
        usuarioMod.setEstado(estado);
        userRepositoryI.save(usuarioMod);

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/editar/" + usuarioMod.getCorreo());

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

    }

    /**
     * Muestra los usuarios con estado = false.
     *
     * @return Lista de DTOs de los usuarios encontrados.
     */
    @Override
    public List<UserDtoRegister> devolverUsuariosConEstadoFalse() {

        List<User> users = userRepositoryI.findByEstadoFalse();
        List<UserDtoRegister> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDtoRegister(user));
        }

        return userDtos;
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param nombre Nombre del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public List<UserDto> consultarUsuarioPorNombre(String nombre, String correoAutor) {

        if (nombre.isEmpty() || nombre.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/nombre/" + nombre);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        List<User> users = userRepositoryI.findByNombre(nombre);

        if (users.isEmpty()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/nombre/" + nombre);
            exceptionHandler.setMessage("No se han encontrado usuarios registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("No se han encontrado usuarios registrado");
        }

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/nombre/" + nombre);

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return userDtos;
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param apellidos Apellidos del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public List<UserDto> consultarUsuarioPorApellidos(String apellidos, String correoAutor) {

        if (apellidos.isEmpty() || apellidos.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/apellidos/" + apellidos);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        List<User> users = userRepositoryI.findByApellidos(apellidos);

        if (users.isEmpty()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/apellidos/" + apellidos);
            exceptionHandler.setMessage("No se han encontrado usuarios registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("No se han encontrado usuarios registrado");
        }

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }
        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/apellidos/" + apellidos);

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return userDtos;
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param edad Edad del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public List<UserDto> consultarUsuarioPorEdad(int edad, String correoAutor) {

        List<User> users = userRepositoryI.findByEdad(edad);

        if (users.isEmpty()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/edad/" + edad);
            exceptionHandler.setMessage("No se han encontrado usuarios registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("No se han encontrado usuarios registrado");
        }

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }
        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/edad/" + edad);

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return userDtos;
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param correo Correo del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public List<UserDto> consultarUsuarioPorCorreo(String correo, String correoAutor) {

        if (correo.isEmpty() || correo.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/correo/" + correo);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        if (!userRepositoryI.existsByCorreo(correo)) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/correo/" + correo);
            exceptionHandler.setMessage("No se han encontrado usuarios registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("No se han encontrado usuarios registrado");
        }

        User user = userRepositoryI.findByCorreo(correo);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(convertToDto(user));

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/correo/" + correo);

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return userDtos;
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param direccion Direccion del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public List<UserDto> consultarUsuarioPorDireccion(String direccion, String correoAutor) {

        if (direccion.isEmpty() || direccion.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/direccion/" + direccion);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        List<User> users = userRepositoryI.findByDireccion(direccion);

        if (users.isEmpty()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/direccion/" + direccion);
            exceptionHandler.setMessage("No se han encontrado usuarios registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("No se han encontrado usuarios registrado");
        }

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/direccion/" + direccion);

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return userDtos;
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param telefono Telefono del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public List<UserDto> consultarUsuarioPorTelefono(String telefono, String correoAutor) {

        if (telefono.isEmpty() || telefono.isBlank()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/telefono/" + telefono);
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        User user = userRepositoryI.findByTelefono(telefono);

        if (!userRepositoryI.existsByTelefono(telefono)) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(correoAutor);
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("api/v1/usuarios/telefono/" + telefono);
            exceptionHandler.setMessage("No se han encontrado usuarios registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("No se han encontrado usuarios registrado");
        }

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(convertToDto(user));

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/telefono/" + telefono);

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);
        return userDtos;
    }

    /**
     * Convierte un objeto User en un objeto UserDto.
     *
     * @param user Objeto User a convertir.
     * @return Objeto UserDto convertido.
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setNombre(user.getNombre());
        userDto.setApellidos(user.getApellidos());
        userDto.setCorreo(user.getCorreo());
        userDto.setEdad(user.getEdad());
        userDto.setDireccion(user.getDireccion());
        userDto.setTelefono(user.getTelefono());
        return userDto;
    }

    /**
     * Convierte un objeto User en un objeto UserDtoRegister.
     *
     * @param user Objeto User a convertir.
     * @return Objeto UserDtoRegister convertido.
     */
    private UserDtoRegister convertToDtoRegister(User user) {
        UserDtoRegister userDto = new UserDtoRegister();
        userDto.setNombre(user.getNombre());
        userDto.setApellidos(user.getApellidos());
        userDto.setCorreo(user.getCorreo());

        return userDto;
    }
}
