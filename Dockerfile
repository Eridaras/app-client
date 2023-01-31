FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY build/libs/miAplicacion.jar /app/miAplicacion.jar
ENV PORT 8080
EXPOSE 8080
LABEL traefik.http.services.app.loadbalancer.server.port=8080
CMD ["java", "-jar", "miAplicacion.jar"]