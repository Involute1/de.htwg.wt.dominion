package de.htwg.sa.dominion.model.playerDatabaseComponent.mongoImpl

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.sa.dominion.util.{DatabasePlayer, DatabaseRoundManager}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import play.api.libs.json.Json

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.Duration
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
    val playerList: List[Player] = for(doc <- Await.result(playerCollection.find().head(), Duration(1, TimeUnit.SECONDS))) yield {
      //val singledoc = Await.result(playerCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
      val jsonDoc = Json.parse(doc.toJson())
      val loadedPlayerDatabase = jsonDoc.validate[DatabasePlayer].get
      val response = Http().singleRequest(Get("http://0.0.0.0:8082/card/load", loadedPlayerDatabase.value))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[(List[List[Card]], List[Card], List[Card], List[Card], List[Card])])
      val res = Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
      val Player1: Player = Player(loadedPlayerDatabase.name, loadedPlayerDatabase.value, res._5, res._4, res._3, loadedPlayerDatabase.actions, loadedPlayerDatabase.buys, loadedPlayerDatabase.money)
      Player1
    }
    playerList
  }

  override def update(playerList: List[Player]): Boolean = {
    try {
      for (doc <- playerCollection.find()) playerCollection.deleteMany(doc).head()
      for (player <- playerList) {
        val dbPlayer = DatabasePlayer(player.name, player.value, player.actions, player.buys, player.money)
        val playerDoc: Document = Document(Json.prettyPrint(Json.toJson(dbPlayer)))
        playerCollection.insertOne(playerDoc).head()
        Http().singleRequest(Get("http://0.0.0.0:8082/card/save", (player.handCards, player.stacker, player.deck, player.value)))
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
