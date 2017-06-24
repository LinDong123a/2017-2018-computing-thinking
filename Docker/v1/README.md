
## V1.Requirments

Docker1.1+Docker-compose+Microservices(ActiveMQ5.14.2+Mongodb2.17+SpringBoot1.5.3+BlockChain+gradle@2.14+Python3.5+Tensorflow1.1)+Kubernetes

### Architecture

![Screenshot of v1Framework](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/v1Framework.jpg)

#### Docker Hubs

#### 1.smartkit/godpaper-go-message-broker-activemq

1.docker pull smartkit/godpaper-go-message-broker-activemq

2.docker run -d --name activemq -p 8161:8161 -p 1883:1883 -p 5672:5672 -p 61613:61613 -p 61614:61614 -p 61616:61616 smartkit/godpaper-go-message-broker-activemq

#### 2.smartkit/godpaper-go-mongodb

1.docker run -P -d --name mongodb smartkit/godpaper-go-mongodb

2.

#### 3.smartkit/godpaper-go-uuid-accredit

1. docker run -p 8095:8095 -d --name accredit --link mongodb --link activemq smartkit/godpaper-go-uuid-accredit


2. 

User API: http://127.0.0.1:8095/accredit/swagger-ui.html#/user-controller

Gamer API: http://127.0.0.1:8095/accredit/swagger-ui.html#/game-controller

BlockChain API: http://127.0.0.1:8095/accredit/swagger-ui.html#/chain-code-controller
