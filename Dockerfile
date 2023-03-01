FROM amazoncorretto:17-alpine-jdk
MAINTAINER VARM
EXPOSE 8080
COPY target/curso-0.0.1-SNAPSHOT.jar test-app.jar
ENTRYPOINT ["java", "-jar", "/test-app.jar"]