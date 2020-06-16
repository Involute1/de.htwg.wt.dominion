package de.htwg.sa.dominion.model.playerDatabaseComponent.mongoImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.sa.dominion.util.DatabasePlayer
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContextExecutor
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

  override def read(): List[Player] = ???

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
