# Use the official OpenJDK base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/makeatable-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
