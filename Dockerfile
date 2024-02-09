# Use the OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Working directory in the container
WORKDIR /app

# Copy project files into the container
COPY ./target/Wine_Project-0.0.1-SNAPSHOT.jar .

# Build the application using Maven
RUN mvn clean package

# Application startup command
CMD ["java", "-jar", "Wine_Project-0.0.1-SNAPSHOT.jar"]

# Expose a port if necessary
EXPOSE 8080