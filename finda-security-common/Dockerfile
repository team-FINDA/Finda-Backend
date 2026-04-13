FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
RUN useradd -r -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
