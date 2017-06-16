package info.smartkit.godpaper.go.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public User(String id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}

