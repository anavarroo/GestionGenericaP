package com.viewnext.CRUD_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CrudServiceApplicationTest {

    @Test
    void testMain() {
        // Arrange & Act
        ConfigurableApplicationContext context = SpringApplication.run(CrudServiceApplication.class);

        // Assert
        assertNotNull(context);
        context.close();
    }
}
