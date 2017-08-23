package info.smartkit.godpaper.go.controller;


import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.dto.SgfDto;
import info.smartkit.godpaper.go.dto.SgfObj;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.AierRepository;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.DockerService;
import info.smartkit.godpaper.go.service.GamerService;
import info.smartkit.godpaper.go.service.MqttService;
import info.smartkit.godpaper.go.service.StompService;
import info.smartkit.godpaper.go.settings.*;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.projectodd.stilts.stomp.StompException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.jms.JMSException;
import javax.net.ssl.SSLException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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
        @Autowired AierRepository aierRepository;
        @Autowired GamerRepository gamerRepository;
        @Autowired StompService stompService;
        @Autowired
        MqttProperties mqttProperties;
        @Autowired ChainCodeProperties chainCodeProperties;

        @RequestMapping(method = RequestMethod.POST)
        public Gamer createOne(@RequestBody Gamer gamer) throws IOException, InterruptedException, URISyntaxException, TimeoutException, StompException, JMSException {
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
        public List<Gamer> playAll() throws MqttException, DockerException, InterruptedException, IOException {
                return service.playAll();
        }

        @RequestMapping(method = RequestMethod.GET,value="/play/{gamerId}")
        public Gamer playOne(@PathVariable String gamerId) throws Exception {
                //
                return service.playOne(gamerId);
        }

        @RequestMapping(method = RequestMethod.GET, value="/{gamerId}")
        public Gamer get(@PathVariable String gamerId){
                return repository.findOne(gamerId);
        }

        @RequestMapping(method = RequestMethod.GET)
        public List<Gamer> listAll() throws InterruptedException, SSLException, URISyntaxException, TimeoutException, JMSException, StompException {
                List<Gamer> all = repository.findAllByOrderByCreatedDesc();
                for(Gamer gamer:all) {
                        //stomp get ready if has any human player.
                        if (hasHumanPlayer(gamer)) {
                                service.connectHumanPlayer(gamer);
                        }
                }
                return all;
        }

        @RequestMapping(method = RequestMethod.GET, value="/saved")
        public List<Gamer> listSaved(){
                return repository.findByStatus(GameStatus.SAVED.getIndex());
        }


        @RequestMapping(method = RequestMethod.DELETE, value="/{gamerId}")
        public void deleteOne(@PathVariable String gamerId) throws MqttException, IOException, StompException {
                //Dismiss gamer's user
                Gamer gamer = repository.findOne(gamerId);
                //mqtt unsubscribe
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
                //stomp unsubscribe
                if(hasHumanPlayer(gamer)){
                        if(stompService.isConnected()) {
                                stompService.unsubscribe();
                        }
                }
        }
        @RequestMapping(method = RequestMethod.DELETE, value="/")
        public void deleteAll() throws MqttException, StompException {
                List<Gamer> allGamers = repository.findAll();
                for(Gamer gamer : allGamers){
                        //unsubscribe
                        mqttService.unsubscribe(gamer.getTopic());
                        //stomp unsubscribe
                        if(hasHumanPlayer(gamer)){
                                stompService.unsubscribe();
                        }
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

        @RequestMapping(method = RequestMethod.GET, value="/result/{gamerId}")
        public String getResultById(@PathVariable String gamerId) throws Exception {
                //
                Gamer gamer = repository.findOne(gamerId);
//                if(gamer.getStatus()!=GameStatus.SAVED.getIndex()){
                if(gamer.getResult()==null){
                        return service.getSgfResult(gamer);
                }
                return gamer.getResult().trim();
        }

        @RequestMapping(method = RequestMethod.GET,value="/play/r/{gamerNum}")
        public void playOne(@PathVariable int gamerNum) throws MqttException, DockerException, InterruptedException, IOException {
                service.randomPlaySome(gamerNum);
        }

        @RequestMapping(method = RequestMethod.GET, value="/sse/sgf/{gamerId}")
        public SseEmitter sseGetSgfById(@PathVariable String gamerId,HttpSession session) {

                SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

                Thread t1 = new Thread(() ->{
                        try {
                                int i = 0;
                                while(++i<=10000){
                                        Thread.sleep(10000);
                                        System.out.println("SSE sgf Sending....");
                                        try{
                                                Gamer gamer = gamerRepository.findOne(gamerId);
                                                if(gamer!=null) {
                                                        emitter.send(gamer.getSgf());
                                                }else{
                                                        LOG.warn("Not found with this gamerId.");
                                                }
                                        }catch(ClientAbortException cae){
                                                cae.printStackTrace();
                                                i = 10000;
                                        }
                                }
                                emitter.complete();
                        } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                        }
                });
                t1.start();

                return emitter;
        }

        @RequestMapping(method = RequestMethod.PUT, value="/sgf/{gamerId}")
        public SgfObj updateSgfById(@RequestBody SgfObj sgfObj, @PathVariable String gamerId) {
                Gamer gamer  = repository.findOne(gamerId);
                String sgfStrUpdate = gamer.getSgf();
                String sgfHeader  = service.getSgfHeader(chainCodeProperties.getChainName(),"0.0.1",gamer,"B?R");
                String sgfBody  = sgfObj.getBody();
                sgfStrUpdate = sgfHeader.concat(sgfBody);
                //
                gamer.setSgf(sgfStrUpdate);
                Gamer updater = repository.save(gamer);
                //post to simple AI server.
                RestTemplate restTemplate = new RestTemplate();
                Map<String, String> vars = new HashMap<String, String>();
                vars.put("gamer_id", gamerId);
                vars.put("user_id", gamer.getPlayer2().getId());
                vars.put("msg", sgfObj.getBody());
                HttpHeaders headers =new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> request = new HttpEntity<>(vars,headers);
                ResponseEntity<Object> response = restTemplate
                        .exchange("http://"+mqttProperties.getIp()+":6000/", HttpMethod.POST, request, Object.class);
                LOG.debug("response:"+response.toString());
                //
                //
                SgfObj sgfObjUpdate = new SgfObj(sgfHeader,sgfBody);
                return sgfObjUpdate;
        }

        private boolean hasHumanPlayer(Gamer gamer) {
                return
                (gamer.getPlayer1().getType() == UserTypes.HUMAN.getIndex() || gamer.getPlayer2().getType() == UserTypes.HUMAN.getIndex());
        }


}
