FROM openjdk:21-jdk-slim

# Ustaw zmienną środowiskową dla wersji JAR-a
ARG JAR_FILE=target/*.jar

# Skopiuj plik JAR do obrazu Docker
COPY ${JAR_FILE} company-backend.jar

# Wystaw port, na którym działa aplikacja
EXPOSE 8080

# Komenda uruchamiająca aplikację
ENTRYPOINT ["java", "-jar", "/company-backend.jar"]
