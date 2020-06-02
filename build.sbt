import sbt.Keys.libraryDependencies
//name          := "de.htwg.sa.dominion"
ThisBuild / version       := "0.1"
ThisBuild / scalaVersion  := "2.13.1"
ThisBuild / trapExit := false


val commonDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  "com.google.inject" % "guice" % "4.2.2",
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "com.typesafe.akka" %% "akka-http" % "10.1.12",
  "com.typesafe.akka" %% "akka-stream" % "2.6.5",
  "com.typesafe.akka" %% "akka-testkit" % "2.6.5" % Test,
  "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0"
)

lazy val root = (project in file(".")).settings(
  name := "de.htwg.sa.dominion",
  libraryDependencies ++= commonDependencies,
  mainClass in (Compile, run) :=Some("src/main/scala/de.htwg.sa.dominion.Dominion")
).aggregate(cardModule, playerModule).dependsOn(cardModule, playerModule)

lazy val cardModule = project.settings(
  name := "cardModule",
  libraryDependencies ++= commonDependencies,
  mainClass in (Compile, run) :=Some("cardModule/src/main/scala/de.htwg.sa.dominion.CardMain")
)

lazy val playerModule = project.settings(
  name := "playerModule",
  libraryDependencies ++= commonDependencies,
  mainClass in (Compile, run) :=Some("playerModule/src/main/scala/de.htwg.sa.dominion.PlayerMain")
).aggregate(cardModule).dependsOn(cardModule)