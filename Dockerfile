#-- Build
FROM maven:3.6.3-jdk-11-slim as build
WORKDIR /usr/src/who-is-reviewing
COPY pom.xml .
COPY src/ src/
RUN ["mvn", "clean", "package"]

#--Image
FROM openjdk:8-jre-alpine
COPY --from=build /usr/src/who-is-reviewing/target/who-is-reviewing-1.0-SNAPSHOT.jar /usr/src/who-is-reviewing/who-is-reviewing.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/src/who-is-reviewing/who-is-reviewing.jar", "-Dspring.profiles.active=docker.prod"]