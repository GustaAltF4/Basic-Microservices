#imagen modelo
FROM eclipse-temurin:17-jdk-alpine

#informacion de la app
EXPOSE 8001
LABEL authors="acost"

#directorio raiz
WORKDIR /producto1

#copiar y pegar archivos dentro del contenedor
COPY ./pom.xml /producto1
COPY ./.mvn /producto1/.mvn
COPY ./mvnw /producto1
COPY ./mvnw.cmd /producto1

#descargar dependencias
RUN ./mvnw dependency:go-offline

#copiar codigo fuente
COPY ./data/data.json /producto1/data/data.json
COPY ./src /producto1/src

#construir la imagen
RUN ./mvnw clean install -DskipTests

#levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/producto1/target/producto1-0.0.1-SNAPSHOT.jar"]


