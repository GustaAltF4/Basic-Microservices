#imagen modelo
FROM eclipse-temurin:17-jdk-alpine

#informacion de la app
EXPOSE 8080
LABEL authors="acost"

#directorio raiz
WORKDIR /gateway

#copiar y pegar archivos dentro del contenedor
COPY ./pom.xml /gateway
COPY ./.mvn /gateway/.mvn
COPY ./mvnw /gateway
COPY ./mvnw.cmd /gateway

#descargar dependencias
RUN ./mvnw dependency:go-offline

#copiar codigo fuente
COPY ./src /gateway/src

#construir la imagen
RUN ./mvnw clean install -DskipTests

#levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/gateway/target/gateway-0.0.1-SNAPSHOT.jar"]


