package info.smartkit.godpaper.go.pojo;

import info.smartkit.godpaper.go.settings.MqttVariables;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.springframework.data.annotation.Id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private String name;
    private String policy;
    private int score;


    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    private String rank="0";//rank of game;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status= UserStatus.STANDBY.getIndex();//0:untenanted,3:playing,2:standby,2:tenanted

    public String getTopicName() {
        return MqttVariables.clientId+id;
    }

    @Override public String toString() {
        return "User{" + "id='" + id + '\'' + ", email='" + email + '\'' + ", name='" + name + '\'' + ", policy='" + policy + '\'' + ", score=" + score + ", rank=" + rank + ", status=" + status + ", created=" + created + '}';
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


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date created = new Date();

}

