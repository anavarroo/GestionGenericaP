package com.viewnext.crud_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    @Value("${rabbitmq.queue.exception.name}")
    private String exceptionQueue;

    @Value("${rabbitmq.exchange.exception.name}")
    private String exceptionExchange;

    @Value("${rabbitmq.routing.exception.key}")
    private String routingexceptionKey;

    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

    @Bean
    public Queue exceptionQueue() {
        return new Queue(exceptionQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public TopicExchange exceptionExchange() {
        return new TopicExchange(exceptionExchange);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

    @Bean
    public Binding exceptionBinding() {
        return BindingBuilder
                .bind(exceptionQueue())
                .to(exceptionExchange())
                .with(routingexceptionKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
