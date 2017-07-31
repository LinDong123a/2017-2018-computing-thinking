package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.dto.SgfDto;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.List;

/**
 * Created by smartkit on 22/06/2017.
 */
public interface GamerService {
        List<Gamer> pairAll(List<User> tenantedUsers) throws MqttException;
        List<Gamer> playAll() throws MqttException, DockerException, InterruptedException, IOException;
        Gamer playOne(String gamerId) throws MqttException, DockerException, InterruptedException, IOException;
        SgfDto saveSgf(Gamer gamer) throws IOException, DockerException, InterruptedException;
        String getSgfResult(Gamer gamer) throws IOException, DockerException, InterruptedException;
        void createFolder(String name) throws IOException;
        void deleteFolder(String name) throws IOException;
        void rPlayNum(int gamerNum) throws InterruptedException, DockerException, MqttException, IOException;
}
