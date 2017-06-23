package info.smartkit.godpaper.go.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smartkit on 23/06/2017.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

        public String getBrokerUrl() {
                return brokerUrl;
        }

        public void setBrokerUrl(String brokerUrl) {
                this.brokerUrl = brokerUrl;
        }

        public String getClientId() {
                return clientId;
        }

        public void setClientId(String clientId) {
                this.clientId = clientId;
        }

        public int getQos() {
                return qos;
        }

        public void setQos(int qos) {
                this.qos = qos;
        }

        private String brokerUrl;
        private String clientId;
        private int qos;

        public MqttProperties() {
        }

        public MqttProperties(String brokerUrl, String clientId, int qos) {

                this.brokerUrl = brokerUrl;
                this.clientId = clientId;
                this.qos = qos;
        }

        @Override public String toString() {
                return "MqttProperties{" + "brokerUrl='" + brokerUrl + '\'' + ", clientId='" + clientId + '\'' + ", qos=" + qos + '}';
        }
}
