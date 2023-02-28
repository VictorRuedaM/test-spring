FROM amazoncorretto:17-alpine-jdk
MAINTAINER VARM
COPY target/curso-0.0.1-SNAPSHOT.war test-app.war
ENTRYPOINT ["java", "-war", "/test-app.war"]