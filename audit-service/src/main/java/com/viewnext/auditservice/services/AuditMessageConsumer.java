package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.persistence.repository.AuditorRepositoryI;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditMessageConsumer {

    private final AuditorRepositoryI auditorRepo;

    @Autowired
    public AuditMessageConsumer(AuditorRepositoryI auditorRepo) {
        this.auditorRepo = auditorRepo;
    }

    @RabbitListener(queues = "auditQueue")
    public void receiveMessage(AuditingData audit) {
        auditorRepo.save(audit);
    }
}
