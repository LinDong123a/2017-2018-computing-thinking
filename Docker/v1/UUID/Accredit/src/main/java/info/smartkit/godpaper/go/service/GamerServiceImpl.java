package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.GamerRepository;
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
                return null;
        }
}
