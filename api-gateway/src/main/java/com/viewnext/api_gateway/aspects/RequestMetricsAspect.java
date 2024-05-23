package com.viewnext.api_gateway.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Aspect
@Component
public class RequestMetricsAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestMetricsAspect.class);

    @Around("execution(* org.springframework.cloud.gateway.handler.FilteringWebHandler.handle(..))")
    public Object measureRequestProcessingTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Obtener la fecha y hora actuales
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = requestDateTime.format(formatter);

        ServerWebExchange exchange = (ServerWebExchange) joinPoint.getArgs()[0];
        String method = Objects.requireNonNull(exchange.getRequest().getMethod()).name();
        String uri = Objects.requireNonNull(exchange.getRequest().getPath()).value();
        String contentType = exchange.getRequest().getHeaders().getFirst("Content-Type");

        // Obtener la dirección IP del cliente
        String clientIp = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();

        // Determinar el estado de la solicitud (entrante o saliente)
        String requestState = (clientIp != null) ? "Saliente" : "Entrante";

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            HttpStatus httpStatus = (HttpStatus) exchange.getResponse().getStatusCode();
            int statusCode = httpStatus != null ? httpStatus.value() : -1;

            logger.info("Request: {} {} [{}] - Fecha y hora: {} - Duracion: {} ms - Estado: {} - Codigo de respuesta: {}",
                    method, uri, contentType, formattedDateTime, duration, requestState, statusCode);
        }

        return result;
    }
}
