FROM amazoncorretto:17
MAINTAINER kaebalsaebal@github.com
VOLUME /tmp
EXPOSE 38080
COPY build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]