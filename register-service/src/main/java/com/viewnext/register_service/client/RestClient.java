package com.viewnext.register_service.client;

import com.viewnext.register_service.persistence.dto.AuditingDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {

    private final RestTemplate restTemplate;
    private final String auditingServiceUrl;

    public RestClient(RestTemplate restTemplate, @Value("${auditing.service.url}") String auditingServiceUrl) {
        this.restTemplate = restTemplate;
        this.auditingServiceUrl = auditingServiceUrl;
    }

    public void sendAudit(AuditingDataDto audit) {
        restTemplate.postForObject(auditingServiceUrl, audit, AuditingDataDto.class);
    }

}
