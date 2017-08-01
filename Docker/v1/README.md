
## V1.Requirments

Docker1.1+Docker-compose+Microservices(ActiveMQ5.14.2+Mongodb2.17+SpringBoot1.5.3+BlockChain+gradle@2.14+Python3.5+Tensorflow1.1)+Kubernetes

### Architecture

![Screenshot of v1Framework](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/v1Framework.jpg)

#### Docker Hubs

### FlowChartDiagram
![Screenshot of v1FlowChart](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/FlowDiagram_ToyhouseGo_export.png)


#### Docker compose

```
sudo wget https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/docker-compose.yml 
```
&

```
sudo docker-compose up -d
```

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
docker run -p 27017:27017 -d --name mongodb smartkit/godpaper-go-mongodb
```

#### 3.MultiChain/HyperLedger

_1.Docker run_
```
cd BlockChain/HyperLedger & docker-compose up -d
```

#### 4.smartkit/godpaper-go-uuid-accredit

_1.Docker run_
```
docker run  --name uuid-accredit --link mongodb --link activemq --link v1_vp0_1 -p 8095:8095 smartkit/godpaper-go-uuid-accredit
```

or:
```
java -Dspring.data.mongodb.uri=mongodb://mongodb/toyhouse -Djava.security.egd=file:/dev/./urandom -Dmqtt.brokerUrl=tcp://activemq:1883 -Dchain.baseUrl=http://v1_vp0_1:7050 -jar build/libs/Accredit-0.0.1-SNAPSHOT.jar
```

_2. RESTful API:_

1.User API: http://127.0.0.1:8095/accredit/swagger-ui.html#/user-controller

2.Gamer API: http://127.0.0.1:8095/accredit/swagger-ui.html#/game-controller

3.BlockChain API: http://127.0.0.1:8095/accredit/swagger-ui.html#/chain-code-controller


__7.Verify Steps:__

__7.0.Deploy "toyhouse_go_dev" ChainCode__

```
curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/chain/deploy/"
```

__7.1.Clean tenanted Users and Create 2 random users.__

```
curl -X DELETE --header "Accept: */*" "http://localhost:8095/accredit/user/status/1" && curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/user/r/2"
```

__7.2.Dismiss all history gamers__

```
curl -X DELETE --header "Accept: */*" "http://localhost:8095/accredit/game/"
```

#### 5.smartkit/godpaper-go-ai-player

_1.Docker run_
```
docker run --name aiplayer -e URI_API=http://192.168.0.11:8095/accredit/ -e IP_MQTT=192.168.0.11 -v /Users/smartkit/git/2017-2018-computing-thinking/Docker/v1/UUID/Accredit/target/classes/AI_FILEs:/AI_FILEs smartkit/godpaper-go-ai-player:latest
```


__7.3.Pair gamers all && Play gamers all___

```
curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/pair"&& curl -X GET --header "Accept: */*" "http://localhost:8095/accredit/game/play"
```

#### 6.smartkit/godpaper-go-ionic
_1.Docker run_

```
docker run -ti -p 8100:8100 -p 35729:35729 -v ~/git/2017-2018-computing-thinking/Docker/v1/GameViewer/App:/App smartkit/godpaper-go-ionic
```

```
cd /v1/GameViewer/App && ionic serve --address 172.31.228.77
```

__7.5.Game Viewer__

```
http://localhost:8100
```
or

```
file:///Users/smartkit/git/2017-2018-computing-thinking/Docker/v1/GameViewer/App/platforms/ios/www/index.html#/page1/page3
```

__8.AI Agents__

1.Mugo

```
docker run --name mugo -v /Users/smartkit/git/2017-2018-computing-thinking/Docker/v1/UUID/Accredit/target/classes/sgf:/sgf smartkit/godpaper-go-ai-agent-mugo:latest
```
1.DarkGo

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

4.Export container
```
sudo docker export containerID > your.tar
```
5.Import container
```
docker import your.tar your/container
```
6.Run container with entrypoint or command
```
docker run -p 27017:27017 -d --name mongodb our/container entrypoint/command 
```
7.stop all containers by image name
```
docker stop $(docker ps -q --filter ancestor=<image-name> )
```

### Deployment

![Screenshot of DockerDeploymentDiagram](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/DockerDeploymentDiagram.png)
