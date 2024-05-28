package com.viewnext.crud_service.services;


import com.viewnext.crud_service.persistence.model.AuditingData;
import com.viewnext.crud_service.published.RabbitMQProducer;
import com.viewnext.crud_service.persistence.dto.UserDto;
import com.viewnext.crud_service.persistence.dto.UserDtoRegister;
import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImp implements UserServiceI {

    private final UserRepositoryI userRepositoryI;

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public UserServiceImp(UserRepositoryI userRepositoryI, RabbitMQProducer rabbitMQProducer) {
        this.userRepositoryI = userRepositoryI;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    /**
     * Crea un nuevo usuario en el sistema, encriptando la contraseña antes de guardarla en la base de datos.
     *
     * @param user El objeto User que representa al nuevo usuario a crear.
     * @return El objeto User creado y guardado en la base de datos.
     */
    @Override
    public UserDto crearUsuario(User user, String correoAutor) {
        String contrasenaSinEncriptar = user.getContrasena();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = encoder.encode(contrasenaSinEncriptar);

        user.setContrasena(contrasenaEncriptada);

        userRepositoryI.save(user);

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/crear/" + user.getCorreo());

        rabbitMQProducer.sendMessage(auditingData);

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

        rabbitMQProducer.sendMessage(auditingData);

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
        User user = userRepositoryI.findByCorreo(correo);
        if (user != null) {
            userRepositoryI.delete(user);
        } else {
            throw new UsernameNotFoundException("No se encontró ningún usuario con el correo electrónico proporcionado: " + correo);
        }
        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/borrar/" + user.getCorreo());

        rabbitMQProducer.sendMessage(auditingData);

    }

    /**
     * Aprueba el registro de un usuario cambiando el estado a true.
     *
     * @param correo La dirección de correo electrónico del usuario a aprobar.
     * @param estado Estado del usuario a aprobar
     */
    @Override
    public void aprobarRegistro(String correo, boolean estado, String correoAutor) {
        User usuarioMod = userRepositoryI.findByCorreo(correo);
        usuarioMod.setEstado(estado);
        userRepositoryI.save(usuarioMod);

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/editar/" + usuarioMod.getCorreo());

        rabbitMQProducer.sendMessage(auditingData);

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
        List<User> users = userRepositoryI.findByNombre(nombre);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/nombre/" + nombre);

        rabbitMQProducer.sendMessage(auditingData);

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
        List<User> users = userRepositoryI.findByApellidos(apellidos);

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }
        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/apellidos/" + apellidos);

        rabbitMQProducer.sendMessage(auditingData);

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

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }
        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/edad/" + edad);

        rabbitMQProducer.sendMessage(auditingData);

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
        User user = userRepositoryI.findByCorreo(correo);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(convertToDto(user));

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/correo/" + correo);

        rabbitMQProducer.sendMessage(auditingData);

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
        List<User> users = userRepositoryI.findByDireccion(direccion);

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/direccion/" + direccion);

        rabbitMQProducer.sendMessage(auditingData);

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
        User user = userRepositoryI.findByTelefono(telefono);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(convertToDto(user));

        User author = userRepositoryI.findByCorreo(correoAutor);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(author.getCorreo());
        auditingData.setTypeRequest("/api/v1/usuarios/telefono/" + telefono);

        rabbitMQProducer.sendMessage(auditingData);
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
