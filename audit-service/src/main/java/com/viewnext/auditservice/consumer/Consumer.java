package com.viewnext.auditservice.consumer;

import com.viewnext.auditservice.persistence.model.AuditingData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = { "${generics.queue.name} "})
    public void receive(@Payload AuditingData message) {
        log.info("Received message: " + message);

    }

    private void makeSlow() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
