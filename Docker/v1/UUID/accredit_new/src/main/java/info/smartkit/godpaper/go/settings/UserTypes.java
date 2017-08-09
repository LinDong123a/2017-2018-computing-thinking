package info.smartkit.godpaper.go.settings;

/**
 * Created by smartkit on 03/08/2017.
 */
public enum UserTypes {
        //
        AI("AI", 0), HUMAN("HUMAN", 1);

        UserTypes(String name, int index) {
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
                return "UserTypes{" + "name='" + name + '\'' + ", index=" + index + '}';
        }
}
