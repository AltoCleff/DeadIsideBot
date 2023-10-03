FROM openjdk:17
ARG JAR_FILE=target/*.jar
ENV BOT_TOKEN=qwerty
COPY ${JAR_FILE} app.jar
COPY dice.json dice.json
ENTRYPOINT ["java", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]