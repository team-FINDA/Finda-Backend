FROM openjdk:17-jdk-slim
WORKDIR /app
COPY finda-batch/build/libs/*.jar app.jar
RUN useradd -r -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
