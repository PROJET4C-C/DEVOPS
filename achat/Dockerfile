FROM eclipse-temurin:11-jre-focal

LABEL maintainer="devops-team"

# ✅ Créer un utilisateur non-root dédié
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app

ARG JAR_FILE=target/achat-1.0.jar

# ✅ COPY plus sûr que ADD
COPY ${JAR_FILE} app.jar

# ✅ Donner les droits à l'utilisateur non-root
RUN chown appuser:appgroup app.jar

# ✅ Basculer vers l'utilisateur non-root
USER appuser

EXPOSE 8090

ENV SERVER_PORT=8090

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]