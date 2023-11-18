# Build stage
FROM gradle:8-jdk17 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

# Run stage
FROM openjdk:17
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/api-gateway.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]