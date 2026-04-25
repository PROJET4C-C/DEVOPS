# Image de base Java 21
FROM openjdk:21-jdk-slim

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copier le jar généré par Maven
COPY target/*.jar app.jar

# Port exposé
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
