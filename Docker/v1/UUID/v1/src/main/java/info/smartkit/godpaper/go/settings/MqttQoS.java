package info.smartkit.godpaper.go.settings;

/**
 * Created by smartkit on 23/06/2017.
 */
public enum MqttQoS {
        MOST_ONCE("atMostOnce", 0), LEAST_ONCE("atLeastOnce", 1), EXCATLY_ONCE("excatly once", 2);

        MqttQoS(String name, int index) {
                this.name = name;
                this.index = index;
        }

        private String name;
        private int index;

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

        @Override public String toString() {
                return "MqttQoS{" + "name='" + name + '\'' + ", index=" + index + '}';
        }
}

