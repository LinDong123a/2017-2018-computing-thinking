package info.smartkit.godpaper.go.controller;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerStats;
import info.smartkit.godpaper.go.service.DockerService;
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
        private static Logger LOG = LogManager.getLogger(DockerController.class);

        @RequestMapping(method = RequestMethod.GET,value="/run/player/{userId}")
        public String runPlayer(@PathVariable String userId) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                return dockerService.runPlayer(userId);
        }

        @RequestMapping(method = RequestMethod.GET,value="/run/agent/{name}")
        public String runAgent(@PathVariable String name) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                return dockerService.runAgent(name);
        }

        @RequestMapping(method = RequestMethod.GET,value="/info/{id}")
        public ContainerInfo info(@PathVariable String id) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                return dockerService.info(id);
        }
        @RequestMapping(method = RequestMethod.GET,value="/stats/{id}")
        public ContainerStats stats(@PathVariable String id) throws MqttException, InterruptedException, DockerException, DockerCertificateException {
                return dockerService.stats(id);
        }
}
