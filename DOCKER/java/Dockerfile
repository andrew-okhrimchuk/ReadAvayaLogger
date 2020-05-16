
FROM openjdk:8-jdk-slim

         # из -> в 
COPY nonBlockingEchoServer.jar nonBlockingEchoServer.jar

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://springboot-mongo:27017/AVAYA_LOGGER","-Djava.security.egd=file:/dev/./urandom","-jar","./nonBlockingEchoServer.jar"]

