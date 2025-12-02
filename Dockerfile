# Этап 1: Сборка
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем все файлы
COPY . .

# Даем права на выполнение mvnw
RUN chmod +x mvnw

# Собираем проект
RUN ./mvnw clean package -DskipTests

# Этап 2: Запуск
FROM openjdk:17.0.2-slim

WORKDIR /app

# Копируем собранный JAR из этапа сборки
COPY --from=builder /app/target/*.jar app.jar

# Копируем конфигурационные файлы
COPY app-dev.yml .

# Открываем порт
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=classpath:/application.yml,file:app-dev.yml"]