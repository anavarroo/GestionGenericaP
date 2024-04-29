package com.viewnext.CRUD_service.services;


import com.viewnext.CRUD_service.persistence.dto.UserDto;
import com.viewnext.CRUD_service.persistence.model.User;
import com.viewnext.CRUD_service.persistence.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserServiceI {

    private final UserRepositoryI userRepositoryI;

    @Autowired
    public UserServiceImp(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }


    /**
     * Crea un nuevo usuario en el sistema, encriptando la contraseña antes de guardarla en la base de datos.
     *
     * @param user El objeto User que representa al nuevo usuario a crear.
     * @return El objeto User creado y guardado en la base de datos.
     */
    @Override
    public User crearUsuario(User user) {
        String contrasenaSinEncriptar = user.getContrasena();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = encoder.encode(contrasenaSinEncriptar);

        user.setContrasena(contrasenaEncriptada);

        return userRepositoryI.save(user);
    }

    /**
     * Actualiza la informacion de un usuario y devuelve su DTO actualizado.
     *
     * @param correo Correo del usuario cuya informacion se actualizará.
     * @param userDto DTO del usuario con la nueva informacion.
     * @return DTO del usuario actualizado.
     */
    @Override
    public UserDto actualizarUsuario(String correo, UserDto userDto) {
        User user = userRepositoryI.findByCorreo(correo);

        user.setNombre(userDto.getNombre());
        user.setApellidos(userDto.getApellidos());
        user.setEdad(userDto.getEdad());
        user.setDireccion(userDto.getDireccion());
        user.setTelefono(userDto.getTelefono());

        User usuarioActualizado = userRepositoryI.save(user);

        return convertToDto(usuarioActualizado);
    }


    /**
     * Elimina un usuario de la base de datos utilizando su dirección de correo electrónico como identificador único.
     *
     * @param correo La dirección de correo electrónico del usuario que se va a eliminar.
     * @throws UsernameNotFoundException Si no se encuentra ningún usuario con la dirección de correo electrónico proporcionada.
     */
    @Override
    public void borrarUsuarioPorEmail(String correo) {
        User user = userRepositoryI.findByCorreo(correo);
        if (user != null) {
            userRepositoryI.delete(user);
        } else {
            throw new UsernameNotFoundException("No se encontró ningún usuario con el correo electrónico proporcionado: " + correo);
        }
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param nombre Nombre del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public UserDto consultarUsuarioPorNombre(String nombre) {
        User user = userRepositoryI.findByNombre(nombre);

        return convertToDto(user);
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param apellidos Apellidos del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public UserDto consultarUsuarioPorApellidos(String apellidos) {
        User user = userRepositoryI.findByApellidos(apellidos);

        return convertToDto(user);
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param edad Edad del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public UserDto consultarUsuarioPorEdad(int edad) {
        User user = userRepositoryI.findByEdad(edad);

        return convertToDto(user);
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param correo Correo del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public UserDto consultarUsuarioPorCorreo(String correo) {
        User user = userRepositoryI.findByCorreo(correo);

        return convertToDto(user);
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param direccion Direccion del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public UserDto consultarUsuarioPorDireccion(String direccion) {
        User user = userRepositoryI.findByDireccion(direccion);

        return convertToDto(user);
    }

    /**
     * Encuentra un usuario por su correo y devuelve su DTO.
     *
     * @param telefono Telefono del usuario a buscar.
     * @return DTO del usuario encontrado.
     */
    @Override
    public UserDto consultarUsuarioPorTelefono(int telefono) {
        User user = userRepositoryI.findByTelefono(telefono);

        return convertToDto(user);
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
}
