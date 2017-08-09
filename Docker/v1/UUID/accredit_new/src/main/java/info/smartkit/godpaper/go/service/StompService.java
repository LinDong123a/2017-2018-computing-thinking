package info.smartkit.godpaper.go.service;

import javax.jms.JMSException;

public interface StompService {

    void connect(String uri) throws JMSException;
    void subscribe(String topic) throws JMSException;
    void publish(String topic, String content,int qos);
    void disconnect(String brokerUrl, String clientId);
}
