FROM openjdk:17-jdk-alpine
COPY target/audit-service-0.0.1-SNAPSHOT.jar audit-service.jar
ENTRYPOINT ["java", "-jar", "audit-service.jar"]