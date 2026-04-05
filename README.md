# Application Achat Spring Boot

## Prérequis
- **MySQL** : localhost:3306, user root (sans password), DB `achatdb`
  - XAMPP : Démarrer MySQL → phpMyAdmin → CREATE DATABASE achatdb;

- **Java 8** (projet pom.xml <java.version>1.8</java.version>)
  - Si Java 21, installez JDK 8

## Exécution
1. Double-cliquez `run-app.bat` ou dans terminal :
   ```
   cd achat
   mvn clean spring-boot:run
   ```

2. Ou JAR (après build) :
   ```
   cd achat
   mvn clean package
   java -jar target/achat-1.0.jar
   ```

## Accès
- Swagger API : http://localhost:8089/SpringMVC/swagger-ui/
- App : http://localhost:8089/SpringMVC/

**Logs** : Cherchez "Started AchatApplication in Xs"

## Dépannage
- Erreur HikariPool : Démarrez MySQL
- Port 8089 occupé : Changez server.port=8080 dans application.properties
- Java version : `mvn -version`
