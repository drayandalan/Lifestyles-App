# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN useradd -r -u 1001 spring && chown -R spring:spring /app
USER spring

COPY --from=build /app/target/FootballApp-*.jar /app/app.jar

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
EXPOSE 8087
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
