# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Leverage cache: resolve deps before copying sources
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

COPY src ./src
RUN mvn -q -B package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# (Optional) run as non-root
RUN useradd -r -u 1001 spring && chown -R spring:spring /app
USER spring

# copy fat jar (rename to a stable name)
COPY --from=build /app/target/AppsGateway-*.jar /app/app.jar

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
