# Use the OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Working directory in the container
WORKDIR /app

# Copy project files into the container
COPY ./src /app/src
COPY ./out /app/out  # If you have precompiled files

# Compile the code
RUN javac -d out src/main/java/backend/Wine_Project/WineProjectApplication.java

# Application startup command
CMD ["java", "-cp", "out", "WineProjectApplication"]

# Expose a port if necessary
EXPOSE 8080