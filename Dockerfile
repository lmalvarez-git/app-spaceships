FROM adoptopenjdk/openjdk11:alpine
WORKDIR /tmp
COPY target/apps-rest-movie-spaceships-0.0.1-SNAPSHOT.jar /tmp
EXPOSE 8080
CMD ["java", "-jar", "apps-rest-movie-spaceships-0.0.1-SNAPSHOT.jar"]