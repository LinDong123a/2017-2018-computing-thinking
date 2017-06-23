package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.activemq.ActivemqReceiver;
import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.MqttQoS;
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
                List<User> firstPart = tenantedUsers.subList(0,arraySize/2);
                List<User> secondPart = tenantedUsers.subList(arraySize/2,arraySize);
                List<Gamer> gamers = new ArrayList<>(arraySize/2);
                for (int i=0;i<arraySize/2;i++) {
                        Gamer gamer  = new Gamer(firstPart.get(i),secondPart.get(i),null);
                        //db save first.
                        Gamer saved = gamerRepository.save(gamer);
                        //produce message topic by uuid,for public message.
                        User player1 = firstPart.get(i);
                        User player2 = secondPart.get(i);
                        String vsTitle =  player1.getTopicName()+"#vs#"+player2.getTopicName();
//                        ActivemqSender sender2Player1 = new ActivemqSender(player1.getTopicName());
//                        sender2Player1.sendMessage("echo");//For CREATE.CREATE
                        mqttService.subscribe(player1.getTopicName());
                        mqttService.publish(player1.getTopicName(),vsTitle, MqttQoS.EXCATLY_ONCE.getIndex());

//                        ActivemqSender sender2Player2 = new ActivemqSender(secondPart.get(i).getTopicName());
//                        sender2Player2.sendMessage("echo");//For CREATE.
                        mqttService.subscribe(player2.getTopicName());
                        mqttService.publish(player2.getTopicName(),vsTitle, MqttQoS.EXCATLY_ONCE.getIndex());

                        //
                        gamers.add(saved);
                }
                return gamers;
        }

        @Override public List<Gamer> playAll() throws MqttException {
                List<Gamer> pairedGames = gamerRepository.findByStatus(GameStatus.PAIRED.getIndex());
                //find each player, send play notification
                for(int i=0;i<pairedGames.size();i++) {
                        Gamer curGamer =  pairedGames.get(i);
                        User player1 = curGamer.getPlayer1();
                        User player2 = curGamer.getPlayer2();
                        String playMessage = player1.getId()+"#play#";
                        //FIRST HAND

                        //
//                        ActivemqSender sender2Player1 = new ActivemqSender(player1.getTopicName());
//                        sender2Player1.sendMessage(gameTitle);//Math info message.
                        mqttService.subscribe(player1.getTopicName());
                        mqttService.publish(player1.getTopicName(),playMessage, MqttQoS.EXCATLY_ONCE.getIndex());
                        //
//                        ActivemqSender sender2Player2 = new ActivemqSender(player1.getTopicName());
//                        sender2Player2.sendMessage(gameTitle);//Math info message.
                        mqttService.subscribe(player2.getTopicName());
                        mqttService.publish(player2.getTopicName(),playMessage, MqttQoS.EXCATLY_ONCE.getIndex());
                        //
//                        ActivemqReceiver activemqReceiver = new ActivemqReceiver(curGamer.getName());
//                        activemqReceiver.receiveMessage();
                        //Game turn now
                        player1.setStatus(UserStatus.PLAYING.getIndex());
                        player2.setStatus(UserStatus.STANDBY.getIndex());
                        //

                        //
                        //Save game status
                        curGamer.setStatus(GameStatus.SAVED.getIndex());
                        Gamer savedGamer = gamerRepository.save(curGamer);
                        LOG.info("savedGamer:"+savedGamer.toString());
                }
                List<Gamer> updatedPairedGames = gamerRepository.findByStatus(GameStatus.PAIRED.getIndex());
                return updatedPairedGames;
        }
}
