FROM maven:latest

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/AppsGateway-0.0.1-SNAPSHOT.jar"]