package info.smartkit.godpaper.go.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by smartkit on 22/06/2017.
 */
@Document(collection = "gamers")
public class Gamer {
        @Override public String toString() {
                return "Gamer{" + "id='" + id + '\'' + ", player1=" + player1 + ", player2=" + player2 + ", sgf='" + sgf + '\'' + ", created=" + created + '}';
        }

        public String getId() {
                return player1.getId()+"_"+player2.getId();
        }

        public void setId(String id) {
                this.id = id;
        }

        @Id
        private String id;
        public Gamer() {
        }

        public Gamer(User player1, User player2, String sgf) {
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

}
