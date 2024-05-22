package com.viewnext.register_service.services;

import com.viewnext.register_service.persistence.model.AuditingData;
import com.viewnext.register_service.persistence.model.User;
import com.viewnext.register_service.persistence.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorServiceImpl implements AuditorServiceI<String> {

    @Autowired
    private UserRepositoryI auditingRepo;

    @Override
    public Optional<String> getCurrentAuditor(Authentication authentication) {
        String correo = authentication.getName();
        User user = auditingRepo.findByCorreo(correo);
        return Optional.of(user.getNombre() + " " + user.getApellidos());
    }
}
