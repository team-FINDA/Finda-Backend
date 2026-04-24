FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :finda-gateway:bootJar -x test --no-daemon

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/finda-gateway/build/libs/*.jar app.jar
RUN useradd -r -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
