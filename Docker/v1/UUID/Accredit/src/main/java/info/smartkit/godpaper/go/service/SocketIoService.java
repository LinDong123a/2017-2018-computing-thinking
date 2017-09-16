package info.smartkit.godpaper.go.service;

/**
 * Created by smartkit on 16/09/2017.
 */
public interface SocketIoService {
        void connect(String clientId);
        void subscribe(String topic);
        void publish(String topic, String content,int qos);
        void disconnect(String clientId);
        void unsubscribe(String topic);
}
