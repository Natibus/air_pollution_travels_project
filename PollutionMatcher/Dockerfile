FROM openjdk:11-jdk
COPY target/PollutionMatcher-0.1.jar app.jar
# RUN rm -rf /usr/bin/java
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--debug"]
