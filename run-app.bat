@echo off
echo Demarrage de l'application Spring Boot Achat...
cd /d "%~dp0\achat"
mvn clean compile -Dmaven.compiler.release=8 spring-boot:run

pause
