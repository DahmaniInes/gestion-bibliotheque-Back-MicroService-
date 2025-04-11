FROM openjdk:17
EXPOSE 8081
ADD target/BlogMs-0.0.1-SNAPSHOT.jar BlogMs.jar
ENTRYPOINT ["java", "-jar", "BlogMs.jar"]