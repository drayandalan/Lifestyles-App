FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src/ ./src/
RUN mvn package -DskipTests
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/AppsGateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8080
CMD ["java", "-jar", "AppsGateway-0.0.1-SNAPSHOT.jar"]