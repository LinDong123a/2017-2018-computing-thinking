package info.smartkit.godpaper.go.controller;

import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.ChainCodeService;
import info.smartkit.godpaper.go.service.MqttService;
import info.smartkit.godpaper.go.service.UserService;
import info.smartkit.godpaper.go.settings.ChainCodeProperties;
import info.smartkit.godpaper.go.settings.MqttProperties;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
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
    @Autowired ChainCodeProperties chainProperties;

    @Autowired MqttService mqttService;

    @Autowired MqttProperties mqttProperties;

    @RequestMapping(method = RequestMethod.POST)
    public User createOne(@RequestBody User user){
        User result = repository.save(user);
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
    public void deleteByUserId(@PathVariable String userId){
            repository.delete(userId);
    }


        @RequestMapping(method = RequestMethod.DELETE, value="/")
        public void deleteAll(){
                repository.deleteAll();
        }

    @RequestMapping(method = RequestMethod.DELETE, value="/status/{index}")
    public void deleteByStatus(@PathVariable int index){
            List<User> statusUsers = repository.findByStatus(index);
            repository.delete(statusUsers);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> listAll(){
            return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET,value="/status/{index}")
    public List<User> listByStatus(@PathVariable int index){
            return repository.findByStatus(index);
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
            //produce message topic by uuid
            mqttService.connect(mqttProperties.getBrokerUrl(), updated.getTopicName());
            //and subscribe
            mqttService.subscribe(updated.getTopicName());
            //
            //TODO:ChainCode register
            //        chainCodeService.createRegistrar("jim", "6avZQLwcUe9b");
            return updated;
    }
        @RequestMapping(method = RequestMethod.DELETE, value="/tenant/{userId}")
        public User untenant(@PathVariable String userId) throws MqttException {
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
                return updated;
        }

}
