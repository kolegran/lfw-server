FROM openjdk:11 as builder

WORKDIR /tmp
COPY . /tmp

WORKDIR /tmp/src
RUN find -name "*.java" > sources.txt
RUN javac @sources.txt
RUN jar cfe lets-forward-server.jar LetsForwardServerApplication LetsForwardServerApplication.class letsfwserver/LetsFwServer.class

FROM openjdk:11-jre-slim

RUN mkdir /app
COPY --from=builder tmp/src/lets-forward-server.jar /app

ENTRYPOINT ["java", "-jar", "/app/lets-forward-server.jar"]
