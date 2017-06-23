package info.smartkit.godpaper.go.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by smartkit on 23/06/2017.
 */
public interface MqttService {

        void connect(String brokerUrl, String clientId) throws MqttException;
        void subscribe(String topic) throws MqttException;
        void publish(String topic, String content,int qos) throws MqttException;

}
