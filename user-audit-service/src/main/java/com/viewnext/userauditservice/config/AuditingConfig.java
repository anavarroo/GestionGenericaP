package com.viewnext.userauditservice.config;

import com.viewnext.userauditservice.services.AuditorServiceI;
import com.viewnext.userauditservice.services.AuditorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorServiceI<String> currentStudioProvider() {
        return new AuditorServiceImpl();
    }

}
