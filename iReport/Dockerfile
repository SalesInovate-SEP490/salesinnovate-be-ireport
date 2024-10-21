FROM openjdk:17

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} ireport-service.jar

ENTRYPOINT ["java","-jar","ireport-service.jar"]

EXPOSE 8087