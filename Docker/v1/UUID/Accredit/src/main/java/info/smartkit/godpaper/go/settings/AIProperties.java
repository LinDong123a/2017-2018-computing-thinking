package info.smartkit.godpaper.go.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by smartkit on 04/07/2017.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ai")
public class AIProperties {
        private static Logger LOG = LogManager.getLogger(AIProperties.class);

        @Override public String toString() {
                return "AIProperties{" + "rank=" + rank + ", policys=" + policys + '}';
        }

        public int getRank() {
                return rank;
        }

        public void setRank(int rank) {
                this.rank = rank;
                //
                AIVariables.rank = rank;
                LOG.info("setRank:"+AIVariables.rank);
        }

        public List<String> getPolicys() {
                return policys;
        }

        public void setPolicys(List<String> policys) {
                this.policys = policys;
                //
                AIVariables.policys = policys;
                LOG.info("setPolicys:"+AIVariables.policys.toString());
        }

        private int rank;
        private List<String> policys;
}
