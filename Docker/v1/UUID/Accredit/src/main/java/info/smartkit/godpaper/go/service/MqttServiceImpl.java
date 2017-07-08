package info.smartkit.godpaper.go.service;

//import com.toomasr.sgf4j.Sgf;
//import com.toomasr.sgf4j.parser.Game;

import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.settings.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.apache.log4j.spi.NOPLogger;

/**
 * Created by smartkit on 23/06/2017.
 */
@Service
public class MqttServiceImpl implements MqttService,MqttCallback {

        @Autowired GamerRepository gamerRepository;
        @Autowired GamerService gamerService;
        @Autowired MqttProperties mqttProperties;
        @Autowired ChainCodeService chainCodeService;
        @Autowired ChainCodeProperties chainCodeProperties;
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
                //ONLY PLAYING GAME TOPIC.
                if(topic.contains(MqttVariables.tag_vs)) {
                        LOG.info("MQTT subscribed game topic:"+topic);
                        mqttClient.setCallback(this);
                }
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
                if(mqttClient.isConnected()) {
                        mqttClient.unsubscribe(topic);
                }
        }

        @Override public void connectionLost(Throwable throwable) {
                LOG.warn("MQTT connection lost:"+throwable.toString());
                throwable.printStackTrace();
                //reconnect?
        }

        @Override public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                LOG.info("MQTT messageArrived,topic:"+topic+",message:"+mqttMessage.toString());
                String playingMessage = mqttMessage.toString();
//                LOG.debug("MQTT messageArrived payload:"+playingMessage);//594dd2e0e4b0dbacf6b3d0e3_play_W[pd]
                if (playingMessage.contains(MqttVariables.tag_play)) {
                        String[] gameIdMessage =  playingMessage.split(MqttVariables.tag_play);
                        String gamerId =gameIdMessage[0];
                        LOG.info("gameIdMessage("+gameIdMessage.length+"),gamerId:"+gamerId);
                        //Except first hand.
                        if(gameIdMessage.length==2) {
                                String gamerMessage = gameIdMessage[1];
                                //find one
                                Gamer playingGamer = gamerRepository.findByTopic(topic).get(0);
//                                Gamer playingGamer = gamerRepository.findOne(gamerId);
                                LOG.info("findByTopicName:"+playingGamer.toString());
                                //by gamer id and update SGF info
//                                Game sgfGame = Sgf.createFromString(gamerMessage);
//                                LOG.info("sgfGame:"+sgfGame.toString());
                                String sgfString = playingGamer.getSgf().concat(gamerMessage).concat(";");//(;FF[4]GM[1]SZ[19]CA[UTF-8]SO[go.toyhouse.cc]BC[cn]WC[cn]PB[aa]BR[9p]PW[bb]WR[5p]KM[7.5]DT[2012-10-21]RE[B+R];
                                playingGamer.setSgf(sgfString);
                                //update to ChainCode invoke
                                String[] putArgs = {playingGamer.getId(),playingGamer.getSgf()};
                                chainCodeService.invoke(chainCodeProperties.getChainName(),chainCodeProperties.getEnrollId(),putArgs);
                                //Saved to real sgf file.

                                //
                                playingGamer.setStatus(GameStatus.SAVED.getIndex());
                                Gamer updatedGamer = gamerRepository.save(playingGamer);
                                LOG.info("updatedGamer.sgf:"+updatedGamer.getSgf());
                        }
                }
        }

        @Override public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//                LOG.info("MQTT deliveryComplete.");
        }
}
