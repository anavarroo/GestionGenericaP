package com.viewnext.crud_service.published;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.crud_service.persistence.dto.AuditingDataDto;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class Publisher {

    private final RabbitTemplate template;
    private final Queue queue;

    @Autowired
    public Publisher(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void sendAudit(AuditingData message) {
        template.convertAndSend(queue.getName(), message);
    }

}
