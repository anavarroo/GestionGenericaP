package com.viewnext.auditservice.consumer;

import com.viewnext.auditservice.persistence.model.AuditingData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumJsonMessage(String auditingData) {

        LOGGER.info(String.format("Received JSON message -> %s", auditingData.toString()));

    }

}
