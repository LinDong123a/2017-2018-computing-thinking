package info.smartkit.godpaper.go.controller;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerStats;
import info.smartkit.godpaper.go.pojo.Aier;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.AierRepository;
import info.smartkit.godpaper.go.repository.UserRepository;
import info.smartkit.godpaper.go.service.DockerService;
import info.smartkit.godpaper.go.settings.AierStatus;
import info.smartkit.godpaper.go.settings.UserStatus;
import info.smartkit.godpaper.go.utils.StringUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by smartkit on 07/07/2017.
 */
@RestController
@RequestMapping("/docker")
public class DockerController {
        //
        @Autowired DockerService dockerService;
        @Autowired AierRepository aierRepository;
        @Autowired UserRepository userRepository;
        private static Logger LOG = LogManager.getLogger(DockerController.class);

        @RequestMapping(method = RequestMethod.GET,value="/run/player/{userId}")
        public User runPlayer(@PathVariable String userId) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                //update user status
                User updater = userRepository.findOne(userId);
                updater.setStatus(UserStatus.unTENANTED.getIndex());
                User updated = userRepository.save(updater);
                LOG.info("updated:"+updated.toString());
                //
                dockerService.runPlayer(userId);
                //
                return updated;

        }

        @RequestMapping(method = RequestMethod.GET,value="/run/scorer/{name}")
        public String runScorer(@PathVariable String name) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                return dockerService.runScorer(StringUtil.getUuidString(name,6));
        }

//        @RequestMapping(method = RequestMethod.GET,value="/run/agent/{id}")
//        public Aier runAgent(@PathVariable String id) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
//                //update status
//                Aier aier = aierRepository.findOne(id);
//                aier.setStatus(AierStatus.TRAINING.getIndex());
//                Aier updater = aierRepository.save(aier);
//                //
//                dockerService.runAgent(id,aier.getModel());
//                //
//                return updater;
//        }

        @RequestMapping(method = RequestMethod.GET,value="/train/agent/{id}")
        public String trainAgent(@PathVariable String id) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                //update status
                Aier aier = aierRepository.findOne(id);
                aier.setStatus(AierStatus.TRAINING.getIndex());
                aierRepository.save(aier);
                //
                return dockerService.trainAgent(id,aier.getModel());
        }

//        @RequestMapping(method = RequestMethod.GET,value="/info/{id}")
//        public ContainerInfo info(@PathVariable String id) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
//                return dockerService.info(id);
//        }
//        @RequestMapping(method = RequestMethod.GET,value="/stats/{id}")
//        public ContainerStats stats(@PathVariable String id) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
//                return dockerService.stats(id);
//        }
}
