FROM openjdk:11-jdk-slim-bullseye
VOLUME /tmp
COPY target/*.jar schoolregistration.jar
ENTRYPOINT ["java","-jar","/schoolregistration.jar"]


