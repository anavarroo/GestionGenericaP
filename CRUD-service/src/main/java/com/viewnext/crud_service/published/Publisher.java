package com.viewnext.crud_service.published;

import com.viewnext.crud_service.persistence.dto.AuditingDataDto;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class Publisher {

    private final RabbitTemplate template;
    private final Queue queue;

    public Publisher(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void sendAudit(AuditingDataDto message) {
        template.convertAndSend(queue.getName(), message);
    }

}
