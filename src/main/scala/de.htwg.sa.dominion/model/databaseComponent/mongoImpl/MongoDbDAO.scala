package de.htwg.sa.dominion.model.databaseComponent.mongoImpl

import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import play.api.libs.json.Json

import scala.util.Try


class MongoDbDAO extends IDominionDatabase {

  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/DominionCluster?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient()
  val database: MongoDatabase = client.getDatabase("Dominion")
  val roundManagerCollection: MongoCollection[Document] = database.getCollection("roundManager")


  override def create: Boolean = ???

  override def read(): (String, Roundmanager) = ???

  override def update(controllerState: String, roundmanager: IRoundmanager): Boolean = {
    try {
      val roundmanagerDoc: Document = Document(Json.prettyPrint(roundmanager.toJson))
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
