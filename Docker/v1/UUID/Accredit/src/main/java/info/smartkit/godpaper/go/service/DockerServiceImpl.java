package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import info.smartkit.godpaper.go.settings.AIProperties;
import info.smartkit.godpaper.go.settings.AIVariables;
import info.smartkit.godpaper.go.settings.MqttProperties;
import info.smartkit.godpaper.go.utils.ServerUtil;
import info.smartkit.godpaper.go.utils.SgfUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smartkit on 06/07/2017.
 * @see: https://github.com/spotify/docker-client
 */
@Service
public class DockerServiceImpl implements DockerService{

        @Autowired AIProperties aiProperties;
        @Autowired MqttProperties mqttProperties;
        @Autowired ServerProperties serverProperties;

        private static Logger LOG = LogManager.getLogger(DockerServiceImpl.class);

        final DockerClient docker = DefaultDockerClient.fromEnv().build();

        public DockerServiceImpl() throws DockerCertificateException {
        }

        @Override public String runPlayer(String name) throws DockerException, InterruptedException, DockerCertificateException {
                // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars

//                final DockerClient docker = DefaultDockerClient.builder()
//                        .uri(URI.create("https://boot2docker:2376"))
//                        .dockerCertificates(new DockerCertificates(Paths.get("~/.docker/boot2docker-vm/")))
//                        .build();
//                final DockerClient docker = DefaultDockerClient.builder()
                        // Set various options
//                        .build();
                // Pull an image
                docker.pull(aiProperties.getPlayer());
                // Create container
                List<String> envStrings = new ArrayList<>();
                String UriString = mqttProperties.getIp()+":"+serverProperties.getPort()+serverProperties.getContextPath();
//                                envStrings.add("URI_API=http://192.168.0.11:8095/accredit/");
//                                envStrings.add("IP_MQTT=192.168.0.11");
                envStrings.add("URI_API=http://"+ UriString+"/");//http://192.168.0.11:8095/accredit/
                envStrings.add("IP_MQTT="+mqttProperties.getIp());//192.168.0.11,ServerUtil.getInetAddress().getHostAddress()
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getPlayer())
                        .env(envStrings)
//                        .hostConfig()
                        .build();
                final ContainerCreation creation = docker.createContainer(config, name);
                final String id = creation.id();

                // Start container
                docker.startContainer(id);
                // Inspect container
//                final ContainerInfo info = docker.inspectContainer(id);

                // Bind container ports to host ports
//                final String[] ports = {"80", "22"};
//                final Map<String, List<PortBinding>> portBindings = new HashMap<>();
//                for (String port : ports) {
//                        List<PortBinding> hostPorts = new ArrayList<>();
//                        hostPorts.add(PortBinding.of("0.0.0.0", port));
//                        portBindings.put(port, hostPorts);
//                }

                // Bind container port 443 to an automatically allocated available host port.
//                List<PortBinding> randomPort = new ArrayList<>();
//                randomPort.add(PortBinding.randomPort("0.0.0.0"));
////                portBindings.put("443", randomPort);
//
//                final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();
//
//                // Create container with exposed ports
//                final ContainerConfig containerConfig = ContainerConfig.builder()
//                        .hostConfig(hostConfig)
//                        .image("smartkit/godpaper-go-ai-player")
////                        .exposedPorts(ports)
////                        .cmd("sh", "-c", "while :; do sleep 1; done")
//                        .build();
//
//                final ContainerCreation creation = docker.createContainer(containerConfig);
//                final String id = creation.id();
//
//                // Inspect container
//                final ContainerInfo info = docker.inspectContainer(id);
//
//                // Start container
//                docker.startContainer(id);
//
//                // Exec command inside running container with attached STDOUT and STDERR
//                final String[] command = {"bash", "-c", "ls"};
//                final ExecCreation execCreation = docker.execCreate(
//                        id, command, DockerClient.ExecCreateParam.attachStdout(),
//                        DockerClient.ExecCreateParam.attachStderr());
//                final LogStream output = docker.execStart(execCreation.id());
//                final String execOutput = output.readFully();

                // Kill container
//                docker.killContainer(id);

                // Remove container
//                docker.removeContainer(id);

