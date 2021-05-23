#
# Build stage
#
FROM maven:3.8.1-openjdk-11-slim AS build

COPY chat-protobuf /home/app/chat-protobuf
COPY chat-server /home/app/chat-server
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim

COPY --from=build /home/app/chat-server/target/chat-server-0.0.1-SNAPSHOT.jar /usr/local/lib/chat-server.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/chat-server.jar"]
