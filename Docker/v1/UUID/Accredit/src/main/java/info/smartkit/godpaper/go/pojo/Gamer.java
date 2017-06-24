package info.smartkit.godpaper.go.pojo;

import info.smartkit.godpaper.go.settings.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by smartkit on 22/06/2017.
 */
@Document(collection = "gamers")
public class Gamer {

        public String getName() {
                return player1.getId()+"_vs_"+player2.getId();
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getId() {
                return id;
        }

        public Date getCreated() {
                return created;
        }

        @Id
        private String id;

        public void setName(String name) {
                this.name = name;
        }

        private String name;
        public Gamer() {
        }

        public Gamer(String name, User player1, User player2, String sgf) {
                this.name = name;
                this.player1 = player1;
                this.player2 = player2;
                this.sgf = sgf;
        }

        private User player1;
        private User player2;
        private String sgf;//status

        public User getPlayer1() {
                return player1;
        }

        public void setPlayer1(User player1) {
                this.player1 = player1;
        }

        public User getPlayer2() {
                return player2;
        }

        public void setPlayer2(User player2) {
                this.player2 = player2;
        }

        public String getSgf() {
                return sgf;
        }

        public void setSgf(String sgf) {
                this.sgf = sgf;
        }

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private Date created = new Date();

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        private int status= UserStatus.STANDBY.getIndex();//1:playing,0:standby

        @Override public String toString() {
                return "Gamer{" + "id='" + id + '\'' + ", player1=" + player1 + ", player2=" + player2 + ", sgf='" + sgf + '\'' + ", created=" + created + ", status=" + status + '}';
        }
}
