# Dockerfile
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY /pom.xml .
COPY /src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/delivery-track-1.0.0.jar .

CMD ["java", "-jar", "delivery-track-1.0.0.jar"]