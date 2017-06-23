package info.smartkit.godpaper.go.pojo;

import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by smartkit on 16/06/2017.
 * @see: https://github.com/blockstack-packages/blockchain-id-deprecated/wiki/Blockchain-ID-Wikipedia
 */
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String fullName;

    public User(String id, String email, String fullName, int rank, int status) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.rank = rank;
        this.status = status;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override public String toString() {
        return "User{" + "id='" + id + '\'' + ", email='" + email + '\'' + ", fullName='" + fullName + '\'' + ", rank=" + rank + ", status=" + status + ", topicName='" + getTopicName() + '\'' + ", created=" + created + '}';
    }

    private int rank=0;//rank of game;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status= UserStatus.unTENANTED.getIndex();//0:untenanted,3:playing,2:standby,2:tenanted

    public String getTopicName() {
        return ActivemqVariables.channelName+id;
    }


    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date created = new Date();

}

