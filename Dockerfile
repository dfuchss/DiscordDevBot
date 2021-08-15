FROM maven:3-jdk-14 as builder

WORKDIR /usr/src/bot
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package

FROM azul/zulu-openjdk-alpine:11

ENV DISCORD_TOKEN MY_TOKEN

WORKDIR /usr/src/bot
COPY --from=builder /usr/src/bot/target/devbot-*-jar-with-dependencies.jar devbot.jar
ENTRYPOINT java -jar /usr/src/bot/devbot.jar
