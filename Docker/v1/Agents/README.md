
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
docker run -v /Users/yangboz/sgfs/test:/sgfs/ -v /Users/yangboz/sgfs/test/processed_data/:/processed_data/ smartkit/godpaper-go-ai-agent-mugo-prep
```

```
docker run -v /Users/yangboz/sgfs/test:/sgfs/ -v /Users/yangboz/sgfs/test/processed_data/:/processed_data/ -v /Users/yangboz/sgfs/test/saved_model:/saved_model/ smartkit/godpaper-go-ai-agent-mugo-train
```

3.docker inspect -f {{.Volumes}} mugo

### DarkGo


### References

Mugo: https://github.com/brilee/MuGo

DarkGo: https://pjreddie.com/darknet/darkgo-go-in-darknet/

Docker client: https://github.com/spotify/docker-client

Docker Volume: http://dockone.io/article/128

Game Records: https://www.u-go.net/gamerecords/
