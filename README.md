# DEVOPS

## Application Achat Spring Boot

### Prérequis
- **MySQL** : localhost:3306, user root (sans password), DB `achatdb`
  - XAMPP : Démarrer MySQL → phpMyAdmin → CREATE DATABASE achatdb;

- **Java 8** (projet pom.xml avec `java.version` = 1.8)
  - Si vous avez Java 21, installez JDK 8

### Exécution
1. Double-cliquez sur `run-app.bat` ou dans le terminal :
   ```bash
   cd achat
   mvn clean spring-boot:run