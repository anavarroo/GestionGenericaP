# Aplicacion de Gestion Generica

Aplicación para la gestión de usuarios, operaciones CRUD para el manejo de los mismos con un registro con posibilidad de 2Outh para el registro en ella.

# Índice

## Punto de vista usuario
1. [Guia de instalacion del entorno](#Entorno)
      - [Configuracion local](#Configuracion-Local)
      - [Usando docker](#ConfiguracionConDocker) 

## Punto de vista técnico
1. [Arquitectura](#Arquitectura)
  - [Explicación de cada servicio](#Servicios)
     - [Spring Security](#Spring-Security)
     - [Testing](#Testing)
     - [SonarQube](#SonarQube)
       
3. [Despligue](#Despligue)
     - [Elementos](#estructura)
         - DockerFile
         - Docker Compose
         - Jenkins File
     - [Experiencia/Promas implementandolos](#bbdd-mysql)








# Entorno

## 1. Descripción de los Microservicios h

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

## 4. Dockerizar los microservicios con Docker Compose

### 4.1 Instalación de Docker
- Instala Docker Desktop desde [Descargar Docker Desktop](https://www.docker.com/products/docker-desktop/)

### 4.2 Implementación de Dockerfile en los microservicios
- Dentro de la raíz de la carpeta de cada microservicio añade un documento llamado Dockerfile.
- Agrega `FROM tuJDK`. Este comando especifica la imagen base que se utilizará para construir la imagen de Docker. Esto significa que nuestra aplicación se ejecutará en un entorno Java dentro de un contenedor Docker basado en `tuJDK`.
- La siguiente línea es `COPY target/nombreArchivo-SNAPSHOT.jar tuApodoArchivo.jar`. Este comando copia el archivo JAR de la aplicación de destino (nombreArchivo-0.0.1-SNAPSHOT.jar) desde el directorio target del sistema de archivos del host al directorio raíz del contenedor Docker, y le da el nombre tuApodoArchivo.jar.
- Por último, tenemos `ENTRYPOINT ["java", "-jar", "discovery-server.jar"]`. Esto significa que nuestra aplicación Spring Boot se ejecutará cuando se inicie el contenedor Docker.

### 4.3 Implementación de docker-compose
[Ver archivo docker-compose.yml](docker-compose.yml)

- `version`: '4'. Este indica la versión de Docker Compose que se utilizará para interpretar el archivo. En este caso, se está utilizando la versión 4 de Docker Compose.
- `services`:
  Aquí comienza la sección donde se definen los servicios que se ejecutarán como contenedores Docker.
- `register-db`:
  Este es el primer servicio definido y se refiere al contenedor de la base de datos MySQL que se utilizará en el sistema. register-db es el nombre del servicio.
- `container_name`: register-db
  Esto establece el nombre del contenedor como register-db.
- `image`: mysql:5
  Esta línea especifica la imagen de Docker que se utilizará para crear el contenedor de MySQL. En este caso, se está utilizando la imagen oficial de MySQL con la versión 5.
- `restart`: always
  Esta configuración indica que el contenedor se reiniciará automáticamente en caso de fallo o reinicio del sistema.
- `environment`:
  Aquí se definen las variables de entorno que se pasarán al contenedor. En este caso, se establece la contraseña de root de MySQL y el nombre de la base de datos.
- `ports`:
  Esta sección mapea los puertos del contenedor al host. En este caso, el puerto 3306 del contenedor MySQL se mapea al puerto 3307 del host.
- `networks`:
  Aquí se especifica la red a la que pertenecerá el contenedor.
  Los siguientes bloques (register-service, crud-service, discovery-server, api-gateway) siguen una estructura similar, pero para cada uno se especifica su propio contenedor, imagen, puertos, variables de entorno y red.
- `networks`:
  Al final del archivo, se define una red llamada generics que será utilizada por todos los contenedores para que puedan comunicarse entre sí.

Adapta el docker compose a tu proyecto, ajustando cada elemento a tus microservicios. No olvides realizar un `clean` seguido de un `install` cuando acabes los Dockerfiles para que tu archivo .jar se actualice en target. [Haz clic aquí para más información sobre Docker](https://docs.docker.com/guides/)

### 4.4 Ejecución de contenedores

Para levantar tus microservicios en contenedores de Docker, necesitarás ejecutar cada uno de estos comandos en una terminal:

1. `docker build -t apodoDeTuArchivo.jar .`: Este comando se utiliza para construir una imagen Docker a partir de un Dockerfile en el directorio actual. Deberás ejecutar este comando sobre la carpeta raíz de cada microservicio para construir sus imágenes, por ejemplo: `C:\Desarrollo\Proyecto\api-gateway`.
2. `docker-compose up`: Se encarga de levantar los archivos especificados en el docker-compose.yml. Este comando se lanzará sobre la raíz del proyecto padre donde se encuentra el docker-compose.yml, por ejemplo: `C:\Desarrollo\Proyecto`.
3. Ante cualquier problema o cambio que se haga en los microservicios, podrás lanzar los siguientes comandos para detener la ejecución de los contenedores y eliminarlos:
- `Cntrl + c`: Si ejecutas esta combinación de teclas en la terminal mientras están corriendo los contenedores, estos se detendrán pero no se borrarán.
- `docker ps -a`: Te mostrará una lista de todos los contenedores que has ejecutado anteriormente, incluidos los que están actualmente en ejecución y los que están detenidos con su información.
- `docker container rm 'primeros números del id'`: Elimina uno o más contenedores de Docker.
- `docker-compose down`: Se utiliza para detener y eliminar los contenedores, las redes y los volúmenes creados por `docker-compose up`.
- `docker-compose down --rmi all`: Detiene y elimina los contenedores de un servicio definido en un archivo docker-compose.yml, así como para eliminar las imágenes asociadas a esos contenedores.
- `docker system prune -a`: Se utiliza para eliminar todos los recursos de Docker que no están en uso.

### 4.5 Posibles problemas
1. Puerto en uso:
   - Asegúrate de que ningún otro programa esté utilizando el puerto especificado.
   - Si el problema persiste, considera cambiar el puerto a uno diferente.
2. Problemas de Conexión del Microservicio a la Base de Datos:
  - Verifica que todos los atributos necesarios estén configurados correctamente para la conexión a la base de datos.
  - Confirma que tanto el DATABASE_USERNAME como el DATABASE_PASSWORD coincidan con las credenciales configuradas en la base de datos.
  - Aunque un microservicio no se comunique directamente con la base de datos, es necesario proporcionar la misma configuración en el archivo docker-compose.yml.
3. Problemas de Comunicación entre el Cliente y el Servidor:
  - Asegúrate de que las rutas estén correctamente configuradas en tu controlador.
  - Verifica que al levantar los contenedores, cada uno de ellos utilice el archivo .jar correspondiente.
4. Problemas de Recursos Insuficientes:
  - Si los contenedores se detienen inesperadamente, verifica si hay suficientes recursos asignados a Docker. Puedes ajustar la asignación de recursos en la configuración de Docker Desktop.
  - También puedes ejecutar el comando ``docker system prune -a`` para eliminar todos los datos antiguos y que no haya ningún posible conflicto con los actuales.
5. Errores de Compilación:
   - Si encuentras errores de compilación al construir las imágenes Docker, verifica que todas las dependencias estén correctamente configuradas y que el código esté actualizado. Ejecuta mvn clean install para asegurarte de que todas las dependencias se resuelvan correctamente.

## 5. Contribución

¡Las contribuciones son bienvenidas! Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea tu rama de características (`git checkout -b feature/AmazingFeature`).
3. Realiza tus cambios y haz commit de ellos (`git commit -m 'Add some AmazingFeature'`).
4. Empuja tus cambios a la rama (`git push origin feature/AmazingFeature`).
5. Abre una solicitud de extracción.

## 5. Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.



## Despliegue
  ### Estructura
  

