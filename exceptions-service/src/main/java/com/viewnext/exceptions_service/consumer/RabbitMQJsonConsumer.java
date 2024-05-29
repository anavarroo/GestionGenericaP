package com.viewnext.exceptions_service.consumer;

import com.viewnext.exceptions_service.persistence.model.ExceptionHandler;
import com.viewnext.exceptions_service.services.ExceptionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private final ExceptionServiceImpl exceptionMngm;

    @Autowired
    public RabbitMQJsonConsumer(ExceptionServiceImpl exceptionMngm) {
        this.exceptionMngm = exceptionMngm;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.exception.name=generics_login_exception}"})
    public void consumJsonMessage(@Payload String data) {

        String[] dataException = data.split(",");

        ExceptionHandler exception = new ExceptionHandler();
        exception.setCreatedBy(dataException[1]);
        exception.setCreatedDate(LocalDate.parse(dataException[2]));
        exception.setTypeRequest(dataException[3]);
        exception.setMessage(dataException[4]);

        exceptionMngm.saveException(exception);
    }

}
