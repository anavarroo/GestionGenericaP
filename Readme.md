# Gestión Genérica de Empleados

Este proyecto es una aplicación de gestión genérica de empleados que utiliza microservicios implementados en Java para el backend, una base de datos MySQL para almacenar los datos y Node.js para el frontend. La arquitectura del sistema se basa en microservicios que se comunican entre sí a través de una API Gateway.

## Tecnologías Utilizadas

- Java
- Spring Boot
- MySQL
- SonarQube (Test)

## Estructura de Archivos y Directorios

El proyecto está organizado en varias carpetas:
- `back-end`: Contiene los microservicios implementados en Java.
  - `CRUD-service`: Servicio encargado de realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los datos de los empleados.
  - `discovery-server`: Servidor de descubrimiento Eureka para la gestión de microservicios.
  - `register-service`: Servicio encargado del registro de nuevos empleados.
  - `api-gateway`: API Gateway para la gestión de las peticiones entre los microservicios y el frontend.
  ## Estructura de Archivos y Directorios

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

## Arquitectura de Alto Nivel

La arquitectura del sistema sigue un enfoque de microservicios, donde cada componente tiene una responsabilidad específica y se comunica con otros a través de una API Gateway. El frontend interactúa con la API Gateway para realizar operaciones CRUD sobre los datos de los empleados, y esta a su vez redirige las solicitudes a los microservicios correspondientes en el backend.

## Instalación y Configuración

### Backend

1. Para cada microservicio, navega a su carpeta correspondiente y sigue las instrucciones de instalación y ejecución.
2. Asegúrate de tener MySQL instalado y configurado correctamente.
3. Tener instalado Sonarqube para conocer la cobertura del código, se necesita tener instalado java con la versión 17 para que funcione.

## Cómo Contribuir

Si deseas contribuir a este proyecto, sigue estos pasos:

1. Realiza un fork del repositorio.
2. Clona tu repositorio fork en tu máquina local.
3. Crea una nueva rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`).
4. Haz tus cambios y realiza commits con mensajes descriptivos.
5. Sube tus cambios a tu repositorio fork (`git push origin feature/nueva-funcionalidad`).
6. Crea una nueva solicitud de extracción en GitHub.

## Contacto

Si tienes alguna pregunta, sugerencia o encuentras algún problema con este proyecto, no dudes en ponerte en contacto con nosotros a través de [correo electrónico](gestiongenerica@gmail.com). ¡Estamos aquí para ayudarte!




