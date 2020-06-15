package de.htwg.sa.dominion.model.cardDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

import scala.util.Try

class CardMongoDbDAO extends ICardDatabase {
  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/DominionCluster?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient()
  val database: MongoDatabase = client.getDatabase("Dominion")
  val roundManagerCollection: MongoCollection[Document] = database.getCollection("roundManager")

  override def create: Boolean = ???

  override def read(playerId: Option[Int]): (List[List[Card]], List[Card], List[Card], List[Card], List[Card]) = ???

  override def update(playingDecks: Option[List[List[Card]]], trashList: Option[List[Card]], handCards: Option[List[Card]],
                      stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Boolean = ???

  override def delete: Boolean = ???
}
