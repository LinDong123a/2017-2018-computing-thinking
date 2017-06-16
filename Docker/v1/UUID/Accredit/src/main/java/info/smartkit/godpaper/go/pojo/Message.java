package info.smartkit.godpaper.go.pojo;

/**
 * Created by smartkit on 16/06/2017.
 * @see:
 */
public class Message {
    private String message;
    private int chessBotID;
    private int timestamp;

    public Message(String message, int chatBotID, int timestamp) {
        this.message = message;
        this.chessBotID = chatBotID;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", chessBotID=" + chessBotID +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getChessBotID() {
        return chessBotID;
    }

    public void setChessBotID(int chatbotID) {
        this.chessBotID = chatbotID;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
