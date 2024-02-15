FROM openjdk:17
COPY . /app
WORKDIR /app
COPY target/inventory-service-0.0.1-SNAPSHOT.jar /app/app.jar
ENV INNVETORY_SERVICE_PORT=23131
EXPOSE $INNVETORY_SERVICE_PORT
CMD ["java", "-jar", "app.jar", "--server.port=${INNVETORY_SERVICE_PORT}"]

