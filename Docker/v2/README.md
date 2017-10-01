# onlinego.com
OGS的网页端

# score-estimator
计算围棋得分

https://www.gnu.org/software/gnugo/gnugo_1.html#SEC2

http://homepages.cwi.nl/~aeb/go/sgfutils/html/sgfcount.html

#### 安装GitLab、Wiki、Jenkins、Docker、NetLogo、Netlyfy、Jupiter、Modelica、等一系列基础环境

#### 建立自己的 Docker Registry，私有的Docker镜像服务，用于存储我们自己的Docker镜像仓库（Repository）

#### 使用GitLab进行代码管理，并联动Jenkins进行自动化打包，生成Docker Image，推送到自己的Repository

#### 使用Docker Compose在目的机上进行集成环境部署

https://blog.catscarlet.com/201612022593.html


# Jenkins

```
docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts
```
## Jenkins-Docker

https://addops.cn/post/jenkins-docker-01.html
