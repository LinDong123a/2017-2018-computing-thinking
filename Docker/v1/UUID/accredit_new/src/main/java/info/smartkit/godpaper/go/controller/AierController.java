package info.smartkit.godpaper.go.controller;

import com.spotify.docker.client.exceptions.DockerException;
import info.smartkit.godpaper.go.pojo.Aier;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.AierRepository;
import info.smartkit.godpaper.go.service.AierService;
import info.smartkit.godpaper.go.settings.GameStatus;
import info.smartkit.godpaper.go.settings.UserStatus;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by smartkit on 09/07/2017.
 */
@RestController
@RequestMapping("/ai")
public class AierController {
        @Autowired AierRepository repository;
        @Autowired AierService aierService;

        @RequestMapping(method = RequestMethod.POST)
        public Aier createOne(@RequestBody Aier value) throws IOException {
                //file operation for trainning
                value.setFiles(aierService.createModelFolder(value.getName()).getAbsolutePath());
                if(value.getModel()!=null){
                        //TODO:
//                        aierService.copySgfFiles(value.getModel(),value.getName());
                }
                //
                return repository.save(value);
        }


        @RequestMapping(method = RequestMethod.GET, value="/{Id}")
        public Aier get(@PathVariable String Id){
                return repository.findOne(Id);
        }

        @RequestMapping(method = RequestMethod.DELETE, value="/{Id}")
        public void deleteById(@PathVariable String Id) {
                repository.delete(Id);
        }


        @RequestMapping(method = RequestMethod.DELETE, value="/")
        public void deleteAll(){
                repository.deleteAll();
        }


        @RequestMapping(method = RequestMethod.GET)
        public List<Aier> listAll(){

                return repository.findAllByOrderByCreatedDesc();
        }

        @RequestMapping(method = RequestMethod.GET, value="/status/{index}")
        public List<Aier> listByStatus(@PathVariable int index){

                return repository.findByStatusOrderByCreatedDesc(index);
        }


        @RequestMapping(method = RequestMethod.PUT)
        public Aier updateOne(@RequestBody Aier value){
                //
                Aier updateOne = repository.findOne(value.getId());
                updateOne.setName(value.getName());
                updateOne.setModel(value.getModel());
                return repository.save(value);
        }
}
