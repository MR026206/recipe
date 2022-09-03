FROM openjdk:11

WORKDIR /opt/app

ARG JAR_FILE=target/lacuisine-recipe-backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
