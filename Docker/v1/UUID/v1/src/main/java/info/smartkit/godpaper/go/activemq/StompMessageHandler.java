package info.smartkit.godpaper.go.activemq;

import info.smartkit.godpaper.go.service.MqttService;
import info.smartkit.godpaper.go.service.StompService;
import info.smartkit.godpaper.go.settings.MqttQoS;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.projectodd.stilts.stomp.StompException;
import org.projectodd.stilts.stomp.StompMessage;
import org.projectodd.stilts.stomp.client.MessageHandler;

public class StompMessageHandler implements MessageHandler {

//    private static Logger LOG = LogManager.getLogger(StompMessageHandler.class);
    @Override
    public void handle(StompMessage message) {
//        LOG.debug("handle StompMessage content:"+message.getContentAsString());
        String content = message.getContentAsString();
        System.out.println("handle StompMessage content:"+content);
        try {
            stompService.publish(topic,userId+"#play#B[dp]", MqttQoS.EXCATLY_ONCE.getIndex());
            //
//            mqttService.publish(topic,content,MqttQoS.EXCATLY_ONCE.getIndex());
        } catch (StompException e) {
            e.printStackTrace();
        }
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }

    }
    private StompService stompService;
    private MqttService mqttService;
    private String topic;
    private String userId="594a4c8b6516899e6a30e17f";
    //
    public StompMessageHandler(StompService stompService,MqttService mqttService,String topic) {
        this.stompService = stompService;
        this.mqttService = mqttService;
        this.topic = topic;
    }
}
