package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.pojo.User;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

/**
 * Created by smartkit on 16/06/2017.
 */
public interface UserService {
        List<User> createRandomUsers(int numbers);
        User tenant() throws MqttException, DockerException, InterruptedException;
        User untenant(String userId) throws MqttException, DockerException, InterruptedException;
}
