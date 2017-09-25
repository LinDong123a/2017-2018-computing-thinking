package info.smartkit.godpaper.go.dto;

public class SgfMessage {

    private final String sgf;

    public SgfMessage(String sgf) {
        this.sgf = sgf;
    }

    public String getSgf() {
        return sgf;
    }
}
