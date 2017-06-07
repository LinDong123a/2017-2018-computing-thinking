# 2017-2018-computing-thinking

## Docker+CI/CD+Microservices

### Ubuntu+Python+Flask+Wgo.js

1.docker pull smartkit/godpaper-go-flask

2.docker run -p 8080:80 smartkit/godpaper-go-flask

3.http://localhost:8080/demo

### Ubuntu+NodeJS+Tenuki.js

1.docker pull smartkit/godpaper-go-nodejs

2.docker run -p 3000:3000 smartkit/godpaper-go-nodejs

3.http://localhost:3000/client.html

### Ubuntu16.04+Python3.5+Tensorflow1.1+MuGo

1.docker pull smartkit/godpaper-tensorflow-mugo

2.docker run -it smartkit/godpaper-tensorflow-mugo-201706 bash

3.pip install -r requirements.txt

4.https://github.com/brilee/MuGo


### Ubuntu16.04+Python3.5+Tensorflow1.1+WeiQi

1.docker pull smartkit/godpaper-tensorflow-weiqi

2.docker run -p 8080:8080 smartkit/godpaper-tensorflow-weiqi

4.http://127.0.0.1:8080/

### Ubuntu+Minikube

#### Inside VirtualBox
1.

2.

3.
#### Outside VirtualBox
1.

2.

3.

### Ubuntu+MultiChain

1.git clone https://github.com/yangboz/docker-multichain && cd docker-multichain

2.sudo docker-compose up

3.http://127.0.0.1:2750/

### References

MutiChain:

http://www.multichain.com/getting-started/

http://allcoinsnews.com/2015/11/27/with-multichain-create-a-private-blockchain-in-90-seconds/

https://labs.kunstmaan.be/blog/hands-on-with-multichain

Docker:

https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04

Kubernates:

https://kubernetes.io/docs/tutorials/kubernetes-basics/

Minikube:

Ubuntu(apt-get):https://gist.github.com/osowski/adce22b01fadd6e2bc3331c066d7d612

Ubuntu(deb):https://gist.github.com/stephenmw/517bc4b37508b51e070e1260e194526a

MacOSX:https://gist.github.com/kevin-smets/b91a34cea662d0c523968472a81788f7

Docker Compose to Kubernate:

https://github.com/kelseyhightower/compose2kube

### Troubleshots

1.Issue:# -*- coding: utf-8 -*-  '0x1\' issue on Python2.7.
1.Fix:upgrade to Python3.5;

2.Ubuntu 16.4 virtualization problem- MIniKub: https://github.com/kubernetes/minikube/issues/1507

3.Virtualbox+Vagrant problem: Starting local Kubernetes v1.6.4 cluster...

"Starting VM...
E0607 15:29:14.273063    2855 start.go:127] Error starting host: Error getting state for host: unexpected EOF."


