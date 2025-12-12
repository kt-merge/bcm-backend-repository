FROM eclipse-temurin:17-jdk AS builder
# FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY gradle gradle
COPY src src
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:17-jdk
# FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]