package info.smartkit.godpaper.go.service;

import org.projectodd.stilts.stomp.StompException;

import javax.jms.JMSException;
import javax.net.ssl.SSLException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

public interface StompService {

    void connect(String uri) throws JMSException, InterruptedException, TimeoutException, StompException, SSLException, URISyntaxException;
    void subscribe(String topic) throws JMSException, StompException;
    void publish(String topic, String content,int qos) throws StompException;
    void disconnect(String brokerUrl, String clientId) throws StompException, TimeoutException, InterruptedException;
}
