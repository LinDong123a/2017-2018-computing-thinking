package info.smartkit.godpaper.go.dto;

/**
 * Created by smartkit on 03/07/2017.
 */
public class SgfDto {
        private String cmd;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        private String name;

        public SgfDto() {
        }

        public String getCmd() {
                return cmd;
        }

        public void setCmd(String cmd) {
                this.cmd = cmd;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        private String url;

        public String getLocal() {
                return local;
        }

        public void setLocal(String local) {
                this.local = local;
        }

        private String local;

        @Override public String toString() {
                return "SgfDto{" + "cmd='" + cmd + '\'' + ", name='" + name + '\'' + ", url='" + url + '\'' + ", local='" + local + '\'' + ", result='" + result + '\'' + '}';
        }

        public String getResult() {
                return result;
        }

        public void setResult(String result) {
                this.result = result;
        }

        private String result;

}
