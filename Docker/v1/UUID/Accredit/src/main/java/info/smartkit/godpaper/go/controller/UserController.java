package info.smartkit.godpaper.go.controller;

import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.ChainCodeService;
import info.smartkit.godpaper.go.service.MqttService;
import info.smartkit.godpaper.go.service.UserService;
import info.smartkit.godpaper.go.settings.ChainProperties;
import info.smartkit.godpaper.go.settings.MqttProperties;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by smartkit on 16/06/2017.
 */
@RestController
@RequestMapping("/user")
public class UserController {
        private static Logger LOG = LogManager.getLogger(UserController.class);
    @Autowired UserRepository repository;

    @Autowired UserService service;
    @Autowired ChainCodeService chainCodeService;
    //
    @Autowired ChainProperties chainProperties;

    @Autowired MqttService mqttService;

    @Autowired MqttProperties mqttProperties;

    @RequestMapping(method = RequestMethod.POST)
    public User createOne(@RequestBody User user){
        User result = repository.save(user);
        //produce message topic by uuid
        ActivemqSender sender = new ActivemqSender(ActivemqVariables.channelName+result.getId());
        sender.sendMessage("echo");//For CREATE.
        //ChainCode register
//        chainCodeService.createRegistrar("jim", "6avZQLwcUe9b");
        //
        return result;
    }


    @RequestMapping(method = RequestMethod.GET, value="/r/{numbers}")
    public List<User>  createRandomUsers(@PathVariable int numbers)
    {
        return service.createRandomUsers(numbers);
    }

    @RequestMapping(method = RequestMethod.GET, value="/{userId}")
    public User get(@PathVariable String userId){
            return repository.findOne(userId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{userId}")
    public void delete(@PathVariable String userId){
            repository.delete(userId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/status/{index}")
    public void delete(@PathVariable int index){
            List<User> statusUsers = repository.findByStatus(index);
            repository.delete(statusUsers);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> list(){
            return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/tenant")
    public User tenant() throws MqttException {
            //User tenant
            User untenantedOne = repository.findByStatus(UserStatus.unTENANTED.getIndex()).get(0);
            //update status
            untenantedOne.setStatus(UserStatus.TENANTED.getIndex());
            User updated = repository.save(untenantedOne);
            LOG.info("tenantedUser:"+updated.toString());
            //MqttClient tenant
            mqttService.connect(mqttProperties.getBrokerUrl(), updated.getTopicName());
            //
            mqttService.subscribe(updated.getTopicName());
            //
//            TimeUnit.SECONDS.sleep(1);
//            mqttService.publish(updated.getTopicName(),"echo", MqttQoS.LEAST_ONCE.getIndex());
            return updated;
    }

}
