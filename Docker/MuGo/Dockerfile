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

RUN pip3 install -r requirements.txt

#Preprocess SGFs
ENTRYPOINT ["python3"]
CMD ["main.py preprocess /app/data/2017"]