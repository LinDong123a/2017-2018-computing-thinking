package info.smartkit.godpaper.go.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smartkit on 21/06/2017.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "chain")
public class ChainProperties {

        private static Logger LOG = LogManager.getLogger(ChainProperties.class);
        //
        public ChainProperties(String baseUrl, String enrollSecret, String chainName) {
                this.baseUrl = baseUrl;
                this.enrollSecret = enrollSecret;
                this.chainName = chainName;
        }

        @Override public String toString() {
                return "ChainProperties{" + "baseUrl='" + baseUrl + '\'' + ", enrollSecret='" + enrollSecret + '\'' + ", chainName='" + chainName + '\'' + '}';
        }

        public ChainProperties() {
        }

        public String getBaseUrl() {

                return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
                //
                ChainVariables.baseUrl = baseUrl;
                LOG.info("baseUrl:" + ChainVariables.baseUrl);
        }

        public String getEnrollSecret() {
                return enrollSecret;
        }

        public void setEnrollSecret(String enrollSecret) {
                this.enrollSecret = enrollSecret;
                //
                ChainVariables.enrollSecret = enrollSecret;
                LOG.info("channelName:" + enrollSecret);
        }

        private String baseUrl;
        private String enrollSecret;

        public String getChainName() {
                return chainName;
        }

        public void setChainName(String chainName) {
                this.chainName = chainName;
                LOG.info("chainName:" + chainName);
        }

        private String chainName;
}
