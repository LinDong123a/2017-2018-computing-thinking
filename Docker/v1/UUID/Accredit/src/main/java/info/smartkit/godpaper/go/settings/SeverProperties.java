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
public class SeverProperties {
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
}
