FROM java:8
VOLUME /tmp
ADD build/libs/Accredit-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8095
RUN bash -c "touch /app.jar"
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb/toyhouse", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]