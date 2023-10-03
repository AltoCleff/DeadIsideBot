FROM maven:3.8.4-openjdk AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package


FROM openjdk:17
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
ENV BOT_TOKEN=temp_token
COPY --from=builder ${JAR_FILE} app.jar
COPY dice.json dice.json
ENTRYPOINT ["java", "-Dbot.token=${BOT_TOKEN}", "-jar", "app.jar"]