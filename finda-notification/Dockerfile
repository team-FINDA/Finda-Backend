FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :finda-notification:bootJar -x test
 
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/finda-notification/build/libs/*.jar app.jar
RUN useradd -r -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
