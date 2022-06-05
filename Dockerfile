FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/currency*.jar
COPY ${JAR_FILE} currency.jar
ENTRYPOINT ["java","-jar","/currency.jar"]