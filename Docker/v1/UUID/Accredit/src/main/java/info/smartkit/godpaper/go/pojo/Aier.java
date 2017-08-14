package info.smartkit.godpaper.go.pojo;

import info.smartkit.godpaper.go.settings.AierStatus;
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

        @Override public String toString() {
                return "Aier{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", model='" + model + '\'' + ", files='" + files + '\'' + ", created=" + created + ", status=" + status + ", gid='" + gid + '\'' + '}';
        }

        public String getFiles() {
                return files;
        }

        public void setFiles(String files) {
                this.files = files;
        }

        private String files;//ai model files absolute path.
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private Date created = new Date();

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        private int status= AierStatus.STANDBY.getIndex();

        public String getGid() {
                return gid;
        }

        public void setGid(String gid) {
                this.gid = gid;
        }

        private String gid;//related gamer id.

}
