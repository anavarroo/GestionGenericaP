package com.viewnext.userauditservice.persistence.repository;

import com.viewnext.crud_service.persistence.model.User;

public interface AuditingRepositoryI {

    /**
     * Busca un usuario por su correo.
     *
     * @param correo Nombre de usuario a buscar.
     * @return Usuario encontrado.
     */
    User findByCorreo(String correo);

}
