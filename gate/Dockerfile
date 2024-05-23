FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/gate-0.0.1-SNAPSHOT.jar gate.jar
ENTRYPOINT ["java", "-jar", "gate.jar"]