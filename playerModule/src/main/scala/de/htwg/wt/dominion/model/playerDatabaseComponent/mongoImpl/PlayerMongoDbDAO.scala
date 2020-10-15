package de.htwg.wt.dominion.model.playerDatabaseComponent.mongoImpl

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.wt.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.wt.dominion.util.DatabasePlayer
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import play.api.libs.json.Json

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.util.Try

class PlayerMongoDbDAO extends IPlayerDatabase with PlayJsonSupport {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/Dominion?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("Dominion")
  val playerCollection: MongoCollection[Document] = database.getCollection("player")

  override def create: Boolean = {
    try {
      database.createCollection("player").head()
      true
    } catch  {
      case error: Error =>
      println("Database error: ", error)
      false
    }
  }

  override def read(): List[Player] = {

    val docSeq: Seq[Document] = Await.result(playerCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS))
    val loadedDatabasePlayerList: Seq[DatabasePlayer] = for (doc <- docSeq) yield {
      Json.parse(doc.toJson()).validate[DatabasePlayer].get
    }

    val loadedPlayers = for (dbPlayer <- loadedDatabasePlayerList.toList) yield {
      val loadedCards = {
        val response = Http().singleRequest(Get("http://card:8082/card/load", dbPlayer.value))
        val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[(List[List[Card]], List[Card], List[Card], List[Card], List[Card])])
        val result = Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
        (result._3, result._4, result._5)
      }
      Player(dbPlayer.name, dbPlayer.value, loadedCards._3, loadedCards._2, loadedCards._1, dbPlayer.actions, dbPlayer.buys, dbPlayer.money)
    }
    loadedPlayers
  }

  override def update(playerList: List[Player]): Boolean = {
    try {
      for (doc <- playerCollection.find()) playerCollection.deleteMany(doc).head()
      for (player <- playerList) {
        val dbPlayer = DatabasePlayer(player.name, player.value, player.actions, player.buys, player.money)
        val playerDoc: Document = Document(Json.prettyPrint(Json.toJson(dbPlayer)))
        playerCollection.insertOne(playerDoc).head()
        Http().singleRequest(Get("http://card:8082/card/save", (player.handCards, player.stacker, player.deck, player.value)))
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
      for (doc <- playerCollection.find()) playerCollection.deleteMany(doc).head()
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }
}
