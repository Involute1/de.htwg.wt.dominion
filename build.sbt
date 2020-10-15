import sbt.Keys.libraryDependencies
//name          := "de.htwg.sa.dominion"
ThisBuild / version       := "0.1"
ThisBuild / scalaVersion  := "2.13.1"
ThisBuild / trapExit := false
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "UTF-8")

resolvers += Resolver.jcenterRepo

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
  "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0",
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe" % "config" % "1.4.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "com.microsoft.sqlserver" % "mssql-jdbc" % "8.3.1.jre8-preview",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.0.4"
)

lazy val root = (project in file(".")).settings(
  name := "de.htwg.sa.dominion",
  libraryDependencies ++= commonDependencies,
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "module-info.class"                           => MergeStrategy.concat
    case "CHANGELOG.adoc"                              => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("de.htwg.wt.dominion.Dominion")
).aggregate(cardModule, playerModule).dependsOn(cardModule, playerModule)

lazy val cardModule = project.settings(
  name := "cardModule",
  libraryDependencies ++= commonDependencies,
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "module-info.class"                           => MergeStrategy.concat
    case "CHANGELOG.adoc"                              => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("de.htwg.wt.dominion.CardMain")
)

lazy val playerModule = project.settings(
  name := "playerModule",
  libraryDependencies ++= commonDependencies,
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "module-info.class"                           => MergeStrategy.concat
    case "CHANGELOG.adoc"                              => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("de.htwg.wt.dominion.PlayerMain")
).aggregate(cardModule).dependsOn(cardModule)

