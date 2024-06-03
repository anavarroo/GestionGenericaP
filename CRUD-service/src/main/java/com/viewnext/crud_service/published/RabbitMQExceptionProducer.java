package com.viewnext.crud_service.published;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQExceptionProducer {

    @Value("${rabbitmq.exchange.exception.name}")
    private String exchange;

    @Value("${rabbitmq.routing.exception.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQExceptionProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQExceptionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(String data) {
        LOGGER.info(String.format("Json message sent -> %s", data));

        rabbitTemplate.convertAndSend(exchange, routingJsonKey, data);
    }

}
