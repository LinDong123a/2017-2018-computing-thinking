package info.smartkit.godpaper.go.service;

import info.smartkit.UUIDAccreditApplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

/**
 * Created by smartkit on 23/06/2017.
 */
@Service
public class MqttServiceImpl implements MqttService,MqttCallback {

        MqttClient sampleClient = null;
        private static Logger LOG = LogManager.getLogger(MqttServiceImpl.class);

        @Override public void connect(String brokerUrl, String clientId) throws MqttException {
                MemoryPersistence persistence = new MemoryPersistence();
                //
                sampleClient = new MqttClient(brokerUrl, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                LOG.info("Connecting to broker: "+brokerUrl);
                sampleClient.connect(connOpts);
                LOG.info("MqttClient Connected.");

        }

        @Override public void subscribe(String topic) throws MqttException {
                sampleClient.subscribe(topic);
                sampleClient.setCallback(this);
        }

        @Override public void publish(String topic, String content, int qos) throws MqttException {

                LOG.info("Publishing message: "+content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);
                LOG.info("MqttClient Message published to:"+topic);
//                sampleClient.disconnect();
//                LOG.info("MqttClient Disconnected");

        }

        @Override public void connectionLost(Throwable throwable) {
                LOG.warn("MQTT connection lost.");
                //reconnect?
        }

        @Override public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                LOG.info("messageArrived:"+mqttMessage.toString());
        }

        @Override public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                LOG.info("deliveryComplete.");
        }
}
