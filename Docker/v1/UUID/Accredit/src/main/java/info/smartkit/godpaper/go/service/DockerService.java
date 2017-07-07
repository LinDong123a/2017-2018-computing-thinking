package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerStats;

/**
 * Created by smartkit on 06/07/2017.
 */
public interface DockerService {
        String runPlayer(String name) throws DockerException, InterruptedException, DockerCertificateException;
        String runAgent(String name) throws DockerException, InterruptedException, DockerCertificateException;
        ContainerInfo info(String id) throws DockerException, InterruptedException, DockerCertificateException;
        ContainerStats stats(String id) throws DockerException, InterruptedException, DockerCertificateException;
}
