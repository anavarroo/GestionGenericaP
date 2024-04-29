package com.viewnext.register_service.persistence.repository;

import com.viewnext.register_service.persistence.model.User;
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
    User findByCorreo(String correo);
}
