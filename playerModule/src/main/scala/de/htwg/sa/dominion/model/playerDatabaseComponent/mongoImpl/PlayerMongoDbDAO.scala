package de.htwg.sa.dominion.model.playerDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

import scala.util.Try

class PlayerMongoDbDAO extends IPlayerDatabase {
  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/DominionCluster?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient()
  val database: MongoDatabase = client.getDatabase("Dominion")
  val roundManagerCollection: MongoCollection[Document] = database.getCollection("roundManager")

  override def create: Boolean = ???

  override def read(): List[Player] = ???

  override def update(playerList: List[Player]): Boolean = ???

  override def delete: Boolean = ???
}
