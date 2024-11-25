FROM eclipse-temurin:latest
LABEL authors="koffiange"
VOLUME /tmp
EXPOSE 8080
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]