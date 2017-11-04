package info.smartkit.godpaper.go.settings;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by smartkit on 24/09/2017.
 */
public class GameVariables {
        public static Map<String, SseEmitter> sseEmitters = new Hashtable<String, SseEmitter>();
}
