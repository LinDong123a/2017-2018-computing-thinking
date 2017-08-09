package info.smartkit.godpaper.go.settings;

/**
 * Created by smartkit on 22/06/2017.
 */
public enum GameStatus {

        STANDBY("standby", 0), PAIRED("paired", 1), PLAYING("playing", 2), ENDED("ended", 3),SAVED("saved", 4);

        GameStatus(String name, int index) {
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
                return "GameStatus{" + "name='" + name + '\'' + ", index=" + index + '}';
        }
}
