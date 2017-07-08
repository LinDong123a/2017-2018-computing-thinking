
## V1.AI Agents

### Mugo:

1.docker pull smartkit/godpaper-tensorflow-mugo

2.docker rm mugo && docker run --name mugo -v /sgf  smartkit/godpaper-tensorflow-mugo:latest /bin/bash

3.docker inspect -f {{.Volumes}} mugo

### DarkGo


### References

Mugo: https://github.com/brilee/MuGo

DarkGo: https://pjreddie.com/darknet/darkgo-go-in-darknet/

Docker client: https://github.com/spotify/docker-client

Docker Volume: http://dockone.io/article/128
