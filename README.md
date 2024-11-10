# Informacje
## Swagger
URL z dokumentacją: [Swagger](http://localhost:8080/swagger-ui/index.html)

## Budowanie i uruchamianie w dockerze:
w katalogu z projektem należy uruchomić:

```bash
mvn clean package -DskipTests
docker compose up --build
