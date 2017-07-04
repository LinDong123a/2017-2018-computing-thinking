package info.smartkit.godpaper.go.dto;

/**
 * Created by smartkit on 03/07/2017.
 */
public class SgfDto {
        private String cmd;

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

        public SgfDto(String cmd, String url) {
                this.cmd = cmd;
                this.url = url;
        }

        @Override public String toString() {
                return "SgfDto{" + "cmd='" + cmd + '\'' + ", url='" + url + '\'' + '}';
        }
}
