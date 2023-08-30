FROM openjdk:11 as build
WORKDIR ./target/
ENTRYPOINT ["java","-jar","animal-0.0.1-SNAPSHOT.jar"]