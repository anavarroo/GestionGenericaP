package com.viewnext.crud_service.auditing.services;

import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorServiceImpl implements AuditorServiceI<String>{

    @Autowired
    private UserRepositoryI auditingRepo;

    @Override
    public Optional<String> getCurrentAuditor(Authentication authentication) {
        String correo = authentication.getName();
        User user = auditingRepo.findByCorreo(correo);
        return Optional.of(user.getNombre() + " " + user.getApellidos() + "\n" + user.getCorreo());
    }
}
