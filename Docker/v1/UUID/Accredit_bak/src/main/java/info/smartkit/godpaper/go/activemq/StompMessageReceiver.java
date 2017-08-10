package info.smartkit.godpaper.go.activemq;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.projectodd.stilts.stomp.StompMessage;
import org.projectodd.stilts.stomp.client.MessageHandler;

public class StompMessageReceiver implements MessageHandler {

    private static Logger LOG = LogManager.getLogger(StompMessageReceiver.class);
    @Override
    public void handle(StompMessage message) {
        LOG.debug("handle StompMessage content:"+message.getContentAsString());
        System.out.println("handle StompMessage content:"+message.getContentAsString());
    }
}
