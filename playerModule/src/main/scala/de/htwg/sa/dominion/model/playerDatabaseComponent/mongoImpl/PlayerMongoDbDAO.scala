package de.htwg.sa.dominion.model.playerDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

import scala.util.Try

class PlayerMongoDbDAO extends IPlayerDatabase {
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

  override def update(playerList: List[Player]): Boolean = ???

  override def delete: Boolean = ???
}
