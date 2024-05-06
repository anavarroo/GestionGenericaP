# Gestión Genérica de Empleados

Este proyecto es una aplicación de gestión genérica de empleados que utiliza microservicios implementados en Java para el backend, una base de datos MySQL para almacenar los datos y Node.js para el frontend. La arquitectura del sistema se basa en microservicios que se comunican entre sí a través de una API Gateway.

1. [Tecnologías Utilizadas](#tecnologias-utilizadas)
2. [Estructura de Archivos y Directorios](#estructura-de-archivos-y-directorios)
3. [Arquitectura de Alto Nivel](#arquitectura-de-alto-nivel)
4. [JWT](#jwt)
5. [Entorno](#entorno)
6. [Requisitos del Sistema](#requisitos-del-sistema)
7. [Instrucciones de Uso](#instrucciones-de-uso)
8. [Licencia](#licencia)
9. [Contacto](#contacto)


## 1. Tecnologías Utilizadas {#tecnologías-utilizadas}

- Java
- Spring Boot
- MySQL
- SonarQube (Test)

## 2. Estructura de Archivos y Directorios {#estructura-de-archivos-y-directorios}

El proyecto está organizado en varias carpetas:

- `back-end`: Contiene los microservicios implementados en Java. Cada microservicio tiene su propia carpeta dentro de esta carpeta principal.
  - `CRUD-service`: Servicio encargado de realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los datos de los empleados.
    - `controllers`: Contiene los controladores que manejan las solicitudes HTTP.
    - `exceptionHandler`: Maneja las excepciones lanzadas por el servicio.
    - `persistence`: Capa de persistencia que interactúa con la base de datos MySQL.
    - `security`: Configuración de seguridad para proteger el servicio.
    - `services`: Lógica de negocio que implementa las operaciones CRUD.
  - `discovery-server`: Servidor de descubrimiento Eureka para la gestión de microservicios. Este servicio permite a los microservicios       registrarse y descubrirse entre sí de manera dinámica.
    - `DiscoveryServer.java`: Clase principal que inicia el servidor de descubrimiento Eureka.
  - `register-service`: Servicio encargado del registro de nuevos empleados.
    - `exceptionHandler`: Maneja las excepciones lanzadas por el servicio.
    - `persistence`: Capa de persistencia que interactúa con la base de datos MySQL.
    - `security`: Configuración de seguridad para proteger el servicio.
- `api-gateway`: API Gateway para la gestión de las peticiones entre los microservicios y el frontend. Este componente enruta las solicitudes del frontend a los microservicios correspondientes y agrega funcionalidades como autenticación y autorización.
  - `appconfig`: Configuración de la aplicación.
  - `AuthenticationFilter`: Filtro para autenticar las solicitudes entrantes.
  - `routeValidator`: Validador de rutas para garantizar que las solicitudes se enrutan correctamente.
  - `JwtUtil`: Utilidad para generar y verificar tokens JWT.

## 3. Arquitectura de Alto Nivel {#arquitectura-de-alto-nivel}

La arquitectura del sistema sigue un enfoque de microservicios, donde cada componente tiene una responsabilidad específica y se comunica con otros a través de una API Gateway. El frontend interactúa con la API Gateway para realizar operaciones CRUD sobre los datos de los empleados, y esta a su vez redirige las solicitudes a los microservicios correspondientes en el backend.

## 4. JWT {#jwt}

## JWT: Clase JwtAuthenticationFilter

Esta clase representa un filtro de seguridad que maneja la autenticación basada en tokens JWT.

## Métodos

- **Descripción:** Obtiene el token del encabezado de autorización de la solicitud.
- **Método:** `getTokenFromRequest`
- **Parámetros:**
    - `request` - La solicitud HTTP.
- **Respuesta:**
    - Tipo: `String`
    - Descripción: El token JWT si existe en el encabezado de autorización, de lo contrario, null.

## JWT: Clase JwtService

Esta clase representa un servicio encargado de gestionar tokens JWT para la autenticación.

## Métodos

### Generar Token JWT

- **Descripción:** Genera un token JWT para el usuario proporcionado.
- **Método:** `getToken`
- **Parámetros:**
    - `user` - Detalles del usuario.
- **Respuesta:**
    - Tipo: `String`
    - Descripción: Token JWT generado.

### Verificar Validez del Token

- **Descripción:** Verifica si un token JWT es válido para los detalles del usuario proporcionados.
- **Método:** `isTokenValid`
- **Parámetros:**
    - `token` - Token JWT a verificar.
    - `userDetails` - Detalles del usuario.
- **Respuesta:**
    - Tipo: `boolean`
    - Descripción: `true` si el token es válido, `false` en caso contrario.

### Obtener Clave de Firma

- **Descripción:** Obtiene la clave utilizada para firmar los tokens JWT.
- **Método:** `getKey`
- **Respuesta:**
    - Tipo: `Key`
    - Descripción: Clave de firma.



# Entorno {#entorno}

## 5. Descripción de los Microservicios

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

## 6. Requisitos del Sistema {#requisitos-del-sistema}

- **Java:** Se requiere Java 17 o superior.
- **Maven:** Se requiere Maven para la gestión de dependencias y la compilación del proyecto.
- **MySQL:** Se necesita un servidor de base de datos MySQL para el almacenamiento de datos.

## 7. Instrucciones de Uso {#instrucciones-de-uso}

### 7.1 Configuración del Entorno

1. **Clonar el Repositorio:** Clona este repositorio en tu máquina local.

2. **Instalar Dependencias:** Ejecuta `mvn install` en cada carpeta de microservicio para instalar todas las dependencias requeridas.

3. **Configuración de la Base de Datos:** Configura la conexión a la base de datos MySQL en los archivos de propiedades de cada microservicio.

4. **Compilar y Ejecutar:** Compila cada microservicio con `mvn spring-boot:run`.

5. **Acceder a la Aplicación:** Accede a la aplicación a través de la URL proporcionada por el servicio Eureka Server.

### 7.2 Uso de Postman

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

## 8. Licencia {#licencia}

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.

## 9. Contacto {#contacto}

Si tienes alguna pregunta, sugerencia o encuentras algún problema con este proyecto, no dudes en ponerte en contacto con nosotros a través de [correo electrónico](gestiongenerica@gmail.com). ¡Estamos aquí para ayudarte!

