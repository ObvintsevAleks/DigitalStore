FROM openjdk:17.0.2-slim

WORKDIR /app

# Устанавливаем локали и поддержку UTF-8
RUN apt-get update && \
        apt-get install -y maven && \
        apt-get clean

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем весь проект
COPY . .

# Собираем проект с UTF-8
RUN mvn clean -DskipTests=true install

# Переменные окружения для тестов с поддержкой кириллицы
ENV TEST_ENV=docker

# Запускаем тесты с поддержкой UTF-8
CMD ["mvn", "test"]