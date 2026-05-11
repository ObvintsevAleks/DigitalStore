# Этап 1: Сборка
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Этап 2: Запуск
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "app.jar"]
