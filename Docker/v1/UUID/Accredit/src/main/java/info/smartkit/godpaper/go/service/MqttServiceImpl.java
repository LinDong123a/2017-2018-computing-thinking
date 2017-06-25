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
                connOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
                mqttClient.connect(connOpts);
                LOG.info("MQTT Connecting to broker: "+brokerUrl);

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
                if(mqttClient==null){
                        this.connect(mqttProperties.getBrokerUrl(),topic);
                }
//                LOG.info("MQTT Publishing message: "+content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                mqttClient.publish(topic, message);
                LOG.info("MQTT  Message published to:"+topic+",MSG:"+content);
//                sampleClient.disconnect();
//                LOG.info("MqttClient Disconnected");

        }

        @Override public void disconnect(String brokerUrl, String clientId) throws MqttException {
                mqttClient.disconnect();
        }

        @Override public void unsubscribe(String topic) throws MqttException {
                if(mqttClient==null){
                        this.connect(mqttProperties.getBrokerUrl(),topic);
                }
                mqttClient.unsubscribe(topic);
        }

        @Override public void connectionLost(Throwable throwable) {
                LOG.warn("MQTT connection lost:"+throwable.toString());
                throwable.printStackTrace();
                //reconnect?
        }

        @Override public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                LOG.info("MQTT messageArrived,topic:"+topic+",message:"+mqttMessage.toString());
                String playingMessage = mqttMessage.getPayload().toString();
                LOG.debug("MQTT messageArrived payload:"+playingMessage);
                List<Gamer> playingGamers = gamerRepository.findByName(topic);
                if(playingGamers.size()>0) {
                        String gamerName = playingMessage.split("_play_")[0];
                        String gamerMessage = playingMessage.split("_play_")[1];
                        //TODO: switch by gamer id and update SGF info
                        Gamer playingGamer = gamerRepository.findByName(gamerName).get(0);
                        playingGamer.setSgf(gamerMessage);
                        LOG.info("NOW!! gamer.setSgf(mqttMessage.toString())");
                }
        }

        @Override public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//                LOG.info("MQTT deliveryComplete.");
        }
}
