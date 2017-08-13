package info.smartkit.godpaper.go.pojo;

/**
 * Created by smartkit on 22/06/2017.
 */
public class Enroller {
        public Enroller() {
        }

        public Enroller(String id, String secret) {
                this.id = id;
                this.secret = secret;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getSecret() {
                return secret;
        }

        public void setSecret(String secret) {
                this.secret = secret;
        }

        private String id;
        private String secret;

        @Override public String toString() {
                return "Enroller{" + "id='" + id + '\'' + ", secret='" + secret + '\'' + '}';
        }
}
