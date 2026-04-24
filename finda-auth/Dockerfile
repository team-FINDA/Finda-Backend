FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :finda-volunteer:bootJar -x test --no-daemon

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/finda-auth/build/libs/*.jar app.jar
RUN useradd -r -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
