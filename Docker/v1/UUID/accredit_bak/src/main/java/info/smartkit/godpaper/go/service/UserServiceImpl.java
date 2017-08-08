package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.settings.AIProperties;
import info.smartkit.godpaper.go.settings.MqttProperties;
import info.smartkit.godpaper.go.settings.UserStatus;
import info.smartkit.godpaper.go.settings.UserTypes;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by smartkit on 16/06/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired AIProperties aiProperties;

    @Autowired MqttService mqttService;
    @Autowired DockerService dockerService;
    @Autowired MqttProperties mqttProperties;

    private static Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public List<User> createRandomUsers(int numbers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            User user = new User();
            user.setEmail(RandomStringUtils.randomAlphanumeric(7).toLowerCase().concat("@toyhouse.cc"));
            user.setName(RandomStringUtils.randomAlphanumeric(4).toUpperCase().concat(".").concat(RandomStringUtils.randomAlphanumeric(5).toUpperCase()));
            //Random types
            int size_types = UserTypes.values().length;
            int rType = getRandomNumberInRange(0,size_types-1);
            user.setType(rType);
            if(rType==UserTypes.AI.getIndex()) {
                //Random rank
                int size_ranks = aiProperties.getPolicys().size();
                int rRank = getRandomNumberInRange(0, size_ranks - 1);
                user.setRank(String.valueOf(rRank));
                //Random policy
                int size_policy = aiProperties.getPolicys().size();
                int rPolicy = getRandomNumberInRange(0, size_policy - 1);
                String policy = aiProperties.getPolicys().get(rPolicy);
                user.setPolicy(policy);
            }
            //
            User saved = repository.save(user);
            users.add(saved);
        }
        return users;
    }

    @Override public void tenant(User updater) throws MqttException, DockerException, InterruptedException {

        LOG.info("tenantedUser:"+updater.toString());
        //MqttClient tenant
        //produce message topic by uuid
        mqttService.connect(mqttProperties.getBrokerUrl(), updater.getTopicName());
        //and subscribe
        mqttService.subscribe(updater.getTopicName());
        //
        //TODO:ChainCode register
        //        chainCodeService.createRegistrar("jim", "6avZQLwcUe9b");
    }

    @Override public void untenant(User updater) throws MqttException, DockerException, InterruptedException {
        //MqttClient untenant
        //produce message topic by uuid
        //                mqttService.disconnect(mqttProperties.getBrokerUrl(), updated.getTopicName());
        //and unsubscribe
        mqttService.unsubscribe(updater.getTopicName());
        //
        //TODO:ChainCode un-register
        //        chainCodeService.createRegistrar("jim", "6avZQLwcUe9b");

        //Container related.
        dockerService.stopContainer(updater.getId(),10);
        dockerService.removeContainer(updater.getId());
    }
}
