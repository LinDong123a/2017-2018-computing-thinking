package info.smartkit.godpaper.go.pojo;

/**
 * Created by smartkit on 16/06/2017.
 */
public class MessageResponse {
    private String chatBotName;
    private int chessBotID;
    private String message;
    private String emotion;

    public MessageResponse(String chatBotName, int chatBotID, String message, String emotion) {
        this.chatBotName = chatBotName;
        this.chessBotID = chatBotID;
        this.message = message;
        this.emotion = emotion;
    }

    public MessageResponse() {
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public int getChessBotID() {
        return chessBotID;
    }

    public void setChessBotID(int chessBotID) {
        this.chessBotID = chessBotID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "chatBotName='" + chatBotName + '\'' +
                ", chessBotID=" + chessBotID +
                ", message='" + message + '\'' +
                ", emotion='" + emotion + '\'' +
                '}';
    }
}
