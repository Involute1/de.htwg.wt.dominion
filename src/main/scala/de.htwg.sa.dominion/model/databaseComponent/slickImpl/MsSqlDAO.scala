package de.htwg.sa.dominion.model.databaseComponent.slickImpl

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.sa.dominion.controller.maincontroller.ControllerState
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.{Roundmanager, RoundmanagerStatus}
import de.htwg.sa.dominion.util.RoundManagerTables.{NameTable, RoundmanagerTable, ScoreTable}
import slick.jdbc.{JdbcProfile, SQLServerProfile}
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.Duration
import scala.util.Try

class MsSqlDAO extends IDominionDatabase with PlayJsonSupport {

  val roundManagerTable: TableQuery[RoundmanagerTable] = TableQuery[RoundmanagerTable]
  val namesTable: TableQuery[NameTable] = TableQuery[NameTable]
  val scoreTable: TableQuery[ScoreTable] = TableQuery[ScoreTable]

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  override def create: Boolean = {
    try {
      val setup = DBIO.seq((roundManagerTable.schema ++ namesTable.schema ++ scoreTable.schema).createIfNotExists)
      db.run(setup)
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def read(): (String, Roundmanager) = {
    val loadedPlayerList: List[Player] = {
      val response = Http().singleRequest(Get("http://0.0.0.0:8081/player/load"))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[List[Player]])
      Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
    }
    val loadedPlayingDecks: (List[List[Card]], List[Card]) = {
      val response = Http().singleRequest(Get("http://0.0.0.0:8082/card/loadPlayingDecks"))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[(List[List[Card]], List[Card], List[Card], List[Card], List[Card])])
      val res = Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
      (res._1, res._2)
    }
    val roundManagerSelect = for {
      (r, n) <- roundManagerTable joinLeft namesTable on (_.namesIdFKey === _.namesId)
      (r, s) <- roundManagerTable joinLeft scoreTable on (_.scoreIdFKey === _.scoreId)
    } yield (r.numberOfPlayers, r.turn, r.emptyDeckCount, r.gameEnd, r.roundStatus, r.playerTurn, r. controllerSate
      , n.map(_.player1Name), n.map(_.player2Name), n.map(_.player3Name), n.map(_.player4Name), n.map(_.player5Name)
      , s.map(_.player1Score), s.map(_.player2Score), s.map(_.player3Score), s.map(_.player4Score), s.map(_.player5Score))
    val loadedRoundManagerTuple = Await.result(db.run(roundManagerSelect.result), Duration(1, TimeUnit.SECONDS))

    val nameScoreTuple = reconstructListsForPlayerNumber(loadedRoundManagerTuple.head._1.get, loadedRoundManagerTuple)
    val loadedRoundmanager = Roundmanager(loadedPlayerList, nameScoreTuple._1, loadedRoundManagerTuple.head._1.get,
      loadedRoundManagerTuple.head._2.get, loadedPlayingDecks._1, loadedRoundManagerTuple.head._3.get, loadedRoundManagerTuple.head._4.get,
      nameScoreTuple._2, RoundmanagerStatus.roundStatusMapFromString(loadedRoundManagerTuple.head._5.get), loadedRoundManagerTuple.head._6.get, loadedPlayingDecks._2)

    (loadedRoundManagerTuple.head._7.get, loadedRoundmanager)
  }

