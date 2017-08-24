package info.smartkit.godpaper.go.dto;

public class PlayMessage {


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    private String game_id;
    private String user_id;
    private String msg;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "PlayMessage{" +
                "game_id='" + game_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", msg='" + msg + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

    public PlayMessage(String game_id, String user_id, String msg, String method) {
        this.game_id = game_id;
        this.user_id = user_id;
        this.msg = msg;
        this.method = method;
    }

    private String method;

    public PlayMessage() {
    }


}
