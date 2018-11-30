FROM openjdk:11-jdk
COPY target/myproject-0.1.jar app.jar
# RUN rm -rf /usr/bin/java
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--debug"]
