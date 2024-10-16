#imagen modelo
FROM eclipse-temurin:17-jdk-alpine

#informacion de la app
EXPOSE 8002
LABEL authors="acost"

#directorio raiz
WORKDIR /producto2

#copiar y pegar archivos dentro del contenedor
COPY ./pom.xml /producto2
COPY ./.mvn /producto2/.mvn
COPY ./mvnw /producto2
COPY ./mvnw.cmd /producto2

#descargar dependencias
RUN ./mvnw dependency:go-offline

#copiar codigo fuente
COPY ./data/data.json /producto2/data/data.json
COPY ./src /producto2/src

#construir la imagen
RUN ./mvnw clean install -DskipTests

#levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/producto2/target/producto2-0.0.1-SNAPSHOT.jar"]


