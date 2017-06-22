package info.smartkit.godpaper.go.controller;

import info.smartkit.UUIDAccreditApplication;
import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.GamerService;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by smartkit on 22/06/2017.
 */
@RestController
@RequestMapping("/game")
public class GameController {
        //
        private static Logger LOG = LogManager.getLogger(GameController.class);

        @Autowired GamerRepository repository;
        @Autowired UserRepository userRepository;
        @Autowired GamerService service;
        @RequestMapping(method = RequestMethod.POST)
        public Gamer createOne(@RequestBody Gamer gamer){
                Gamer result = repository.save(gamer);
                return result;
        }
        @RequestMapping(method = RequestMethod.GET,value="/pair")
        public List<Gamer> pairAll()
        {
                List<User> standbyUsers = userRepository.findByStatus(UserStatus.STANDBY.getIndex());
                LOG.info("standbyUsers:"+standbyUsers.toString());
                //
                List<Gamer> pairedGames = service.pairAll(standbyUsers);
                LOG.info("pairedGames:"+pairedGames.toString());
                return pairedGames;
        }

        @RequestMapping(method = RequestMethod.GET,value="/play")
        public List<Gamer> playAll()
        {
                return service.playAll();
        }

        @RequestMapping(method = RequestMethod.GET, value="/{gamerId}")
        public Gamer get(@PathVariable String gamerId){
                return repository.findOne(gamerId);
        }

        @RequestMapping(method = RequestMethod.GET)
        public List<Gamer> listAll(){
                return repository.findAll();
        }

        @RequestMapping(method = RequestMethod.GET, value="/saved")
        public List<Gamer> listSaved(){
                return repository.findByStatus(GameStatus.SAVED.getIndex());
        }
}
