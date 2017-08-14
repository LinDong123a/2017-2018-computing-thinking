package info.smartkit.godpaper.go.pojo;

/**
 * Created by smartkit on 22/06/2017.
 */
public class Invoker {
        @Override public String toString() {
                return "Invoker{" + "chainName='" + chainName + '\'' + ", enrollId='" + enrollId + '\'' + ", values=" + values + '}';
        }

        public Invoker() {
        }

        public Invoker(String chainName, String enrollId, String[] values) {
                this.chainName = chainName;
                this.enrollId = enrollId;
                this.values = values;
        }

        private String chainName;
        private String enrollId;
        private String[] values;

        public String getChainName() {
                return chainName;
        }

        public void setChainName(String chainName) {
                this.chainName = chainName;
        }

        public String getEnrollId() {
                return enrollId;
        }

        public void setEnrollId(String enrollId) {
                this.enrollId = enrollId;
        }

        public String[] getValues() {
                return values;
        }

        public void setValues(String[] values) {
                this.values = values;
        }
}
