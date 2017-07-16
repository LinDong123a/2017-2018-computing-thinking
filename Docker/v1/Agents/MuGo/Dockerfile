FROM tensorflow/tensorflow:nightly

MAINTAINER yangboz "z@smartkit.info"

RUN apt-get update && apt-get install -y build-essential git wget
RUN apt-get install -y vim

# get wget
#RUN apt-get install wget

# install python 3.5
RUN add-apt-repository -y ppa:fkrull/deadsnakes
RUN apt-get -y update
RUN apt-get -y install python3.5 python3-pip



COPY . /app
WORKDIR /app

RUN pip3 install --upgrade pip
RUN pip3 install -r requirements.txt

#EXPOSE 6000

#VOLUME /sgf


ENTRYPOINT ["python3"]
#Preprocess SGFs
CMD ["main.py", "preprocess", "/sgf"]
#CMD ["main.py", "train", "processed_data/"]