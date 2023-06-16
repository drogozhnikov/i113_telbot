FROM openjdk:11 as build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TelegramBot.jar
ENTRYPOINT ["java","-jar","TelegramBot.jar"]
