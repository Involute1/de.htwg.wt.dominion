package de.htwg.sa.dominion.model.playerDatabaseComponent.slickImpl

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.sa.dominion.util.PlayerTables.PlayerTable
import slick.jdbc.SQLServerProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._
import akka.http.scaladsl.client.RequestBuilding._
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.Duration
import scala.util.Try

class PlayerMsSqlDAO extends IPlayerDatabase with PlayJsonSupport {

  val playerTable = TableQuery[PlayerTable]

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def create: Try[Boolean] = {
    Try {
      val setup = DBIO.seq(playerTable.schema.createIfNotExists)
      db.run(setup)
      true
    }
  }

  override def read(): Unit = ???

  override def update(playerList: List[Player]): Try[Boolean] = {
    Try {
      for (player <- playerList) {
        val deletePlayerQuery = playerTable.filter(_.name === player.name).delete
        db.run(deletePlayerQuery)
        val playerInsert = (playerTable returning playerTable.map(_.id)) += (0, Option(player.name), Option(player.value),
          Option(player.actions), Option(player.buys), Option(player.money))
        val playerId = Await.result(db.run(playerInsert), Duration(1, TimeUnit.SECONDS))
        Http().singleRequest(Get("http://0.0.0.0:8082/card/save", (player.handCards, player.stacker, player.deck, playerId)))
      }
      true
    }
  }

  override def delete: Try[Boolean] = ???
}
