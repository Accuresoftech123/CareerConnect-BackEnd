# Stage 1: Build Stage
# Use Maven image to build the app with OpenJDK 17
FROM maven:3.8.1-openjdk-17-slim AS build

# Set the working directory for the build
WORKDIR /app

# Copy pom.xml to download dependencies separately (this uses Docker cache to speed up subsequent builds)
COPY pom.xml .

# Download dependencies to avoid re-downloading them every time
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application and package it into a JAR (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage
# Use a smaller JRE-based image for running the application (openjdk 17 JRE)
FROM openjdk:26-ea-oracle

# Set the working directory for the runtime container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime container
COPY --from=build /app/target/*.jar app.jar

# Expose the port the Spring Boot application will run on (default is 8080)
EXPOSE 9191

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
