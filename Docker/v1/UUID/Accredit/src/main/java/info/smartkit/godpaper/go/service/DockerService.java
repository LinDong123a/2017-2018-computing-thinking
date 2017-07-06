package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

/**
 * Created by smartkit on 06/07/2017.
 */
public interface DockerService {
        boolean runPlayer(String userId) throws DockerException, InterruptedException, DockerCertificateException;
        boolean runAgent(String name) throws DockerException, InterruptedException, DockerCertificateException;
}
