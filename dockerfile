FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
EXPOSE 8080
WORKDIR /dominion
ADD target/scala-2.13/de.htwg.wt.dominion-assembly-0.1.jar /dominion
CMD java -jar de.htwg.wt.dominion-assembly-0.1.jar
ENV DOCKERENV="TRUE"
