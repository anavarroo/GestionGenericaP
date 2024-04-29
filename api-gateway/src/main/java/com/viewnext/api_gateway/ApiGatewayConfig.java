package com.viewnext.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ApiGatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("register_route", r -> r
                        .path("/auth/**") // Ruta del controlador JugadorController
                        .uri("lb://register-service")) // URL base de la aplicación donde se encuentra el JugadorController
                .route("CRUD_route", r -> r
                        .path("/api/v1/usuarios/**")
                        .uri("lb://CRUD-service")) // URL base de la aplicación donde se encuentra el controlador PartidoController
                .build();
    }

}
