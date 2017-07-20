@see: http://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i

### How?

1.docker pull

```
docker pull smartkit/godpaper-go-uuid-accredit
```
2.
```
docker run  --name uuid-accredit --link mongodb --link activemq --link v1_vp0_1 -p 8095:8095 smartkit/godpaper-go-uuid-accredit
```

#### Build
```
gradle build -x test
```
or
```
mvn package -DskipTests=true
```
then
```
docker build -t smartkit/godpaper-go-uuid-accredit .
```
###Verify
http://127.0.0.1:8095/accredit/swagger-ui.html#/user-controller
