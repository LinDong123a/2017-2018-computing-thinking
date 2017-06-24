
## V1.Requirments

Docker1.1+Docker-compose+Microservices(ActiveMQ5.14.2+Mongodb2.17+SpringBoot1.5.3+BlockChain+gradle@2.14+Python3.5+Tensorflow1.1)+Kubernetes

### Architecture

![Screenshot of v1Framework](https://raw.githubusercontent.com/yangboz/2017-2018-computing-thinking/master/Docker/v1/v1Framework.jpg)

#### Docker Hubs

#### 1.smartkit/godpaper-go-message-broker-activemq

1.docker pull smartkit/godpaper-go-message-broker-activemq

2.docker run -d -p 8161:8161 -p 1883:1883 -p 5672:5672 -p 61613:61613 -p 61614:61614 -p 61616:61616 smartkit/godpaper-go-message-broker-activemq --name activemq

#### 2.smartkit/godpaper-go-mongodb



#### 3.smartkit/godpaper-go-uuid-accredit


