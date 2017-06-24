One logical business object → One microservice  → One git repository folder  → One Mongo collection


1. gradle -b build.gradle build -x test

1.1 java -Dspring.data.mongodb.uri=mongodb://mongodb/toyhouse -jar build/libs/Accredit-0.0.1-SNAPSHOT.jar

2. docker build -t smartkit/godpaper-go-uuid-accredit .

3. http://localhost:8095/accredit/swagger-ui.htm

## Reference

https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i
