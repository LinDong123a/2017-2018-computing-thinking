package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import info.smartkit.godpaper.go.settings.AIProperties;
import info.smartkit.godpaper.go.settings.AIVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        @Override public boolean runPlayer(String userId) throws DockerException, InterruptedException, DockerCertificateException {
                // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
                final DockerClient docker = DefaultDockerClient.fromEnv().build();

                // Pull an image
                docker.pull(AIVariables.player);

                // Bind container ports to host ports
                final String[] ports = {"80", "22"};
                final Map<String, List<PortBinding>> portBindings = new HashMap<>();
                for (String port : ports) {
                        List<PortBinding> hostPorts = new ArrayList<>();
                        hostPorts.add(PortBinding.of("0.0.0.0", port));
                        portBindings.put(port, hostPorts);
                }

                // Bind container port 443 to an automatically allocated available host port.
                List<PortBinding> randomPort = new ArrayList<>();
                randomPort.add(PortBinding.randomPort("0.0.0.0"));
                portBindings.put("443", randomPort);

                final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

                // Create container with exposed ports
                final ContainerConfig containerConfig = ContainerConfig.builder()
                        .hostConfig(hostConfig)
                        .image("busybox").exposedPorts(ports)
                        .cmd("sh", "-c", "while :; do sleep 1; done")
                        .build();

                final ContainerCreation creation = docker.createContainer(containerConfig);
                final String id = creation.id();

                // Inspect container
                final ContainerInfo info = docker.inspectContainer(id);

                // Start container
                docker.startContainer(id);

                // Exec command inside running container with attached STDOUT and STDERR
                final String[] command = {"bash", "-c", "ls"};
                final ExecCreation execCreation = docker.execCreate(
                        id, command, DockerClient.ExecCreateParam.attachStdout(),
                        DockerClient.ExecCreateParam.attachStderr());
                final LogStream output = docker.execStart(execCreation.id());
                final String execOutput = output.readFully();

                // Kill container
                docker.killContainer(id);

                // Remove container
                docker.removeContainer(id);

                // Close the docker client
                docker.close();
                return true;
        }

        @Override public boolean runAgent(String name) throws DockerException, InterruptedException, DockerCertificateException {
                return false;
        }
}
