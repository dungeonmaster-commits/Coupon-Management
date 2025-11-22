# Use official Maven image to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# --------------------------
# Run stage (smaller image)
# --------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render injects $PORT automatically)
EXPOSE 8080

# Use PORT environment variable from Render
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
