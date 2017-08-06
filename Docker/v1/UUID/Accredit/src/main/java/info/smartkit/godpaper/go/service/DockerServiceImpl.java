package info.smartkit.godpaper.go.service;

import com.shekhargulati.reactivex.docker.client.RxDockerClient;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import info.smartkit.godpaper.go.pojo.Aier;
import info.smartkit.godpaper.go.pojo.Gamer;
import info.smartkit.godpaper.go.repository.AierRepository;
import info.smartkit.godpaper.go.repository.GamerRepository;
import info.smartkit.godpaper.go.service.GamerService;
import info.smartkit.godpaper.go.settings.*;
import info.smartkit.godpaper.go.utils.SgfUtil;
import info.smartkit.godpaper.go.utils.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartkit on 06/07/2017.
 * @see: https://github.com/spotify/docker-client
 * @see: https://github.com/shekhargulati/rx-docker-client
 */
@Service
public class DockerServiceImpl implements DockerService{

        @Autowired AIProperties aiProperties;
        @Autowired MqttProperties mqttProperties;
        @Autowired ServerProperties serverProperties;
        @Autowired AierRepository aierRepository;
        @Autowired ApiProperties apiProperties;
        @Autowired GamerRepository gamerRepository;
        @Autowired GamerService gamerService;


        private static Logger LOG = LogManager.getLogger(DockerServiceImpl.class);

//        final DockerClient dockerClient = DefaultDockerClient.fromEnv().build();
        final DockerClient dockerClient =new DefaultDockerClient("unix:///var/run/docker.sock");
//        RxDockerClient rxDockerClient = RxDockerClient.fromDefaultEnv();

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
                dockerClient.pull(aiProperties.getPlayer());
                // Create container
                List<String> envStrings = new ArrayList<>();
//                String UriString = mqttProperties.getIp()+":"+serverProperties.getPort()+serverProperties.getContextPath();
//                                envStrings.add("URI_API=http://192.168.0.11:8095/accredit/");
//                                envStrings.add("IP_MQTT=192.168.0.11");
                envStrings.add("URI_API="+apiProperties.getUrl());//http://192.168.0.11:8095/accredit/
                envStrings.add("IP_MQTT="+mqttProperties.getIp());//192.168.0.11,ServerUtil.getInetAddress().getHostAddress()
                //
                final HostConfig hostConfig =
                        HostConfig.builder()
                                .appendBinds(HostConfig.Bind.from(SgfUtil.getAiFilesLocal(""))
                                        .to("/AI_FILEs")
                                        //                                        .readOnly(true)
                                        .build())
                                .build();
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getPlayer())
                        .env(envStrings)
                        .hostConfig(hostConfig)
                        .build();
                final ContainerCreation creation = dockerClient.createContainer(config, name);
                final String id = creation.id();

                // Start container
                dockerClient.startContainer(id);
               //
                final String logs;
                try (LogStream stream = dockerClient.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        LOG.info("Docker(Player) logs:"+logs.toString());
                }
//                final ContainerStats stats = docker.stats(id);
                return id;
        }

        private String runAgentPrep(Aier aier,String hSgf,String modelDest) throws DockerException, InterruptedException, DockerCertificateException, IOException {
                // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
                // Pull an image
                dockerClient.pull(aiProperties.getAgentPrep());
                // Create container
                //
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getAgentPrep())
                        .hostConfig(this.getHostConfig_sgf_mugo_prep(hSgf))
