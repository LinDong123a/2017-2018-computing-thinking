package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.dto.SgfDto;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.projectodd.stilts.stomp.StompException;

import javax.jms.JMSException;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by smartkit on 22/06/2017.
 */
public interface GamerService {
        List<Gamer> pairAll(List<User> tenantedUsers) throws MqttException;
        List<Gamer> playAll() throws MqttException, DockerException, InterruptedException, IOException;
        Gamer playOne(String gamerId) throws MqttException, DockerException, InterruptedException, IOException;
        SgfDto saveSgf(Gamer gamer,boolean fixInvalidMove) throws IOException, DockerException, InterruptedException;
        String getSgfResult(Gamer gamer) throws IOException, DockerException, InterruptedException;
        void createFolder(String name) throws IOException;
        void deleteFolder(String name) throws IOException;
        void randomPlaySome(int gamerNum) throws InterruptedException, DockerException, MqttException, IOException;
        SgfDto updateSgf(String gamerId,String resultStr) throws IOException, InterruptedException, DockerException;
        void connectHumanPlayer(Gamer gamer) throws InterruptedException, SSLException, URISyntaxException, TimeoutException, JMSException, StompException;
        String getSgfHeader(String application,String version,Gamer gamer,String result);
        void createGamerByType(int type,String name);
}
