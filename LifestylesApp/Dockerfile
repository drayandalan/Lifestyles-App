FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src/ ./src/
RUN mvn package -DskipTests
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/LifestylesApp-0.0.1-SNAPSHOT.jar .

EXPOSE 8085
CMD ["java", "-jar", "LifestylesApp-0.0.1-SNAPSHOT.jar"]