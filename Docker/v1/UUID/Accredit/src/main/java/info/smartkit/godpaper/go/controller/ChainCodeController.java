package info.smartkit.godpaper.go.controller;

import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.service.ChainCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by smartkit on 21/06/2017.
 */
@RestController
@RequestMapping("/chain")
public class ChainCodeController {
        @Autowired ChainCodeService chainCodeService;
        @RequestMapping(method = RequestMethod.POST)
        public void createOne(@RequestBody User user){

        }

        @RequestMapping(method = RequestMethod.GET, value="/{enrollId}")
        public User get(@PathVariable String enrollId){
                chainCodeService.getRegistrar(enrollId);
        }


        @RequestMapping(method = RequestMethod.DELETE, value="/{enrollId}/{enrollSecret}")
        public List<User> delete(@PathVariable String userId,@PathVariable String enrollSecret){
                chainCodeService.deleteRegistrar(userId,enrollSecret);
        }
}
