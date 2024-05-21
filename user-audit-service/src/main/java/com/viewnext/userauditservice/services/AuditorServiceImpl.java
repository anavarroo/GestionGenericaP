package com.viewnext.userauditservice.services;

import com.viewnext.commonservices.model.User;
import com.viewnext.userauditservice.persistence.repository.AuditingRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorServiceImpl implements AuditorServiceI<String>{

    @Autowired
    private AuditingRepositoryI auditingRepo;

    @Override
    public Optional<String> getCurrentAuditor(Authentication authentication) {
        String correo = authentication.getName();
        User user = auditingRepo.findByCorreo(correo);
        return Optional.of(user.getNombre() + " " + user.getApellidos() + "\n" + user.getCorreo());
    }
}
