FROM openjdk:11-jdk-slim

ADD ./target/timeline-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/timeline-service.jar"]

EXPOSE 8068