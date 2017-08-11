package info.smartkit.godpaper.go.service;


import info.smartkit.godpaper.go.activemq.StompMessageHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.projectodd.stilts.stomp.StompException;
import org.projectodd.stilts.stomp.StompMessages;
import org.projectodd.stilts.stomp.Subscription;
import org.projectodd.stilts.stomp.client.ClientSubscription;
import org.projectodd.stilts.stomp.client.ClientTransaction;
import org.projectodd.stilts.stomp.client.StompClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.net.ssl.SSLException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;


//@see:http://stilts.projectodd.org/stilts-stomp-client/
@Service
public class StompServiceImpl implements StompService{

    @Autowired MqttService mqttService;

//    private String uri = "ws://192.168.14.1:61614";
    private static Logger LOG = LogManager.getLogger(StompServiceImpl.class);

    private  StompClient stompClient = null;
    private ClientSubscription subscription = null;

    @Override
    public void connect(String uri) throws JMSException, InterruptedException, TimeoutException, StompException, SSLException, URISyntaxException {
        stompClient = new StompClient(uri);
        stompClient.connect();
        //
        LOG.info("stompClient is connected:"+stompClient.isConnected());
    }

    @Override
    public void subscribe(String topic) throws JMSException, StompException {
        subscription =
                stompClient.subscribe( topic )
                        .withMessageHandler( new StompMessageHandler(this,mqttService,topic) )
                        .withAckMode( Subscription.AckMode.CLIENT_INDIVIDUAL )
                        .start();
        LOG.info("stompClient subscription is active:"+subscription.isActive());
    }

    @Override
    public void publish(String topic, String content, int qos) throws StompException {
        ClientTransaction tx = stompClient.begin();
        tx.send(StompMessages.createStompMessage( topic, content ));
        System.out.println("stompClient published:"+content+",to topic: "+topic);
    }

    @Override
    public void unsubscribe() throws StompException {
        subscription.unsubscribe();
        LOG.info("stompClient unsubscribed.");
    }

    @Override
    public void disconnect(String brokerUrl, String clientId) throws StompException, TimeoutException, InterruptedException {

        stompClient.disconnect();
        LOG.info("stompClient disconnected:"+ stompClient.isDisconnected());
    }
}