//                        .env(envStrings)
                        .build();
                String uName =  StringUtil.getUuidString(aier.getName(),6);
                final ContainerCreation creation = dockerClient.createContainer(config,uName);
                final String id = creation.id();

                // Start container
                dockerClient.startContainer(id);
                //inspect mounts
                final ContainerInfo info = dockerClient.inspectContainer(id);
                LOG.info("Inspect mounts:"+info.mounts().toString());
                final String logs;
                //Pause for 15 seconds
                Thread.sleep(15000);//wait for docker execution.
                try (LogStream stream = dockerClient.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        LOG.info("Docker(AgentPrep) logs:"+logs.toString());

                        //update aier status
                        aier.setStatus(AierStatus.PROPROCESS.getIndex());
                        Aier uAier = aierRepository.save(aier);
                        //
                        this.runAgentTrain(uAier,hSgf,modelDest);
                }
                return id;
        }
        private String runAgentTrain(Aier aier,String hSgf,String modelDest) throws DockerException, InterruptedException, DockerCertificateException, IOException {
                // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
                // Pull an image
                dockerClient.pull(aiProperties.getAgentTrain());
                // Create container
                //
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getAgentTrain())
                        .hostConfig(this.getHostConfig_sgf_mugo_train(hSgf))
                        //                        .env(envStrings)
                        .build();
                String uName =  StringUtil.getUuidString(aier.getName(),6);
                final ContainerCreation creation = dockerClient.createContainer(config,uName);
                final String id = creation.id();

                // Start container
                dockerClient.startContainer(id);
                //inspect mounts
                final ContainerInfo info = dockerClient.inspectContainer(id);
                LOG.info("Inspect mounts:"+info.mounts().toString());
                final String logs;
                //wait for docker execution.
                Thread.sleep(30000);
                try (LogStream stream = dockerClient.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        LOG.info("Docker(AgentTrain) logs:"+logs.toString());
                        //cp saved_model to AI_Files.
                        FileUtils.copyDirectory(new File(hSgf+"/saved_model/"),new File(modelDest),true);
                        //update aier status
                        aier.setStatus(AierStatus.TRAINED.getIndex());
                        aierRepository.save(aier);
                }
                return id;
        }

        @Override public String runScorer(String gamerId) throws DockerException, InterruptedException {
                //docker run -it -v /Users/smartkit/sgf:/sgfs smartkit/godpaper-go-score-estimator-gnugo
                // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
                // Pull an image
                dockerClient.pull(aiProperties.getScorer());
                // Create container
                List<String> envStrings = new ArrayList<>();
                //                envStrings.add("URI_API=http://192.168.0.11:8095/accredit/");
                //                envStrings.add("IP_MQTT=192.168.0.11");
                ///rename file to submit2gnugo.sgf
                //@see:https://github.com/spotify/docker-client/blob/master/docs/user_manual.md#mounting-volumes-in-a-container
                String fromSgfs = SgfUtil.getSgfLocal(gamerId);
                LOG.info("fromSgfs:"+fromSgfs);
                final ContainerConfig config = ContainerConfig.builder()
                        .image(aiProperties.getScorer())
                        .hostConfig(this.getHostConfig_sgf(fromSgfs))
                        //                        .env(envStrings)
                        .build();
                //
                String uuidName = StringUtil.getUuidString("gnugo",6);
                //
                final ContainerCreation creation = dockerClient.createContainer(config, uuidName);
                final String id = creation.id();

                // Start container
                dockerClient.startContainer(id);
                //inspect mounts
//                final ContainerInfo info = docker.inspectContainer(id);
//                LOG.info("Inspect mounts:"+info.mounts().toString());
                final String logs;
                String resultStr = "Unknown";
                //Pause for 10 seconds
                Thread.sleep(10000);//wait for docker execution.
                //
                try (LogStream stream = dockerClient.logs(uuidName, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                        logs = stream.readFully();
                        //only one string for result.
                        resultStr = logs.toString();
                        LOG.info("Docker(Scorer) logs:"+resultStr);
                        //update game result.
                        gamerService.updateSgf(gamerId,resultStr);
                } catch (IOException e) {
                        e.printStackTrace();
                        LOG.warn(e.toString());
                }
                return resultStr;
        }

        @Override public String trainAgent(Aier aier) throws DockerException, InterruptedException, DockerCertificateException, IOException {
                //preprocess data then savedmodel
                String fromSgfs = SgfUtil.getSgfLocal(aier.getGid());
                LOG.info("Aier for trainAgent:"+aier.toString());
                return this.runAgentPrep(aier,fromSgfs,aier.getFiles());
        }

        @Override public ContainerInfo info(String id) throws DockerException, InterruptedException, DockerCertificateException {
                final ContainerInfo info = dockerClient.inspectContainer(id);
                return info;
        }

        @Override public ContainerStats stats(String id) throws DockerException, InterruptedException, DockerCertificateException {
                final ContainerStats stats = dockerClient.stats(id);
                return stats;
        }

        @Override public void stopContainer(String id, int delay) throws DockerException, InterruptedException {
                // Stop container
                dockerClient.stopContainer(id,delay);
        }

        @Override public void killContainer(String id) throws DockerException, InterruptedException {
                // Kill container
                dockerClient.killContainer(id);
        }

        @Override public void removeContainer(String id) throws DockerException, InterruptedException {
                // Remove container
                dockerClient.removeContainer(id);
        }

        @Override public void pauseContainer(String id) throws DockerException, InterruptedException {
                dockerClient.pauseContainer(id);
        }

        @Override public void unpauseContainer(String id) throws DockerException, InterruptedException {
                dockerClient.unpauseContainer(id);
        }

        @Override public void startContainer(String id) throws DockerException, InterruptedException {
                dockerClient.startContainer(id);
        }

        @Override public void restartContainer(String id, int delay) throws DockerException, InterruptedException {
                dockerClient.restartContainer(id,delay);
        }

        private HostConfig getHostConfig_sgf(String hSgf){
                final HostConfig hostConfig_sgf =
                        HostConfig.builder()
                                .appendBinds(HostConfig.Bind.from(hSgf)
                                        .to("/sgfs")
                                        .readOnly(true)
                                        .build())
                                .build();
                return hostConfig_sgf;
        }
        private HostConfig getHostConfig_sgf_mugo_prep(String hSgf){
                //
                final HostConfig hostConfig_sgf =
                        HostConfig.builder()
                                .appendBinds(HostConfig.Bind.from(hSgf)
                                        .to("/sgfs/")
                                        .readOnly(false)
                                        .build())
                                .appendBinds(HostConfig.Bind.from(hSgf+"/processed_data/")
                                        .to("/processed_data/")
                                        .readOnly(false)
                                        .build())
                                .build();
                return hostConfig_sgf;
        }


        private HostConfig getHostConfig_sgf_mugo_train(String hSgf){
                final HostConfig hostConfig_sgf =
                        HostConfig.builder()
                                .appendBinds(HostConfig.Bind.from(hSgf)
                                        .to("/sgfs/")
                                        .readOnly(true)
                                        .build())
                                .appendBinds(HostConfig.Bind.from(hSgf+"/processed_data/")
                                        .to("/processed_data/")
                                        .readOnly(false)
                                        .build())
                                .appendBinds(HostConfig.Bind.from(hSgf+"/saved_model/")
                                        .to("/saved_model/")
                                        .readOnly(false)
                                        .build())
                                .build();
                return hostConfig_sgf;
        }

}

