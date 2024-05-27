package com.viewnext.auditservice.consumer;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.services.AuditingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
/*
    private final AuditingServiceImpl auditingService;

    @Autowired
    public Consumer(AuditingServiceImpl auditingService) {
        this.auditingService = auditingService;
    }

    @RabbitListener(queues = { "${generics.queue.name}" })
    public void receive(@Payload AuditingData message) {
        log.info("Received message: {}", message);
        auditingService.saveAudit(message);

    }

    private void makeSlow() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

 */
}
