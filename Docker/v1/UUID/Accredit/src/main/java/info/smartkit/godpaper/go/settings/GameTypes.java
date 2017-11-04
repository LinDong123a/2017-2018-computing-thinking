package info.smartkit.godpaper.go.settings;

public enum GameTypes {
    //
    AI_VS_AI("AI_VS_AI", 0), AI_VS_HUMAN("AI_VS_HUMAN", 1),  HUMAN_VS_AI("HUMAN_VS_AI", 2), HUMAN_VS_HUMAN("HUMAN_VS_HUMAN", 3);

    GameTypes(String name, int index) {
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
        return "GameTypes{" + "name='" + name + '\'' + ", index=" + index + '}';
    }
}
