FROM maven:3.8-eclipse-temurin-11-alpine AS builder

COPY ../kafka-connect-twitter /usr/src/kafka-connect-twitter

WORKDIR /usr/src/kafka-connect-twitter

RUN mvn package

FROM confluentinc/cp-kafka-connect-base:7.2.1

COPY --from=builder /usr/src/kafka-connect-twitter/target/kafka-connect-target/usr/share/kafka-connect/kafka-connect-twitter /data/connect-jars/kafka-connect-twitter
