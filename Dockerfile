FROM hseeberger/scala-sbt
WORKDIR /dominion
ADD . /Dominion
CMD sbt run