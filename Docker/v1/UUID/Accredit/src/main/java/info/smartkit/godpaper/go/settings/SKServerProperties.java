package info.smartkit.godpaper.go.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smartkit on 04/07/2017.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "server")
public class SKServerProperties {
        private String contextPath;

        public String getContextPath() {
                return contextPath;
        }

        public void setContextPath(String contextPath) {
                this.contextPath = contextPath;
        }

        public int getPort() {
                return port;
        }

        public void setPort(int port) {
                this.port = port;
        }

        private int port;

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }

        public String getApi() {
                return api;
        }

        public void setApi(String api) {
                this.api = api;
        }

        private String ip;
        private String api;
}
