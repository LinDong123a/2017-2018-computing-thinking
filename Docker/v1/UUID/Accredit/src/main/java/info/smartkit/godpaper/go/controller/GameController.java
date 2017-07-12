package info.smartkit.godpaper.go.controller;


import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.dto.SgfDto;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.DockerService;
import info.smartkit.godpaper.go.service.GamerService;
import info.smartkit.godpaper.go.service.MqttService;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        @Autowired MqttService mqttService;
        @Autowired DockerService dockerService;

        @RequestMapping(method = RequestMethod.POST)
        public Gamer createOne(@RequestBody Gamer gamer) throws IOException {
                Gamer result = repository.save(gamer);
                //gamer folder creating.
                service.createFolder(result.getId());
                return result;
        }
        @RequestMapping(method = RequestMethod.GET,value="/pair")
        public List<Gamer> pairAll() throws MqttException {
                List<User> tenantedUsers = userRepository.findByStatus(UserStatus.TENANTED.getIndex());
                LOG.info("tenantedUsers("+tenantedUsers.size()+"):"+tenantedUsers.toString());
                //
                List<Gamer> pairedGames = service.pairAll(tenantedUsers);
                LOG.info("pairedGames("+pairedGames.size()+"):"+pairedGames.toString());
                return pairedGames;
        }

        @RequestMapping(method = RequestMethod.GET,value="/play")
        public List<Gamer> playAll() throws MqttException, DockerException, InterruptedException {
                return service.playAll();
        }

        @RequestMapping(method = RequestMethod.GET,value="/play/{gamerId}")
        public Gamer playOne(@PathVariable String gamerId) throws MqttException, DockerException, InterruptedException {
                return service.playOne(gamerId);
        }

        @RequestMapping(method = RequestMethod.GET, value="/{gamerId}")
        public Gamer get(@PathVariable String gamerId){
                return repository.findOne(gamerId);
        }

        @RequestMapping(method = RequestMethod.GET)
        public List<Gamer> listAll(){
                return repository.findAllByOrderByCreatedDesc();
        }

        @RequestMapping(method = RequestMethod.GET, value="/saved")
        public List<Gamer> listSaved(){
                return repository.findByStatus(GameStatus.SAVED.getIndex());
        }


        @RequestMapping(method = RequestMethod.DELETE, value="/{gamerId}")
        public void deleteOne(@PathVariable String gamerId) throws MqttException, IOException {
                //Dismiss gamer's user
                Gamer gamer = repository.findOne(gamerId);
                //unsubscribe
                mqttService.unsubscribe(gamer.getTopic());
                User player1 = gamer.getPlayer1();
                player1.setStatus(UserStatus.unTENANTED.getIndex());
                userRepository.save(player1);
                User player2 = gamer.getPlayer2();
                player2.setStatus(UserStatus.unTENANTED.getIndex());
                userRepository.save(player2);
                //
                repository.delete(gamerId);
                //gamer folder deleting.
                service.deleteFolder(gamerId);
        }
        @RequestMapping(method = RequestMethod.DELETE, value="/")
        public void deleteAll() throws MqttException {
                List<Gamer> allGamers = repository.findAll();
                for(Gamer gamer : allGamers){
                        //unsubscribe
                        mqttService.unsubscribe(gamer.getTopic());
                        //
                        User player1 = gamer.getPlayer1();
                        player1.setStatus(UserStatus.unTENANTED.getIndex());
                        userRepository.save(player1);
                        User player2 = gamer.getPlayer2();
                        player2.setStatus(UserStatus.unTENANTED.getIndex());
                        userRepository.save(player2);
                }
                //
                repository.deleteAll();
        }

        @RequestMapping(method = RequestMethod.GET, value="/sgf/{gamerId}")
        public SgfDto saveSgfById(@PathVariable String gamerId) throws IOException, DockerException, InterruptedException {
                Gamer gamer = repository.findOne(gamerId);
                return service.saveSgf(gamer,true);
        }

        @RequestMapping(method = RequestMethod.GET, value="/score/{gamerId}")
        public String getScoreById(@PathVariable String gamerId) throws IOException, DockerException, InterruptedException {
                //
                Gamer gamer = repository.findOne(gamerId);
                if(gamer.getStatus()!=GameStatus.SAVED.getIndex()){
                        service.saveSgf(gamer,true);
                }
                //
                return dockerService.runScorer(gamerId);
        }

        @RequestMapping(method = RequestMethod.GET,value="/run/player/{userId}")
        public void runPlayer(@PathVariable String userId) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                dockerService.runPlayer(userId);
        }

        @RequestMapping(method = RequestMethod.GET,value="/run/agent/{name}")
        public String runAgent(@PathVariable String name) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                return dockerService.runAgent(name);
        }
}
