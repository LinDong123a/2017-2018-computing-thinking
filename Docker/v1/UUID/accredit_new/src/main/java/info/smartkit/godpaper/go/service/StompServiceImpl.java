package info.smartkit.godpaper.go.service;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class StompServiceImpl implements StompService{

//    private String uri = "ws://192.168.14.1:61614";
    private static Logger LOG = LogManager.getLogger(StompServiceImpl.class);
    private ActiveMQConnectionFactory activeMQConnectionFactory;
    private Connection connection;
    @Override
    public void connect(String uri) throws JMSException {
        activeMQConnectionFactory= new ActiveMQConnectionFactory(uri);
        connection = activeMQConnectionFactory.createConnection();
        LOG.info("connection:"+connection.toString());
    }

    @Override
    public void subscribe(String topic) throws JMSException {
        Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic destTopic  = session.createTopic(topic);
        MessageConsumer messageConsumer = session.createConsumer(destTopic);
        messageConsumer.setMessageListener(new MessageListener(){
            @Override
            public void onMessage(Message m) {
                LOG.info("onMessage:"+m.toString());
                TextMessage textMsg = (TextMessage) m;
                try {
                    LOG.info("onMessage:"+textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void publish(String topic, String content, int qos) {

    }

    @Override
    public void disconnect(String brokerUrl, String clientId) {

    }
}
