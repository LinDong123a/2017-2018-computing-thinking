package info.smartkit.godpaper.go.service;


import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import info.smartkit.godpaper.go.dto.PlayMessage;
import info.smartkit.godpaper.go.settings.ApiProperties;
import info.smartkit.godpaper.go.settings.SocketIoVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by smartkit on 16/09/2017.
 */
@Service
public class SocketIoServiceImpl implements SocketIoService{

        @Override
        public void join(UUID clientId, String roomId) {
                SocketIoVariables.server.getClient(clientId).joinRoom(roomId);
        }

        @Override
        public void leave(UUID clientId,String roomId) {
                SocketIoVariables.server.getClient(clientId).leaveRoom(roomId);
        }

        @Override
        public void emitIn(String roomId,String someFunc, PlayMessage playMessage) {
                SocketIoVariables.server.getRoomOperations(roomId).sendEvent(someFunc,playMessage);

        }

        @Override
        public void emitTo(UUID clientId,String someFunc, PlayMessage playMessage) {
                SocketIoVariables.server.getClient(clientId).sendEvent(someFunc,playMessage);
        }

        @Override
        public void emit(String someFunc, PlayMessage playMessage) {
                SocketIoVariables.server.getBroadcastOperations().sendEvent(someFunc,playMessage);
        }
}
