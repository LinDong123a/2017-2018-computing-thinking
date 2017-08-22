package info.smartkit.godpaper.go.dto;

public class PlayMessage {
    private String v_game_id;
    private String v_player_id;
    private String msg;

    public PlayMessage() {
    }

    public PlayMessage(String v_game_id, String v_player_id, String msg) {

        this.v_game_id = v_game_id;
        this.v_player_id = v_player_id;
        this.msg = msg;
    }

    public String getV_game_id() {
        return v_game_id;
    }

    public void setV_game_id(String v_game_id) {
        this.v_game_id = v_game_id;
    }

    public String getV_player_id() {
        return v_player_id;
    }

    public void setV_player_id(String v_player_id) {
        this.v_player_id = v_player_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PlayMessage{" +
                "v_game_id='" + v_game_id + '\'' +
                ", v_player_id='" + v_player_id + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
