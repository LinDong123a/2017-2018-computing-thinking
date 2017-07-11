
# Pull base image.
FROM ubuntu:16.04

MAINTAINER yangboz <z@smartkit.info>

# Install cmake.
RUN apt-get update; \
    apt-get -y upgrade

RUN apt-get -y install g++ cmake

# Install boost.
RUN apt-get --yes install libboost-all-dev

# Define working directory.
COPY . /app
WORKDIR /app

# Define default command.
#ENTRYPOINT ["make"]

CMD ["./configure"]
CMD ["make"]
CMD ["make","install"]
CMD ["gnugo"]
#CMD ["./estimator", "test_games/1776378.game"]
CMD ["gnugo" "--score estimate", "-l", "106.sgf"]
