[![Docker Hub](https://img.shields.io/badge/docker-ready-blue.svg)](https://registry.hub.docker.com/u/smartkit/godpaper-go-ionic/) 

smartkit/godpaper-go-ionic framework
=============================

![Ionic](http://ionicframework.com/img/ionic-logo-blue.svg)

smartkit/godpaper-go-ionic-framework is a ready-to-go hybrid deveopment environment for building mobile apps with Ionic, Cordova, and Android. 
This is Docker image based on the Vagrant ionic-box (from [driftyco/ionic-box](https://github.com/driftyco/ionic-box)) to build the ionic-framework. 

Requirements
=================

* [Docker](https://www.docker.com/) 


Usage : smartkit/godpaper-go-ionic framework
=================

    sudo docker run -it --rm --name godpaper-go-ionic -p 8100:8100 -p 35729:35729 smartkit/godpaper-go-ionic

* App is running on http://$DOCKER_HOST:8100 which by default is [http://192.168.59.103/:8100](http://192.168.59.103/:8100)

If you have your own ionic sources, you can launch it with:

    sudo docker run -ti -p 8100:8100 -p 35729:35729 -v /path/to/your/ionic-project/:/App smartkit/godpaper-go-ionic/bin/bash

Enable USB for the container

    sudo docker run -ti --rm -p 8100:8100 -p 35729:35729 --privileged -v /dev/bus/usb:/dev/bus/usb -v \$PWD:/App smartkit/godpaper-go-ionic adb list

