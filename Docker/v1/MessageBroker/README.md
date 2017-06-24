
## V1.Docker+MessageBroker

### ActiveMQ:

1.docker pull smartkit/godpaper-go-message-broker-activemq

2.docker run -d -p 8161:8161 -p 1883:1883 -p 5672:5672 -p 61613:61613 -p 61614:61614 -p 61616:61616 smartkit/godpaper-go-message-broker-activemq --name activemq

3.http://localhost:8161/

### Ports exposed by the image:

#### 8161  Web Console
#### 1883  MQTT 
#### 5672  AMQP 
#### 61613 STOMP
#### 61614 WS  
#### 61616 JMS

### References

https://github.com/yangboz/alpine-activemq
