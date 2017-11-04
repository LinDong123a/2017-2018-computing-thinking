package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.dto.PlayMessage;

import java.util.UUID;

/**
 * Created by smartkit on 16/09/2017.
 */
public interface SocketIoService {
        void join(UUID clientId, String roomId);
        void leave(UUID clientId, String roomId);
        void emitIn(String roomId,String someFunc, PlayMessage playMessage);
        void emitTo(UUID clientId,String someFunc, PlayMessage playMessage);
        void emit(String someFunc, PlayMessage playMessage);
}
