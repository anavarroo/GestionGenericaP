package com.viewnext.auditservice.controllers;

import com.viewnext.auditservice.persistence.model.AuditingData;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.viewnext.auditservice.config.RabbitMQConfig.AUDIT_QUEUE;

@RestController
@RequestMapping("/api/audit")
public class AuditingController {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public AuditingController(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @PostMapping
    public ResponseEntity<AuditingData> createAudit(@RequestBody AuditingData audit) {
        amqpTemplate.convertAndSend(AUDIT_QUEUE, audit);
        return new ResponseEntity<>(audit, HttpStatus.CREATED);
    }
}
