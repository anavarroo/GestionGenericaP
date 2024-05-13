package com.viewnext.register_service.register_service.persistence.model.persistence.model.register_service.persistence.model.persistence.repository;

import com.viewnext.register_service.register_service.persistence.model.persistence.model.register_service.persistence.model.persistence.model.User;
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
