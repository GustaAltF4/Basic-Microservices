#imagen modelo
FROM eclipse-temurin:17-jdk-alpine

#informacion de la app
EXPOSE 8003
LABEL authors="acost"

#directorio raiz
WORKDIR /usuario

#copiar y pegar archivos dentro del contenedor
COPY ./pom.xml /usuario
COPY ./.mvn /usuario/.mvn
COPY ./mvnw /usuario
COPY ./mvnw.cmd /usuario

#descargar dependencias
RUN ./mvnw dependency:go-offline

#copiar codigo fuente
COPY ./data/data.json /usuario/data/data.json
COPY ./src /usuario/src

#construir la imagen
RUN ./mvnw clean install -DskipTests

#levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/usuario/target/usuario-0.0.1-SNAPSHOT.jar"]


