package info.smartkit.godpaper.go.service.sse;

import info.smartkit.godpaper.go.dto.SgfMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ReactiveService {

    @Autowired
    private Processor processor;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public SseEmitter getFiniteMessages(int count) {
        final SseEmitter emitter = new SseEmitter();
        processor.processMany(count, executorService)
                .subscribe(
                        value -> onNext(emitter, value),
                        emitter::completeWithError,
                        emitter::complete
                );
        return emitter;
    }

    private void onNext(SseEmitter sseEmitter, SgfMessage message) {
        try {
            sseEmitter.send(message, MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
