# Image de base Java 21
FROM eclipse-temurin:21-jre-jammy

# Sécurité : créer un utilisateur non-root
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copier le jar généré par Maven
COPY target/*.jar app.jar

# Changer le propriétaire des fichiers
RUN chown -R appuser:appgroup /app

# Utiliser l'utilisateur non-root
USER appuser

# Port exposé
EXPOSE 8089

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
