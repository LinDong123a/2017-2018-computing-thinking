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
public class ChainCodeProperties {

        private static Logger LOG = LogManager.getLogger(ChainCodeProperties.class);
        //
        public ChainCodeProperties(String baseUrl, String enrollSecret, String chainName) {
                this.baseUrl = baseUrl;
                this.enrollSecret = enrollSecret;
                this.chainName = chainName;
        }

        @Override public String toString() {
                return "ChainProperties{" + "baseUrl='" + baseUrl + '\'' + ", enrollSecret='" + enrollSecret + '\'' + ", chainName='" + chainName + '\'' + '}';
        }

        public ChainCodeProperties() {
        }

        public String getBaseUrl() {

                return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
                //
                ChainCodeVariables.baseUrl = baseUrl;
                LOG.info("baseUrl:" + ChainCodeVariables.baseUrl);
        }

        public String getEnrollSecret() {
                return enrollSecret;
        }

        public void setEnrollSecret(String enrollSecret) {
                this.enrollSecret = enrollSecret;
                //
                ChainCodeVariables.enrollSecret = enrollSecret;
                LOG.info("channelName:" + enrollSecret);
        }

        private String baseUrl;

        public String getEnrollId() {
                return enrollId;
        }

        public void setEnrollId(String enrollId) {
                this.enrollId = enrollId;
                //
                ChainCodeVariables.enrollId = enrollId;
                LOG.info("ChainCodeVariables.enrollId:" + ChainCodeVariables.enrollId);
        }

        private String enrollId;
        private String enrollSecret;

        public String getChainName() {
                return chainName;
        }

        public void setChainName(String chainName) {
                this.chainName = chainName;
                //
                ChainCodeVariables.chainName = chainName;
                LOG.info("ChainCodeVariables.chainName:" + ChainCodeVariables.chainName);
                //TODO:initialize it.
//                UUIDAccreditChainCode.chainName = chainName;
        }

        private String chainName;
}
