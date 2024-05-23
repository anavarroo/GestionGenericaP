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
import java.util.UUID;

@Aspect
@Component
public class RequestMetricsAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestMetricsAspect.class);

    @Around("execution(* org.springframework.cloud.gateway.handler.FilteringWebHandler.handle(..))")
    public Object measureRequestProcessingTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String traceId = UUID.randomUUID().toString();

        ServerWebExchange exchange = (ServerWebExchange) joinPoint.getArgs()[0];
        String method = Objects.requireNonNull(exchange.getRequest().getMethod()).name();
        String uri = Objects.requireNonNull(exchange.getRequest().getPath()).value();
        String contentType = exchange.getRequest().getHeaders().getFirst("Content-Type");
        String userAgent = exchange.getRequest().getHeaders().getFirst("User-Agent");
        String clientIp = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            HttpStatus httpStatus = (HttpStatus) exchange.getResponse().getStatusCode();
            int statusCode = httpStatus != null ? httpStatus.value() : -1;

            logger.info("Request: {} {} [Content-Type: {}, User-Agent: {}] - Fecha y hora: {} - Duracion: {} ms - Estado: Saliente - Codigo de respuesta: {} - ID de trace: {} - IP del cliente: {}",
                    method, uri, contentType, userAgent, dateTime.format(formatter), duration, statusCode, traceId, clientIp);
        }

        return result;
    }
}
