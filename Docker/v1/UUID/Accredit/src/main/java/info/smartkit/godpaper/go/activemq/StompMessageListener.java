package info.smartkit.godpaper.go.activemq;

import info.smartkit.godpaper.go.service.GamerServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by smartkit on 08/08/2017.
 */
public class StompMessageListener implements MessageListener{

        private static Logger LOG = LogManager.getLogger(StompMessageListener.class);;

        @Override public void onMessage(Message message) {
                LOG.info("Stomp message arrived:"+message.toString());
        }
}
