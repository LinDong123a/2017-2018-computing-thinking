
## V1.Requirments

Docker1.1+Docker-compose+Microservices(ActiveMQ5.14.2+Mongodb2.17+SpringBoot1.5.3+BlockChain+gradle@2.14+Python3.5+Tensorflow1.1)+Kubernetes

### Architecture

![Screenshot of v1Framework](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/v1Framework.jpg)

#### Docker Hubs

#### 1.smartkit/godpaper-go-message-broker-activemq

1.
```
docker pull smartkit/godpaper-go-message-broker-activemq
```
2.
```
docker run -d --name activemq -p 8161:8161 -p 1883:1883 -p 5672:5672 -p 61613:61613 -p 61614:61614 -p 61616:61616 smartkit/godpaper-go-message-broker-activemq
```

#### 2.smartkit/godpaper-go-mongodb

1.
```
docker run -P -d --name mongodb smartkit/godpaper-go-mongodb
```

#### 3.MultiChain/HyperLedger

1.
```
cd BlockChain/HyperLedger & docker-compose up -d
```

#### 4.smartkit/godpaper-go-uuid-accredit

1.
```
docker run -p 8095:8095 -d --name accredit --link mongodb --link activemq smartkit/godpaper-go-uuid-accredit
```

2. RESTful API:

User API: http://127.0.0.1:8095/accredit/swagger-ui.html#/user-controller

Gamer API: http://127.0.0.1:8095/accredit/swagger-ui.html#/game-controller

BlockChain API: http://127.0.0.1:8095/accredit/swagger-ui.html#/chain-code-controller


3.Tips:

1.Clean tenanted Users and Create 2 random users. 

```
curl -X DELETE --header "Accept: */*" "http://localhost:8095/accredit/user/status/1" && curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/user/r/2"
```

2.Dismiss gamers all && Pair gamers all && play gamers all

```
curl -X DELETE --header "Accept: */*" "http://localhost:8095/accredit/game/" && curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/pair"&& curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/play"
```

#### 5.smartkit/godpaper-go-ai-player

1.
```
docker run --name aiplayer -e URI_API=http://192.168.0.11:8095/accredit/ -e IP_MQTT=192.168.0.11 smartkit/godpaper-go-ai-player
```

2. 
```
docker logs -aiplayer
```

#### 6.smartkit/godpaper-go-ionic
1.

```
cd /v1/GameViewer/App && ionic serve
```

2.

```
http://localhost:8100
```
or

```
file:///Users/smartkit/git/2017-2018-computing-thinking/Docker/v1/GameViewer/App/platforms/ios/www/index.html#/page1/page3
```

### TODO:

1.
```
docker run --name gameviewer smartkit/godpaper-go-ionic
```

2. 
```
docker logs -gameviewer
```
---

### Tips

1. stop all containers

```
docker stop $(docker ps -a -q)    
```
2.remove all containers

```
docker rm $(docker ps -a -q)    
```

3.remove all images
```
docker rmi -f $(docker images -q)
```
