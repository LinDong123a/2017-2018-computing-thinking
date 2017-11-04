package info.smartkit.godpaper.go.settings;

/**
 * Created by smartkit on 11/09/2017.
 */
public enum AiPolicies {
        //
        BEST_MOVE("best_move", 0), RANDOM("random", 1),RANDOM_MOVE("random_move", 2);

        AiPolicies(String name, int index) {
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
                return "AiPolicies{" + "name='" + name + '\'' + ", index=" + index + '}';
        }
}
