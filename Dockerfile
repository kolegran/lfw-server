FROM openjdk:11 as builder

WORKDIR /tmp
COPY . /tmp

RUN chmod +x gradlew
RUN ./gradlew clean build

FROM openjdk:11-jre-slim

RUN mkdir /app
COPY --from=builder /tmp/build/libs/lets-forward-server-1.0-SNAPSHOT.jar /app

CMD java -jar /app/lets-forward-server-1.0-SNAPSHOT.jar
