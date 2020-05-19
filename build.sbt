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
  "com.typesafe.play" %% "play-json" % "2.8.1"
)

lazy val root = (project in file(".")).settings(
  name := "de.htwg.sa.dominion",
  libraryDependencies ++= commonDependencies
).aggregate(cardModule, playerModule).dependsOn(cardModule, playerModule)

lazy val cardModule = project.settings(
  name := "cardModule",
  libraryDependencies ++= commonDependencies
)

lazy val playerModule = project.settings(
  name := "playerModule",
  libraryDependencies ++= commonDependencies
).aggregate(cardModule).dependsOn(cardModule)