  def reconstructListsForPlayerNumber(numberOfPlayers: Int, loadedRoundManagerTuple: Seq[(Option[Int], Option[Int], Option[Int], Option[Boolean], Option[String], Option[Int], Option[String], Option[Option[String]], Option[Option[String]], Option[Option[String]], Option[Option[String]], Option[Option[String]], Option[Option[Int]], Option[Option[Int]], Option[Option[Int]], Option[Option[Int]], Option[Option[Int]])] ): (List[String], List[(String, Int)]) = {
    if (numberOfPlayers == 3) {
      val loadedNamesList: List[String] = List(loadedRoundManagerTuple.head._8.toString, loadedRoundManagerTuple.head._9.toString,
        loadedRoundManagerTuple.head._10.toString)

      val loadedScore: List[(String, Int)] = List(("Player 1", loadedRoundManagerTuple.head._13.head.get),
        ("Player 2", loadedRoundManagerTuple.head._14.head.get), ("Player 4", loadedRoundManagerTuple.head._15.head.get))
      (loadedNamesList, loadedScore)
    } else if (numberOfPlayers == 4) {
      val loadedNamesList: List[String] = List(loadedRoundManagerTuple.head._8.toString, loadedRoundManagerTuple.head._9.toString,
        loadedRoundManagerTuple.head._10.toString, loadedRoundManagerTuple.head._11.toString)

      val loadedScore: List[(String, Int)] = List(("Player 1", loadedRoundManagerTuple.head._13.head.get),
        ("Player 2", loadedRoundManagerTuple.head._14.head.get), ("Player 4", loadedRoundManagerTuple.head._15.head.get),
        ("Player 4", loadedRoundManagerTuple.head._16.head.get))
      (loadedNamesList, loadedScore)
    } else {
      val loadedNamesList: List[String] = List(loadedRoundManagerTuple.head._8.toString, loadedRoundManagerTuple.head._9.toString,
        loadedRoundManagerTuple.head._10.toString, loadedRoundManagerTuple.head._11.toString, loadedRoundManagerTuple.head._12.toString)

      val loadedScore: List[(String, Int)] = List(("Player 1", loadedRoundManagerTuple.head._13.head.get),
        ("Player 2", loadedRoundManagerTuple.head._14.head.get), ("Player 4", loadedRoundManagerTuple.head._15.head.get),
        ("Player 4", loadedRoundManagerTuple.head._16.head.get), ("Player 5", loadedRoundManagerTuple.head._17.head.get))
      (loadedNamesList, loadedScore)
    }
  }

  override def update(controllerState: String, roundmanager: IRoundmanager): Boolean = {
    try {
      val currentRoundmanger: Roundmanager = roundmanager.getCurrentInstance
      val namesTuple = currentRoundmanger.names match {
        case List(a, b, c) => (0, Option(a), Option(b), Option(c), Option(null), Option(null))
        case List(a, b, c, d) => (0, Option(a), Option(b), Option(c), Option(d), Option(null))
        case List(a, b, c, d, e) => (0, Option(a), Option(b), Option(c), Option(d), Option(e))
      }
      val namesInsert = (namesTable returning namesTable.map(_.namesId) += (namesTuple))

      val scoreTuple = currentRoundmanger.score match {
        case List((aString, aInt: Int), (bString, bInt: Int), (cString, cInt: Int)) => (0, Option(aInt), Option(bInt), Option(cInt), None, None)
        case List((aString, aInt: Int), (bString, bInt: Int), (cString, cInt: Int), (dString, dInt: Int)) => (0, Option(aInt), Option(bInt), Option(cInt), Option(dInt), None)
        case List((aString, aInt: Int), (bString, bInt: Int), (cString, cInt: Int), (dString, dInt: Int), (eString, eInt: Int))
        => (0, Option(aInt), Option(bInt), Option(cInt), Option(dInt), Option(eInt))
        case _ => (0, None, None, None, None, None)
      }
      val scoreInsert = (scoreTable returning scoreTable.map(_.scoreId) += (scoreTuple))

      db.run(scoreTable.delete)
      db.run(namesTable.delete)
      val scoreId = Await.result(db.run(scoreInsert), Duration(1, TimeUnit.SECONDS))
      val namesId = Await.result(db.run(namesInsert), Duration(1, TimeUnit.SECONDS))

      val roundmanagerInsert = roundManagerTable.map(c => (c.numberOfPlayers, c.turn, c.emptyDeckCount, c.gameEnd,
        c.roundStatus, c.playerTurn, c.controllerSate, c.scoreIdFKey, c.namesIdFKey)) += (
        Option(currentRoundmanger.numberOfPlayers), Option(currentRoundmanger.turn), Option(currentRoundmanger.emptyDeckCount),
        Option(currentRoundmanger.gameEnd), Option(currentRoundmanger.roundStatus.toString), Option(currentRoundmanger.playerTurn),
        Option(controllerState), Option(scoreId), Option(namesId))

      db.run(roundManagerTable.delete)
      db.run(roundmanagerInsert)
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def delete: Boolean = {
    try {
      db.run(scoreTable.delete)
      db.run(namesTable.delete)
      db.run(roundManagerTable.delete)
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

}
