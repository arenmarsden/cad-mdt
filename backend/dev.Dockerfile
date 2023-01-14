FROM ibm-semeru-runtimes:open-17-jre-jammy

# Setup working directory
WORKDIR /app

COPY . /app

RUN chmod +x /app/build/libs/backend-all.jar

# Expose port
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "/app/build/libs/backend-all.jar"]