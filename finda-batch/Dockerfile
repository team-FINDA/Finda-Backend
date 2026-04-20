FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :finda-batch:bootJar -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/finda-batch/build/libs/*.jar app.jar
RUN useradd -r -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
