FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/access-0.0.1-SNAPSHOT.jar access.jar
ENTRYPOINT ["java", "-jar", "access.jar"]