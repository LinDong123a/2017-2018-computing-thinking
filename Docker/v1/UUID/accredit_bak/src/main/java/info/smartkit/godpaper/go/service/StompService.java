package info.smartkit.godpaper.go.service;

public interface StompService {

    void connect(String brokerUrl, String clientId);
    void subscribe(String topic);
    void publish(String topic, String content,int qos);
    void disconnect(String brokerUrl, String clientId);
}
