
## V1.AI Agents

### Mugo:

1.docker pull

```
docker pull smartkit/godpaper-go-ai-agent-mugo
```
```
docker pull smartkit/godpaper-go-ai-agent-mugo-prep
```
```
docker pull smartkit/godpaper-go-ai-agent-mugo-train
```

2.docker run

```
docker rm mugo && docker run --name mugo -v /Users/smartkit/git/2017-2018-computing-thinking/Docker/v1/UUID/Accredit/target/classes/sgf:/sgf smartkit/godpaper-go-ai-agent-mugo-prep:latest
```

```
docker rm mugo && docker run --name mugo -v /Users/smartkit/git/2017-2018-computing-thinking/Docker/v1/UUID/Accredit/target/classes/sgf:/sgf smartkit/godpaper-go-ai-agent-mugo-train:latest
```

3.docker inspect -f {{.Volumes}} mugo

### DarkGo


### References

Mugo: https://github.com/brilee/MuGo

DarkGo: https://pjreddie.com/darknet/darkgo-go-in-darknet/

Docker client: https://github.com/spotify/docker-client

Docker Volume: http://dockone.io/article/128

Game Records: https://www.u-go.net/gamerecords/
