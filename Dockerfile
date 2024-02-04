FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/alertmonitor-0.0.1-SNAPSHOT.jar /app/alertmonitor.jar
EXPOSE 8080
CMD ["java", "-jar", "alertmonitor.jar"]
