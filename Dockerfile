FROM eclipse-temurin:17-alpine AS builder

WORKDIR /app
COPY gradle gradle
COPY src src
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]