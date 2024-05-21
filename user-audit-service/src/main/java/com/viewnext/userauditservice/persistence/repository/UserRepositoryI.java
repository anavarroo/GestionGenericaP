package com.viewnext.userauditservice.persistence.repository;

import com.viewnext.userauditservice.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryI extends JpaRepository<User, Long>{

    /**
     * Busca un usuario por su nombre.
     *
     * @param nombre Nombre de usuario a buscar.
     * @return Lista de usuarios encontrados.
     */
    List<User> findByNombre(String nombre);

    /**
     * Busca un usuario por sus apellidos.
     *
     * @param apellidos Apellidos de usuario a buscar.
     * @return Lista de usuarios encontrados.
     */
    List<User> findByApellidos(String apellidos);

    /**
     * Busca un usuario por su edad.
     *
     * @param edad Edad de usuario a buscar.
     * @return Lista de usuarios encontrados.
     */
    List<User> findByEdad(int edad);

    /**
     * Busca un usuario por su correo.
     *
     * @param correo Nombre de usuario a buscar.
     * @return Usuario encontrado.
     */
    User findByCorreo(String correo);

    /**
     * Busca un usuario por su direccion.
     *
     * @param direccion Direccion de usuario a buscar.
     * @return Lista de usuarios encontrados.
     */
    List<User> findByDireccion(String direccion);

    /**
     * Busca un usuario por su telefono.
     *
     * @param telefono Telefono de usuario a buscar.
     * @return Usuario encontrado.
     */
    User findByTelefono(String telefono);

    /**
     * Busca un usuario por su estado.
     *
     * @return Usuarios encontrados con estado = true.
     */
    List<User> findByEstadoFalse();
}
