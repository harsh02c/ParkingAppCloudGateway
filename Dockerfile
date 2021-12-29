FROM openjdk:8
EXPOSE 8080
ADD target/parkingappcloudgateway.jar parkingappcloudgateway.jar
ENTRYPOINT ["java","-jar","parkingappcloudgateway.jar"]