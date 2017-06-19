package info.smartkit.godpaper.go.controller;

import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by smartkit on 16/06/2017.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired UserRepository repository;

    @Autowired UserService service;

    @RequestMapping(method = RequestMethod.POST)
    public User createOne(@RequestBody User user){
        User result = repository.save(user);
        //produce message topic by uuid
        ActivemqSender sender = new ActivemqSender(ActivemqVariables.channelName+result.getId());
        sender.sendMessage("echo");//For CREATE.
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

    @RequestMapping(method = RequestMethod.GET)
    public List<User> list(){
        return repository.findAll();
    }
}
