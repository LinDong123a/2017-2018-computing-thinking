package info.smartkit.godpaper.go.service.sse;

import info.smartkit.godpaper.go.dto.SgfMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Processor extends AbstractProcessor<SgfMessage> {

    @Override
    public SgfMessage processOne() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new SgfMessage(new Date().toString());
    }
}
