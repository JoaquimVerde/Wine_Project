# Use the OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Working directory in the container
WORKDIR /app

# Copy project files into the container
COPY ././

# Build the application using Maven
RUN mvn clean package

# Application startup command
CMD ["java", "-jar", "target/your-application.jar"]

# Expose a port if necessary
EXPOSE 8080