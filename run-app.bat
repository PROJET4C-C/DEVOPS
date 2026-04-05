@echo off
echo Démarrage de l'application Spring Boot Achat...
cd /d "c:/Users/jemai/OneDrive/Bureau/achat/achat"
mvn clean compile -Dmaven.compiler.release=8 spring-boot:run

pause
