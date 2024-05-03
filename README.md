# Entorno

## 1. Descripción de los Microservicios

### API Gateway
- **Artefacto:** api-gateway
- **Descripción:** API Gateway de los Servicios.
- **Versión:** 0.0.1-SNAPSHOT
- **Dependencias:**
  - Spring Cloud Starter Gateway
  - Spring Boot DevTools
  - Spring Boot Configuration Processor
  - Project Lombok
  - Spring Boot Starter Test
  - Spring Cloud Starter Netflix Eureka Client
  - jjwt-api
  - jjwt-impl
  - jjwt-jackson
  - Spring Boot Starter Webflux

### Eureka Server (Discovery Server)
- **Artefacto:** discovery-server
- **Descripción:** Servidor de descubrimiento de servicios (Eureka Server).
- **Versión:** 0.0.1-SNAPSHOT
- **Dependencias:**
  - Spring Cloud Starter Netflix Eureka Server
  - Spring Boot DevTools
  - Spring Boot Configuration Processor
  - Project Lombok
  - Spring Boot Starter Test

### CRUD Service
- **Artefacto:** CRUD-service
- **Descripción:** Microservicio de CRUD de usuarios.
- **Versión:** 0.0.1-SNAPSHOT
- **Dependencias:**
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Validation
  - Spring Boot Starter Web
  - Spring Boot DevTools
  - MySQL Connector J
  - Project Lombok
  - Spring Boot Starter Test
  - Spring Security Test
  - Spring Cloud Starter Netflix Eureka Client
  - jjwt-api
  - jjwt-impl
  - jjwt-jackson
  - jacoco-maven-plugin

### Register Service
- **Artefacto:** register-service
- **Descripción:** Microservicio de registro y login.
- **Versión:** 0.0.1-SNAPSHOT
- **Dependencias:**
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Validation
  - Spring Boot Starter Web
  - Spring Boot DevTools
  - MySQL Connector J
  - Project Lombok
  - Spring Boot Starter Test
  - Spring Security Test
  - Spring Cloud Starter Netflix Eureka Client
  - jjwt-api
  - jjwt-impl
  - jjwt-jackson
  - dev.samstevens.totp
  - jacoco-maven-plugin

## 2. Requisitos del Sistema

- **Java:** Se requiere Java 17 o superior.
- **Maven:** Se requiere Maven para la gestión de dependencias y la compilación del proyecto.
- **MySQL:** Se necesita un servidor de base de datos MySQL para el almacenamiento de datos.

## 3. Instrucciones de Uso

1. **Clonar el Repositorio:** Clona este repositorio en tu máquina local.
2. **Instalar Dependencias:** Ejecuta `mvn install` en cada carpeta de microservicio para instalar todas las dependencias requeridas.
3. **Configuración de la Base de Datos:** Configura la conexión a la base de datos MySQL en los archivos de propiedades de cada microservicio.
4. **Compilar y Ejecutar:** Compila cada microservicio con `mvn spring-boot:run`.
5. **Acceder a la Aplicación:** Accede a la aplicación a través de la URL proporcionada por el servicio Eureka Server.
6. **Usar los Servicios:** Utiliza los servicios proporcionados por cada microservicio según la documentación correspondiente.

## 4. Contribución

¡Las contribuciones son bienvenidas! Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea tu rama de características (`git checkout -b feature/AmazingFeature`).
3. Realiza tus cambios y haz commit de ellos (`git commit -m 'Add some AmazingFeature'`).
4. Empuja tus cambios a la rama (`git push origin feature/AmazingFeature`).
5. Abre una solicitud de extracción.

## 5. Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.
