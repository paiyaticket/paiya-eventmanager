FROM eclipse-temurin:17-jdk-alpine
LABEL authors="koffiange"

VOLUME /tmp
ARG JAR_FILE
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]