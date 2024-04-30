package com.viewnext.CRUD_service.persistence.repository;

import com.viewnext.CRUD_service.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryI extends JpaRepository<User, Long>{

    /**
     * Busca un usuario por su correo.
     *
     * @param correo Correo de usuario a buscar.
     * @return Usuario encontrado.
     */

    User findByNombre(String correo);

    User findByApellidos(String correo);

    User findByEdad(int edad);

    User findByCorreo(String correo);

    User findByDireccion(String correo);

    User findByTelefono(int telefono);
}
