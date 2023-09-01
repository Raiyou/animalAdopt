#
# Build stage
#
FROM maven:3.9.4-eclipse-temurin-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11
COPY --from=build /home/app/target/*.jar animal.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","animal.jar"]
