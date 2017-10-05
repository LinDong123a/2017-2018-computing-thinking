### Docker for GNUGO

1.docker pull
```
docker pull smartkit/godpaper-go-score-estimator-gnugo
```

2.docker run
```
docker run -it -v /Users/smartkit/sgf:/sgfs smartkit/godpaper-go-score-estimator-gnugo
```


Example:
```
docker run -it -v /root/2017-2018-computing-thinking/Docker/v1/UUID/Accredit/target/classes/sgf:/sgfs smartkit/godpaper-go-score-estimator-gnugo
```
Black wins by 10.2 points

#Reference:

https://www.gnu.org/software/gnugo/gnugo_1.html#SEC2
