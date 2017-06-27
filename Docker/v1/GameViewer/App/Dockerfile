FROM node:7.0

MAINTAINER  z@smartkit.info

# Install basics
RUN apt-get update &&  \
    apt-get install -y git wget curl && \
    apt-get clean

RUN curl -sL https://deb.nodesource.com/setup | bash -

COPY . /App

WORKDIR /App
RUN npm install -g cordova && cordova telemetry off && npm install -g ionic && npm install @ionic/cli-plugin-gulp && npm install @ionic/cli-plugin-ionic1 && npm rebuild node-sass

#RUN npm install && npm cache clean

# Expose port: web (8100), livereload (35729)
EXPOSE 8100 35729
CMD ["ionic", "serve"]
#CMD ["ionic", "serve", "--all", "--port", "8100", "--livereload-port", "35729"]
