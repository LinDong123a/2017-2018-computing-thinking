
## V1.Requirments

Docker1.1+Docker-compose+Microservices(ActiveMQ5.14.2+Mongodb2.17+SpringBoot1.5.3+BlockChain+gradle@2.14+Python3.5+Tensorflow1.1)+Kubernetes

### Architecture

![Screenshot of v1Framework](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/v1Framework.jpg)

#### Docker Hubs

#### 1.smartkit/godpaper-go-message-broker-activemq

_1.Docker pull_
```
docker pull smartkit/godpaper-go-message-broker-activemq
```
_2.Docker run_
```
docker run -d --name activemq -p 8161:8161 -p 1883:1883 -p 5672:5672 -p 61613:61613 -p 61614:61614 -p 61616:61616 smartkit/godpaper-go-message-broker-activemq
```

#### 2.smartkit/godpaper-go-mongodb

_1.Docker run_
```
docker run -P -d --name mongodb smartkit/godpaper-go-mongodb
```

#### 3.MultiChain/HyperLedger

_1.Docker run_
```
cd BlockChain/HyperLedger & docker-compose up -d
```

#### 4.smartkit/godpaper-go-uuid-accredit

_1.Docker run_
```
docker run -p 8095:8095 -d --name accredit --link mongodb --link activemq smartkit/godpaper-go-uuid-accredit
```

_2. RESTful API:_

1.User API: http://127.0.0.1:8095/accredit/swagger-ui.html#/user-controller

2.Gamer API: http://127.0.0.1:8095/accredit/swagger-ui.html#/game-controller

3.BlockChain API: http://127.0.0.1:8095/accredit/swagger-ui.html#/chain-code-controller


_3.Verify Steps:_

__0.Deploy "toyhouse_go_dev" ChainCode__

```
curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/chain/deploy/"
```

__1.Clean tenanted Users and Create 2 random users.__

```
curl -X DELETE --header "Accept: */*" "http://localhost:8095/accredit/user/status/1" && curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/user/r/2"
```

__2.Dismiss all history gamers __

```
curl -X DELETE --header "Accept: */*" "http://localhost:8095/accredit/game/" && curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/pair"&& curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/play"
```

#### 5.smartkit/godpaper-go-ai-player

_1.Docker run_
```
docker run --name aiplayer -e URI_API=http://192.168.0.11:8095/accredit/ -e IP_MQTT=192.168.0.11 smartkit/godpaper-go-ai-player
```


__3.Pair gamers all && play gamers all__

```
curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/pair"&& curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/play"
```

#### 6.smartkit/godpaper-go-ionic
_1.Docker run_

```
cd /v1/GameViewer/App && ionic serve
```

__2.Game Viewer__

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
