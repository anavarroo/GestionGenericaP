package com.viewnext.auditservice.consumer;

import com.viewnext.common.model.AuditingData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(AuditingData auditingData) {
        LOGGER.info(String.format("Recieved message -> "), auditingData);
    }

}
