package de.htwg.wt.dominion.model.playerDatabaseComponent.slickImpl

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import de.htwg.wt.dominion.util.PlayerTables.PlayerTable
import slick.jdbc.SQLServerProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._
import akka.http.scaladsl.client.RequestBuilding._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.wt.dominion.model.playerDatabaseComponent.IPlayerDatabase

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.Duration
import scala.util.Try

class PlayerMsSqlDAO extends IPlayerDatabase with PlayJsonSupport {

  val playerTable = TableQuery[PlayerTable]

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def create: Boolean = {
    try {
      val setup = DBIO.seq(playerTable.schema.createIfNotExists)
      db.run(setup)
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def read(): List[Player] = {
    val playerRowsSelect = for (p <- playerTable) yield (p.name, p.value, p.actions, p.buys, p.money)
    val playerTuples = Await.result(db.run(playerRowsSelect.result), Duration(1, TimeUnit.SECONDS))
    val loadedPlayerList = for (p <- playerTuples.indices) yield {
      val playerValue = playerTuples(p)._2.get
      val response = Http().singleRequest(Get("http://card:8082/card/load", playerValue))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[(List[List[Card]], List[Card], List[Card], List[Card], List[Card])])
      val res = Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
      Player(playerTuples(p)._1.get, playerValue, res._3, res._4, res._5, playerTuples(p)._3.get, playerTuples(p)._4.get, playerTuples(p)._5.get)
    }
    loadedPlayerList.toList
  }

  override def update(playerList: List[Player]): Boolean = {
    try {
      for (player <- playerList) {
        val deletePlayerQuery = playerTable.filter(_.name === player.name).delete
        db.run(deletePlayerQuery)
        val playerInsert = (playerTable returning playerTable.map(_.id)) += (0, Option(player.name), Option(player.value),
          Option(player.actions), Option(player.buys), Option(player.money))
        val playerId = Await.result(db.run(playerInsert), Duration(1, TimeUnit.SECONDS))
        Http().singleRequest(Get("http://card:8082/card/save", (player.handCards, player.stacker, player.deck, playerId)))
      }
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def delete: Boolean = {
    try {
      db.run(playerTable.delete)
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }
}
