package com.viewnext.crud_service.published;

import com.viewnext.crud_service.persistence.model.AuditingData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {

    @Value("${exchange_generics_audit}")
    private String exchange;

    @Value("${routing_json_key_generics_audit}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(AuditingData auditingData) {
        LOGGER.info(String.format("Json message sent -> %s", auditingData.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, auditingData);
    }
}
