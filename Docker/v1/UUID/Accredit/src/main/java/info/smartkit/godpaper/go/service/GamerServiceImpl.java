package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.MqttQoS;
import info.smartkit.godpaper.go.settings.MqttVariables;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartkit on 22/06/2017.
 */
@Service
public class GamerServiceImpl implements GamerService {

        private static Logger LOG = LogManager.getLogger(GamerServiceImpl.class);;

        @Autowired GamerRepository gamerRepository;
        @Autowired MqttService mqttService;

        @Override public List<Gamer> pairAll(List<User> tenantedUsers) throws MqttException {
                int arraySize = tenantedUsers.size();
                LOG.info("tenantedUsers("+tenantedUsers.size()+"):"+tenantedUsers.toString());
                List<User> firstPart = tenantedUsers.subList(0,arraySize/2);
                List<User> secondPart = tenantedUsers.subList(arraySize/2,arraySize);
                List<Gamer> gamers = new ArrayList<>(arraySize/2);
                for (int i=0;i<arraySize/2;i++) {
                        //produce message topic by uuid,for public message.
                        User player1 = firstPart.get(i);
                        User player2 = secondPart.get(i);
                        String vsTitle =  player1.getTopicName()+ MqttVariables.tag_vs+player2.getTopicName();
                        List<Gamer> existedGamers = gamerRepository.findByName(vsTitle);
                        LOG.info("existedGamers("+existedGamers.size()+"):"+existedGamers.toString());
                        if(existedGamers.size()==0) {//Do not existed.
                                Gamer gamer = new Gamer(vsTitle, firstPart.get(i), secondPart.get(i), "");
                                //db save first.
                                gamer.setStatus(GameStatus.PAIRED.getIndex());
                                Gamer saved = gamerRepository.save(gamer);

                                //Send message
                                //                        ActivemqSender sender2Player1 = new ActivemqSender(player1.getTopicName());
                                //                        sender2Player1.sendMessage("echo");//For CREATE.CREATE
                                mqttService.subscribe(player1.getTopicName());
                                mqttService.publish(player1.getTopicName(), vsTitle, MqttQoS.EXCATLY_ONCE.getIndex());

                                //                        ActivemqSender sender2Player2 = new ActivemqSender(secondPart.get(i).getTopicName());
                                //                        sender2Player2.sendMessage("echo");//For CREATE.
                                mqttService.subscribe(player2.getTopicName());
                                mqttService.publish(player2.getTopicName(), vsTitle, MqttQoS.EXCATLY_ONCE.getIndex());
                                //
                                gamers.add(saved);
                        }else {
                                Gamer updateGamer =  existedGamers.get(0);
                                updateGamer.setStatus(GameStatus.PAIRED.getIndex());
                                Gamer updated = gamerRepository.save(updateGamer);
                                gamers.add(updated);
                        }
                }
                LOG.info("pairingGamers("+gamers.size()+"):"+gamers.toString());
                return gamers;
        }

        @Override public List<Gamer> playAll() throws MqttException {
                List<Gamer> pairedGames = gamerRepository.findByStatus(GameStatus.PAIRED.getIndex());
//                List<Gamer> playingGames = gamerRepository.findByStatus(GameStatus.PLAYING.getIndex());//TODO:resume-able game.
//                List<Gamer> playableGames = (pairedGames.size()>0)?pairedGames:playingGames;
                List<Gamer> playableGames = pairedGames;
                LOG.info("playableGames("+playableGames.size()+"):"+playableGames.toString());
                //find each player, send play notification
                for(int i=0;i<playableGames.size();i++) {
                        Gamer curGamer =  playableGames.get(i);
                        this.play(curGamer,i);
                }
                List<Gamer> updatedPairedGames = gamerRepository.findByStatus(GameStatus.PLAYING.getIndex());
                return updatedPairedGames;
        }

        @Override public Gamer playOne(String gamerId) throws MqttException {
                Gamer curGamer = gamerRepository.findOne(gamerId);
                return this.play(curGamer,1);
        }

        private Gamer play(Gamer gamer,int index) throws MqttException{
                LOG.info("this.play:#"+index+",is:"+gamer.toString());
                User player1 = gamer.getPlayer1();
                User player2 = gamer.getPlayer2();
                String playMessage = player1.getId()+MqttVariables.tag_play;
                //Game turn now
                //FIRST HAND
                mqttService.subscribe(player1.getTopicName());
                mqttService.publish(player1.getTopicName(),playMessage, MqttQoS.EXCATLY_ONCE.getIndex());
                //Second Hand
//                mqttService.subscribe(player2.getTopicName());
//                mqttService.publish(player2.getTopicName(),playMessage, MqttQoS.EXCATLY_ONCE.getIndex());
                //Update game status
                player1.setStatus(UserStatus.PLAYING.getIndex());
                player2.setStatus(UserStatus.STANDBY.getIndex());
                //
                //
                //Save game status
                gamer.setStatus(GameStatus.PLAYING.getIndex());
                Gamer savedGamer = gamerRepository.save(gamer);
                LOG.info("savedGamer#"+index+":"+savedGamer.toString());
                return gamer;
        }
}
