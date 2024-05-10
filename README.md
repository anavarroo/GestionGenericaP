# Aplicación Genérica

## Como ejecutar la aplicación con Docker

1. Escribe en la terminal el comando `mvn clean package -DskipTest` para limpiar la aplicación.
2. Escibe `docker-compose up` para iniciar la aplicación.

## Como ejecutar la aplicación sin Docker

1. Escribe en la terminal el comando `mvn clean verify -DskipTest` para entrar en cada carpeta y construit la aplicación.
2. Despues ejecuta `mvn spring-boot:run` para entrar en cada carpeta y iniciar la aplicación.

Para ver lo documentación completa del proyecto: [Wiki](https://github.com/anavarroo/GestionGenericaP/wiki)
