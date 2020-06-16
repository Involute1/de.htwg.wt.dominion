package de.htwg.sa.dominion.model.databaseComponent.mongoImpl

import java.util.concurrent.TimeUnit

import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
import de.htwg.sa.dominion.util.DatabaseRoundManager
import org.mongodb.scala._
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class MongoDbDAO extends IDominionDatabase {

  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/Dominion?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("Dominion")
  val roundManagerCollection: MongoCollection[Document] = database.getCollection("roundManager")

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

  override def read(): (String, Roundmanager) = ???

  override def update(controllerState: String, roundmanager: IRoundmanager): Boolean = {
    try {
      val roundManagerToSave = roundmanager.getCurrentInstance
      val dbRoundManager = DatabaseRoundManager(controllerState, roundManagerToSave.names, roundManagerToSave.numberOfPlayers,
        roundManagerToSave.turn, roundManagerToSave.emptyDeckCount, roundManagerToSave.gameEnd, roundManagerToSave.score,
        roundManagerToSave.roundStatus, roundManagerToSave.playerTurn)

      val roundmanagerDoc: Document = Document(Json.prettyPrint())
      roundManagerCollection.insertOne(roundmanagerDoc)
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }

  }

  override def delete: Boolean = ???
}
