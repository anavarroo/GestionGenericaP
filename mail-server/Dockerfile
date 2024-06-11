FROM openjdk:17-jdk-alpine
COPY target/mail-server-0.0.1-SNAPSHOT.jar mail-server.jar
ENTRYPOINT ["java", "-jar", "mail-server.jar"]