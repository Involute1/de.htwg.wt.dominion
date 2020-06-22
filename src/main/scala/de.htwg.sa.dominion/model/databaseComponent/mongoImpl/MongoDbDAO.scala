package de.htwg.sa.dominion.model.databaseComponent.mongoImpl

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.{Roundmanager, RoundmanagerStatus}
import de.htwg.sa.dominion.util.DatabaseRoundManager
import org.mongodb.scala._
import play.api.libs.json.{JsError, JsNumber, JsSuccess, JsValue, Json}

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.Duration


class MongoDbDAO extends IDominionDatabase with PlayJsonSupport {

  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/Dominion?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("Dominion")
  val roundManagerCollection: MongoCollection[Document] = database.getCollection("roundManager")

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def create: Boolean = {
    try {
      database.createCollection("roundManager").head()
      true
    } catch  {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def read(): (String, Roundmanager) = {
    val doc = Await.result(roundManagerCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
    val jsonDoc = Json.parse(doc.toJson())
    val loadedDatabaseRoundManager = jsonDoc.validate[DatabaseRoundManager].get

    val loadedPlayerList: List[Player] = {
      val response = Http().singleRequest(Get("http://player:8081/player/load"))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[List[Player]])
      Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
    }
    val loadedPlayingDecks: (List[List[Card]], List[Card]) = {
      val response = Http().singleRequest(Get("http://card:8082/card/loadPlayingDecks"))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[(List[List[Card]], List[Card], List[Card], List[Card], List[Card])])
      val res = Await.result(jsonFuture, Duration(1, TimeUnit.SECONDS))
      (res._1, res._2)
    }

    val loadedRoundManager: Roundmanager = Roundmanager(loadedPlayerList, loadedDatabaseRoundManager.names, loadedDatabaseRoundManager.numberOfPlayers, loadedDatabaseRoundManager.turn,
      loadedPlayingDecks._1, loadedDatabaseRoundManager.emptyDeckCount, loadedDatabaseRoundManager.gameEnd, loadedDatabaseRoundManager.score,
      loadedDatabaseRoundManager.roundStatus, loadedDatabaseRoundManager.playerTurn, loadedPlayingDecks._2)
    (loadedDatabaseRoundManager.controllerStateString, loadedRoundManager)
  }

  override def update(controllerState: String, roundmanager: IRoundmanager): Boolean = {
    try {
      for (doc <- roundManagerCollection.find()) roundManagerCollection.deleteMany(doc).head()
      val roundManagerToSave = roundmanager.getCurrentInstance
      val dbRoundManager = DatabaseRoundManager(controllerState, roundManagerToSave.names, roundManagerToSave.numberOfPlayers,
        roundManagerToSave.turn, roundManagerToSave.emptyDeckCount, roundManagerToSave.gameEnd, roundManagerToSave.score,
        roundManagerToSave.roundStatus, roundManagerToSave.playerTurn)

      val roundManagerDoc: Document = Document(Json.prettyPrint(Json.toJson(dbRoundManager)))
      roundManagerCollection.insertOne(roundManagerDoc).head()
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def delete: Boolean = {
    try {
      for (doc <- roundManagerCollection.find()) roundManagerCollection.deleteMany(doc).head()
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }
}
