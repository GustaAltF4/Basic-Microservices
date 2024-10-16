#imagen modelo
FROM eclipse-temurin:17-jdk-alpine

#informacion de la app
EXPOSE 8761
LABEL authors="acost"

#directorio raiz
WORKDIR /root

#copiar y pegar archivos dentro del contenedor
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root
COPY ./mvnw.cmd /root

#descargar dependencias
RUN ./mvnw dependency:go-offline

#copiar codigo fuente
COPY ./src /root/src

#construir la imagen
RUN ./mvnw clean install -DskipTests

#levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/root/target/eureka-0.0.1-SNAPSHOT.jar"]


