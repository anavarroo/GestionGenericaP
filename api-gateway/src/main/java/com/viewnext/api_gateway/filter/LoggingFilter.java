package com.viewnext.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.viewnext.api_gateway.model.RequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            RequestLog log = new RequestLog();
            log.setMethod(request.getMethod().name());
            log.setUrl(request.getURI().toString());
            log.setTimestamp(LocalDateTime.now());

            // Optional: log the request body if needed
            return exchange.getRequest().getBody().collectList().flatMap(body -> {
                if (!body.isEmpty()) {
                    // Assuming the body can be converted to a string for logging purposes
                    StringBuilder sb = new StringBuilder();
                    body.forEach(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        sb.append(new String(bytes));
                    });
                    log.setBody(sb.toString());
                }
                // Log the request
                logger.info("Request: {}", log);
                return chain.filter(exchange);
            });
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
}