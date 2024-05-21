package com.viewnext.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Component
public class AuditFilter extends AbstractGatewayFilterFactory<AuditFilter.Config> {

    private static final Logger logger = Logger.getLogger(AuditFilter.class.getName());
    private static final String LOG_FILE_PATH = "C:\\Desarrollo\\GestionGenericaP\\api-gateway\\api_gateway_audit.log"; // Ruta absoluta del archivo de log
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AuditFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Log the request details
            logRequest(exchange);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Log the response details
                logResponse(exchange);
            }));
        };
    }

    private void logRequest(ServerWebExchange exchange) {
        HttpMethod method = exchange.getRequest().getMethod();
        String path = exchange.getRequest().getURI().getPath();
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String timestamp = LocalDateTime.now().format(formatter);
        String log = String.format("Request - Time: %s, Method: %s, Path: %s, Headers: %s", timestamp, method, path, headers);
        writeLog(log);
    }

    private void logResponse(ServerWebExchange exchange) {
        int statusCode = exchange.getResponse().getStatusCode().value();
        HttpHeaders headers = exchange.getResponse().getHeaders();
        String timestamp = LocalDateTime.now().format(formatter);
        String log = String.format("Response - Time: %s, Status Code: %d, Headers: %s", timestamp, statusCode, headers);
        writeLog(log);
    }

    private void writeLog(String log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            logger.severe("Failed to write log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class Config {
        // Configuration properties if needed
    }
}
