FROM openjdk:17
COPY . /app
WORKDIR /app
ENV PORT 61800
EXPOSE ${PORT}
COPY target/eureka-service-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "app.jar", "--server.port=${PORT}"]