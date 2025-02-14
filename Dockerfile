FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia el archivo JAR de la aplicación en el contenedor
COPY target/*.jar app.jar

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
