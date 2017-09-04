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


        public List<String> getPolicys() {
                return policys;
        }

        public void setPolicys(List<String> policys) {
                this.policys = policys;
        }

        public int getRanks() {
                return ranks;
        }

        public void setRanks(int ranks) {
                this.ranks = ranks;
        }

        private int ranks;
        private List<String> policys;

        public String getPlayer() {
                return gpu?player+":gpu":player;
        }

        public void setPlayer(String player) {
                this.player = player;
        }


        private String player;

        public String getAgentPrep() {
                return gpu?agentPrep+":gpu":agentPrep;
        }

        public void setAgentPrep(String agentPrep) {
                this.agentPrep = agentPrep;
        }

        public String getAgentTrain() {
                return gpu?agentTrain+":gpu":agentTrain;
        }

        public void setAgentTrain(String agentTrain) {
                this.agentTrain = agentTrain;
        }

        private String agentPrep;
        private String agentTrain;

        public String getScorer() {
                return scorer;
        }

        public void setScorer(String scorer) {
                this.scorer = scorer;
        }

        private String scorer;

        @Override public String toString() {
                return "AIProperties{" + "ranks=" + ranks + ", policys=" + policys + ", player='" + player + '\'' + ", agentPrep='" + agentPrep + '\'' + ", agentTrain='" + agentTrain + '\'' + ", scorer='" + scorer + '\'' + ", gpu=" + gpu + '}';
        }

        public boolean isGpu() {
                return gpu;
        }

        public void setGpu(boolean gpu) {
                this.gpu = gpu;
        }

        private boolean gpu;
}
