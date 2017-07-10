package info.smartkit.godpaper.go.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by smartkit on 09/07/2017.
 */
@Document(collection = "aiers")
public class Aier {
        public Aier() {
        }

        public String getId() {
                return id;
        }

        @Id
        private String id;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getModel() {
                return model;
        }

        public void setModel(String model) {
                this.model = model;
        }

        public Date getCreated() {
                return created;
        }

        public void setCreated(Date created) {
                this.created = created;
        }

        private String name;
        private String model;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private Date created = new Date();

        @Override public String toString() {
                return "Aier{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", model='" + model + '\'' + ", created=" + created + '}';
        }
}
