package com.viewnext.crud_service.services;

import com.viewnext.crud_service.persistence.model.User;
import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorServiceImpl implements AuditorAware<String> {

    @Autowired
    private UserRepositoryI userRepo;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String correo = authentication.getName();
        User user = userRepo.findByCorreo(correo);
        return Optional.of(user.getNombre() + " " + user.getApellidos());
    }

}
