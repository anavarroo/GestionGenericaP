package com.viewnext.crud_service.auditing.services;

import org.springframework.security.core.Authentication;
import java.util.Optional;

public interface AuditorServiceI<String> {

    public Optional<String> getCurrentAuditor(Authentication authentication);

}
