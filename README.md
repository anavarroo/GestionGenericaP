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

### 3.1 Configuración del Entorno

1. **Clonar el Repositorio:** Clona este repositorio en tu máquina local.

2. **Instalar Dependencias:** Ejecuta `mvn install` en cada carpeta de microservicio para instalar todas las dependencias requeridas.

3. **Configuración de la Base de Datos:** Configura la conexión a la base de datos MySQL en los archivos de propiedades de cada microservicio.

4. **Compilar y Ejecutar:** Compila cada microservicio con `mvn spring-boot:run`.

5. **Acceder a la Aplicación:** Accede a la aplicación a través de la URL proporcionada por el servicio Eureka Server.

### 3.2 Uso de Postman

Para interactuar con los microservicios utilizando Postman, sigue estos pasos:

1. **Instala la aplicacion de Postman.** [Descarga e Instalación de Postman](https://www.postman.com/downloads/)

2. **Uso:**
   - Abre Postman y haz click en `Create New Collection`.
   - Tras crear una colección haz click en `Add Request`.
   - Cuando crees una solicitud, en la ventana que se abre tendrás:
     - `Method`: Por defecto aparece `GET` ero ajusta el metodo al que necesites, para ello puedes mirar que metodo esta definido en el controller de la api.
     - `base_url`: La URL base de la aplicación, por ejemplo, `http://localhost:8080` para el API Gateway. Para el resto de la ruta puedes mirar la ruta que se ha definido a cada metodo en el controller.
       
3. **Enviar Solicitudes:**
   - Asegúrate de configurar correctamente los parámetros y el cuerpo de la solicitud según la documentación de la API. Por ejemplo cuando haces login te devuelve un token que deberás copiar y pegar siempre que vayas a ejecutar métodos que no sean públicos.
   - `Authorization`: Cuando clikas en authorization te aparecera un desplegable `Type`, clikas `Bearer Token` y pegas el token.

4. **Revisar Respuestas:**
   - Después de enviar una solicitud, revisa la respuesta para asegurarte de que se recibió correctamente y contiene los datos esperados.
  
5. **Ejemplos de solicidudes:**
  - **Registro:** `http://localhost:8080/auth/register`
  - **Login:** `http://localhost:8080/auth/login`
  - **Crear Usuarios:** `http://localhost:8080/api/v1/usuarios/crear`
  - **Editar Usuarios:** `http://localhost:8080/api/v1/usuarios/editar/{correo}`
  - **Eliminar Usuarios:** `http://localhost:8080/api/v1/usuarios/borrar/{correo}`
  - **Consultar Usuarios:** `http://localhost:8080/api/v1/usuarios/(parametro de busqueda)/{variable}`
    - Parametros de busqueda: `nombre`, `apellidos`, `edad`, `correo`, `direccion`, `telefono`.
  - **Mostar Usuarios Pendientes de Registro:** `http://localhost:8080/api/v1/usuarios/pendientes`
  - **Aprobar Registro de Usuarios:** `http://localhost:8080/api/v1/usuarios/aprobar/{correo}`

## 4. Contribución

¡Las contribuciones son bienvenidas! Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea tu rama de características (`git checkout -b feature/AmazingFeature`).
3. Realiza tus cambios y haz commit de ellos (`git commit -m 'Add some AmazingFeature'`).
4. Empuja tus cambios a la rama (`git push origin feature/AmazingFeature`).
5. Abre una solicitud de extracción.

## 5. Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.
