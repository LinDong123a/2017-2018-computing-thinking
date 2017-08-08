package info.smartkit.godpaper.go.service;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerStats;
import info.smartkit.godpaper.go.pojo.Aier;

import java.io.IOException;

/**
 * Created by smartkit on 06/07/2017.
 * @see: https://github.com/spotify/docker-client/blob/master/docs/user_manual.m
 */
public interface DockerService {
        String runPlayer(String name) throws DockerException, InterruptedException, DockerCertificateException;
//        String runAgent(String name,String hSgf) throws DockerException, InterruptedException, DockerCertificateException;
        String runScorer(String name) throws DockerException, InterruptedException;
        String trainAgent(Aier aier) throws DockerException, InterruptedException, DockerCertificateException, IOException;
        ContainerInfo info(String id) throws DockerException, InterruptedException, DockerCertificateException;
        ContainerStats stats(String id) throws DockerException, InterruptedException, DockerCertificateException;
        void stopContainer(String id,int delay) throws DockerException, InterruptedException;
        void killContainer(String id) throws DockerException, InterruptedException;
        void removeContainer(String id) throws DockerException, InterruptedException;
        void pauseContainer(String id) throws DockerException, InterruptedException;
        void unpauseContainer(String id) throws DockerException, InterruptedException;
        void startContainer(String id) throws DockerException, InterruptedException;
        void restartContainer(String id,int delay) throws DockerException, InterruptedException;

}
