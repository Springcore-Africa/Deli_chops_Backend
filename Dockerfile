FROM maven:3.8.5-openjdk-17 AS build
COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/deliChopsBackend-1.0-SNAPSHOT.jar deliChopsBackend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "deliChopsBackend.jar"]