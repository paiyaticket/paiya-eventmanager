FROM eclipse-temurin:17-jdk-alpine
LABEL authors="koffiange"

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]