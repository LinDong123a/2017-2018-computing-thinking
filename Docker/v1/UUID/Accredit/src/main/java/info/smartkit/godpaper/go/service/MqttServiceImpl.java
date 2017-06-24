package info.smartkit.godpaper.go.service;

import info.smartkit.UUIDAccreditApplication;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.MqttProperties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by smartkit on 23/06/2017.
 */
@Service
public class MqttServiceImpl implements MqttService,MqttCallback {

        @Autowired GamerRepository gamerRepository;
        @Autowired MqttProperties mqttProperties;
        private  MqttClient mqttClient = null;

        private static Logger LOG = LogManager.getLogger(MqttServiceImpl.class);

        @Override public void connect(String brokerUrl, String clientId) throws MqttException {
                //
                MemoryPersistence persistence = new MemoryPersistence();
                mqttClient = new MqttClient(brokerUrl, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                LOG.info("MQTT Connecting to broker: "+brokerUrl);
                mqttClient.connect(connOpts);
                LOG.info("MQTT client Connected.");

        }

        @Override public void subscribe(String topic) throws MqttException {
                if(mqttClient==null){
                        this.connect(mqttProperties.getBrokerUrl(),topic);
                }
                //
                mqttClient.subscribe(topic);
                mqttClient.setCallback(this);
        }

        @Override public void publish(String topic, String content, int qos) throws MqttException {

                LOG.info("MQTT Publishing message: "+content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                mqttClient.publish(topic, message);
                LOG.info("MQTT  Message published to:"+topic);
//                sampleClient.disconnect();
//                LOG.info("MqttClient Disconnected");

        }

        @Override public void connectionLost(Throwable throwable) {
                LOG.warn("MQTT connection lost.");
                //reconnect?
        }

        @Override public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                LOG.info("MQTT essageArrived:"+mqttMessage.toString());
                LOG.info("MQTT messageArrived payload:"+mqttMessage.getPayload().toString());
                //TODO: switch by gamer id and update SGF info
                List<Gamer> playingGamers = gamerRepository.findByStatus(GameStatus.PLAYING.getIndex());
//                String[] playingGamersIDs =
                for(Gamer gamer : playingGamers){
                        if(gamer.getName().equals(mqttMessage.getPayload().toString())){
                                LOG.info("NOW!! gamer.setSgf(mqttMessage.toString())");
//                                LOG.info(gamer.setSgf(mqttMessage.toString()));
                        }
                }
        }

        @Override public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                LOG.info("deliveryComplete.");
        }
}
