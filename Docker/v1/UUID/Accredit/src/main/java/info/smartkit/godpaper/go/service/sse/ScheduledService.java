package info.smartkit.godpaper.go.service.sse;


import info.smartkit.godpaper.go.dto.SgfMessage;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.repository.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Component
public class ScheduledService {

    private final SseEmitter emitter = new SseEmitter();

    @Autowired GamerRepository gamerRepository;

    private  String gamerId;
    private  Gamer gamer;

    public SseEmitter getInfiniteMessages(String gamerId) {
        this.gamerId = gamerId;
        this.gamer = gamerRepository.findOne(gamerId);
        return emitter;
    }

    @Scheduled(fixedRate = 3000)
    void timerHandler() {
        try {
            emitter.send(new SgfMessage(gamer.getSgf()), MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}
