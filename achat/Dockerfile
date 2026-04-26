FROM eclipse-temurin:11-jre-focal

LABEL maintainer="devops-team"

# Expose port 8090 to match user Jenkinsfile APP_PORT
EXPOSE 8090

# Set environment variables for the application
ENV SERVER_PORT=8090
# We use host.docker.internal fallback so the isolated container can reach MySQL on the Windows host when launched via simple `docker run`
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/achatdb?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC

ARG JAR_FILE=target/achat-1.0.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
