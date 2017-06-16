@see: http://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i

###How?

1.docker run -P -d --name mongodb smartkit/godpaper-go-mongodb

2.docker run -P -d --name accredit --link mongodb smartkit/godpaper-go-uuid-accredit

####Build

gradle build -x test

mvn package -DskipTests=true

###Verify
http://127.0.0.1:8095/accredit/swagger-ui.html#/user-controller