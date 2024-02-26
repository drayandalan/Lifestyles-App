FROM maven:latest

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8085
CMD ["java", "-jar", "target/FootballApp-0.0.1-SNAPSHOT.jar"]