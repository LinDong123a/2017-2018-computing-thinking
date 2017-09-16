package info.smartkit.godpaper.go.service;


import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import info.smartkit.godpaper.go.dto.PlayMessage;
import info.smartkit.godpaper.go.settings.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by smartkit on 16/09/2017.
 */
@Service
public class SocketIoServiceImpl implements SocketIoService{

        @Autowired ApiProperties apiProperties;
        private Configuration config = new Configuration();
        SocketIOServer socketIOServer;

        @Override public void connect(String clientId) {
//
                config.setHostname(apiProperties.getIp());
                config.setPort(9092);
                config.setContext(clientId);
                socketIOServer = new SocketIOServer(config);
                socketIOServer.start();
        }

        @Override public void subscribe(String topic) {
                //
                socketIOServer.addEventListener(topic, PlayMessage.class, new DataListener<PlayMessage>() {
                        @Override
                        public void onData(SocketIOClient client, PlayMessage data, AckRequest ackRequest) {
                                // broadcast messages to all clients
                                socketIOServer.getBroadcastOperations().sendEvent(topic, data);
                        }
                });
        }

        @Override public void publish(String topic, String content, int qos) {
                // broadcast messages to all clients
                socketIOServer.getBroadcastOperations().sendEvent(topic, content);
        }

        @Override public void disconnect(String clientId) {
//        TODO:socketIOServer.getClient(clientId).
                socketIOServer.stop();
        }

        @Override public void unsubscribe(String topic) {
// TODO:               socketIOServer.(topic);
        }
}