                // Close the docker client
//                docker.close();
                final String logs;
                try (LogStream stream = docker.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        LOG.info("Docker(Player) logs:"+logs.toString());
                }
//                final ContainerStats stats = docker.stats(id);
                return id;
        }

        @Override public String runAgent(String name) throws DockerException, InterruptedException, DockerCertificateException {
                // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
                // Pull an image
                docker.pull(aiProperties.getAgent());
                // Create container
                List<String> envStrings = new ArrayList<>();
//                envStrings.add("URI_API=http://192.168.0.11:8095/accredit/");
//                envStrings.add("IP_MQTT=192.168.0.11");
                //@see:https://github.com/spotify/docker-client/blob/master/docs/user_manual.md#mounting-volumes-in-a-container
                final HostConfig hostConfig =
                        HostConfig.builder()
                                .appendBinds(HostConfig.Bind.from(SgfUtil.getSgfLocal(""))
                                        .to("/sgf")
//                                        .readOnly(true)
                                        .build())
                                .build();
//                final HostConfig hostConfig =
//                        HostConfig.builder()
//                                .appendBinds(HostConfig.Bind.from(SgfUtil.getSgfLocal("savedmodel"))
//                                        .to("/savedmodel")
//                                        //                                        .readOnly(true)
//                                        .build())
//                                .build();
                //
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getAgent())
                        .hostConfig(hostConfig)
//                        .env(envStrings)
                        .build();
                final ContainerCreation creation = docker.createContainer(config, name);
                final String id = creation.id();

                // Start container
                docker.startContainer(id);
                //inspect mounts
                final ContainerInfo info = docker.inspectContainer(id);
                LOG.info("Inspect mounts:"+info.mounts().toString());
                final String logs;
                try (LogStream stream = docker.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        LOG.info("Docker(Agent) logs:"+logs.toString());
                }
                return id;
        }

        @Override public String trainAgent(String name) throws DockerException, InterruptedException, DockerCertificateException {
                //default call.
                this.runAgent(name);
                //then trainAgent
                docker.pull(aiProperties.getAgent());
                // Create container
                List<String> envStrings = new ArrayList<>();
                //                envStrings.add("URI_API=http://192.168.0.11:8095/accredit/");
                //                envStrings.add("IP_MQTT=192.168.0.11");
                //@see:https://github.com/spotify/docker-client/blob/master/docs/user_manual.md#mounting-volumes-in-a-container
                final HostConfig hostConfig =
                        HostConfig.builder()
                                .appendBinds(HostConfig.Bind.from(SgfUtil.getSgfLocal("savedmodel"))
                                        .to("/savedmodel")
                                        //                                        .readOnly(true)
                                        .build())
                                .build();
                //
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getAgent())
                        .hostConfig(hostConfig)
                        .entrypoint("main.py","train","processed_data/","--save-file=/sgf/savedmodel")//python main.py train processed_data/ --save-file=/tmp/savedmodel --epochs=1 --logdir=logs/my_training_run
                        .build();
                final ContainerCreation creation = docker.createContainer(config, name);
                final String id = creation.id();

                // Start container
                docker.startContainer(id);
                //inspect mounts
                final ContainerInfo info = docker.inspectContainer(id);
                LOG.info("Inspect mounts:"+info.mounts().toString());
                final String logs;
                try (LogStream stream = docker.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        LOG.info("Docker(Agent) logs:"+logs.toString());
                }
                return id;
        }

        @Override public ContainerInfo info(String id) throws DockerException, InterruptedException, DockerCertificateException {
                final ContainerInfo info = docker.inspectContainer(id);
                return info;
        }

        @Override public ContainerStats stats(String id) throws DockerException, InterruptedException, DockerCertificateException {
                final ContainerStats stats = docker.stats(id);
                return stats;
        }

        @Override public void stopContainer(String id, int delay) throws DockerException, InterruptedException {
                // Stop container
                docker.stopContainer(id,delay);
        }

        @Override public void killContainer(String id) throws DockerException, InterruptedException {
                // Kill container
                docker.killContainer(id);
        }

        @Override public void removeContainer(String id) throws DockerException, InterruptedException {
                // Remove container
                docker.removeContainer(id);
        }

        @Override public void pauseContainer(String id) throws DockerException, InterruptedException {
                docker.pauseContainer(id);
        }

        @Override public void unpauseContainer(String id) throws DockerException, InterruptedException {
                docker.unpauseContainer(id);
        }

        @Override public void startContainer(String id) throws DockerException, InterruptedException {
                docker.startContainer(id);
        }

        @Override public void restartContainer(String id, int delay) throws DockerException, InterruptedException {
                docker.restartContainer(id,delay);
        }
}
