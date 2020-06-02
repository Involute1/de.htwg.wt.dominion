FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
EXPOSE 8080
WORKDIR /dominion
ADD . /dominion
CMD sbt run