package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.settings.AIProperties;
import info.smartkit.godpaper.go.settings.MqttProperties;
import info.smartkit.godpaper.go.settings.UserStatus;
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


    @Override
    public List<User> createRandomUsers(int numbers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            User user = new User();
            user.setEmail(RandomStringUtils.randomAlphanumeric(7).toLowerCase().concat("@toyhouse.cc"));
            user.setName(RandomStringUtils.randomAlphanumeric(4).toUpperCase().concat(".").concat(RandomStringUtils.randomAlphanumeric(5).toUpperCase()));
            //Random rank
            Random rank_generator = new Random();
            int rRank = aiProperties.getRanks() - rank_generator.nextInt(aiProperties.getRanks());
            user.setRank(rRank);
            //Random policy
            Random policy_generator = new Random();
            int size_policy = aiProperties.getPolicys().size();
            int rPolicy = size_policy - policy_generator.nextInt(size_policy);
            String policy = aiProperties.getPolicys().get(rPolicy);
            user.setPolicy(policy);
            User saved = repository.save(user);
            users.add(saved);
        }
        return users;
    }

    @Override public User tenant() throws MqttException, DockerException, InterruptedException {
        //User tenant
        User untenantedOne = repository.findByStatus(UserStatus.unTENANTED.getIndex()).get(0);
        //update status
        untenantedOne.setStatus(UserStatus.TENANTED.getIndex());
        User updated = repository.save(untenantedOne);
        LOG.info("tenantedUser:"+updated.toString());
        //MqttClient tenant
        //produce message topic by uuid
        mqttService.connect(mqttProperties.getBrokerUrl(), updated.getTopicName());
        //and subscribe
        mqttService.subscribe(updated.getTopicName());
        //
        //TODO:ChainCode register
        //        chainCodeService.createRegistrar("jim", "6avZQLwcUe9b");
        return updated;
    }

    @Override public User untenant(String userId) throws MqttException, DockerException, InterruptedException {
        //User tenant
        User tenantUeser = repository.findOne(userId);
        //update status
        tenantUeser.setStatus(UserStatus.unTENANTED.getIndex());
        User updated = repository.save(tenantUeser);
        LOG.info("untenantedUser:"+updated.toString());
        //MqttClient untenant
        //produce message topic by uuid
        //                mqttService.disconnect(mqttProperties.getBrokerUrl(), updated.getTopicName());
        //and unsubscribe
        mqttService.unsubscribe(updated.getTopicName());
        //
        //TODO:ChainCode un-register
        //        chainCodeService.createRegistrar("jim", "6avZQLwcUe9b");

        //Container related.
        dockerService.stopContainer(updated.getId(),1);
        dockerService.removeContainer(updated.getId());
        return updated;
    }
}
