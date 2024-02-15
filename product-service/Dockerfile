FROM openjdk:17
COPY . /app
WORKDIR /app
COPY target/product-service-0.0.1-SNAPSHOT.jar /app/app.jar
ENV PRODUCT_SERVICE_PORT=23130
EXPOSE $PRODUCT_SERVICE_PORT
CMD ["java", "-jar", "app.jar", "--server.port=${PRODUCT_SERVICE_PORT}"]
