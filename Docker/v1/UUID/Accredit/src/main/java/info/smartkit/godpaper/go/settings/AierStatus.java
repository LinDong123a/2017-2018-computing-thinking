package info.smartkit.godpaper.go.settings;

/**
 * Created by smartkit on 12/07/2017.
 */
public enum AierStatus {

        STANDBY("standby", 0), TRAINING("standby", 1),TRAINED("trained",2);

        AierStatus(String name, int index) {
                this.name = name;
                this.index = index;
        }

        @Override public String toString() {
                return "AierStatus{" + "name='" + name + '\'' + ", index=" + index + '}';
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getIndex() {
                return index;
        }

        public void setIndex(int index) {
                this.index = index;
        }

        private String name;
        private int index;
}
