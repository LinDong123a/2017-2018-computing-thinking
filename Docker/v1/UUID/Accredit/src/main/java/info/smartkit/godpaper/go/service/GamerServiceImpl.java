package info.smartkit.godpaper.go.service;

import info.smartkit.UUIDAccreditApplication;
import info.smartkit.godpaper.go.activemq.ActivemqReceiver;
import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by smartkit on 22/06/2017.
 */
@Service
public class GamerServiceImpl implements GamerService {

        private static Logger LOG = LogManager.getLogger(GamerServiceImpl.class);;

        @Autowired GamerRepository gamerRepository;
        @Override public List<Gamer> pairAll(List<User> standbyUsers) {
                int arraySize = standbyUsers.size();
                List<User> firstPart = standbyUsers.subList(0,arraySize/2);
                List<User> secondPart = standbyUsers.subList(arraySize/2,arraySize);
                List<Gamer> gamers = new ArrayList<>(arraySize/2);
                for (int i=0;i<arraySize/2;i++) {
                        Gamer gamer  = new Gamer(firstPart.get(i),secondPart.get(i),null);
                        //db save first.
                        Gamer saved = gamerRepository.save(gamer);
                        //produce message topic by uuid,for public message.
                        ActivemqSender sender = new ActivemqSender(saved.getId());
                        sender.sendMessage("echo");//For CREATE.
                        //
                        gamers.add(saved);
                }
                return gamers;
        }

        @Override public List<Gamer> playAll() {
                List<Gamer> pairedGames = gamerRepository.findByStatus(GameStatus.PAIRED.getIndex());
                //find each player, send play notification
                for(int i=0;i<pairedGames.size();i++) {
                        Gamer curGamer =  pairedGames.get(i);
                        User player1 = curGamer.getPlayer1();
                        User player2 = curGamer.getPlayer2();
                        //
                        ActivemqSender sender2Player1 = new ActivemqSender(player1.getTopicName());
                        sender2Player1.sendMessage("vs#"+player2.getId());//Math info message.
                        //
                        ActivemqSender sender2Player2 = new ActivemqSender(player1.getTopicName());
                        sender2Player1.sendMessage("vs#"+player1.getId());//Math info message.
                        //
                        ActivemqReceiver activemqReceiver = new ActivemqReceiver(curGamer.getName());
                        activemqReceiver.receiveMessage();
                        //Game turn now
                        player1.setStatus(UserStatus.PLAYING.getIndex());
                        player2.setStatus(UserStatus.STANDBY.getIndex());
                        //Save status
                        Gamer savedGamer = gamerRepository.save(curGamer);
                        LOG.info("savedGamer:"+savedGamer.toString());
                }
                return null;
        }
}
