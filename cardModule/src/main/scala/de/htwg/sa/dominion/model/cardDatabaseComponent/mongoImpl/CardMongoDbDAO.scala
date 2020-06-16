package de.htwg.sa.dominion.model.cardDatabaseComponent.mongoImpl

import com.mongodb.BasicDBObject
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import play.api.libs.json.Json

import scala.util.Try

class CardMongoDbDAO extends ICardDatabase {
  val uri: String = "mongodb+srv://dominionUser:dominion@dominioncluster-fnmjl.mongodb.net/Dominion?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("Dominion")

  val playingDecksCollection: MongoCollection[Document] = database.getCollection("playingDecks")
  val trashCollection: MongoCollection[Document] = database.getCollection("trash")
  val playerHandCollection: MongoCollection[Document] = database.getCollection("playerHand")
  val playerDeckCollection: MongoCollection[Document] = database.getCollection("playerDeck")
  val playerStackerCollection: MongoCollection[Document] = database.getCollection("playerStacker")

  override def create: Boolean = {
    try {
      database.createCollection("playingDecks").head()
      database.createCollection("trash").head()
      database.createCollection("playerHand").head()
      database.createCollection("playerDeck").head()
      database.createCollection("playerStacker").head()
      true
    } catch  {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def read(playerId: Option[Int]): (List[List[Card]], List[Card], List[Card], List[Card], List[Card]) = ???

  override def update(playingDecks: Option[List[List[Card]]], trashList: Option[List[Card]], handCards: Option[List[Card]],
                      stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Boolean = {
    try {
      if (playingDecks.isDefined) {
        for (doc <- playingDecksCollection.find()) playingDecksCollection.deleteMany(doc).head()
        for (doc <- trashCollection.find()) trashCollection.deleteMany(doc).head()

        val playingDecksDoc: Document = Document(Json.prettyPrint(Json.toJson(playingDecks.head)))
        val trashDoc: Document = Document(Json.prettyPrint(Json.toJson(trashList.head)))

        playingDecksCollection.insertOne(playingDecksDoc).head()
        trashCollection.insertOne(trashDoc).head()
      } else {
        for (doc <- playerHandCollection.find()) playerHandCollection.deleteMany(doc).head()
        for (doc <- playerDeckCollection.find()) playerDeckCollection.deleteMany(doc).head()
        for (doc <- playerStackerCollection.find()) playerStackerCollection.deleteMany(doc).head()

        val playerHandDoc: Document = Document(Json.prettyPrint(Json.toJson(playerId.get, handCards.head)))
        val playerStackerDoc: Document = Document(Json.prettyPrint(Json.toJson(playerId.get, stackerCards.head)))
        val playerDeckDoc: Document = Document(Json.prettyPrint(Json.toJson(playerId.get, deckCards.head)))

        playerHandCollection.insertOne(playerHandDoc)
        playerDeckCollection.insertOne(playerStackerDoc)
        playerStackerCollection.insertOne(playerDeckDoc)
      }
      true
    } catch  {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }

  override def delete: Boolean = {
    try {
      for (doc <- playingDecksCollection.find()) playingDecksCollection.deleteMany(doc).head()
      for (doc <- trashCollection.find()) trashCollection.deleteMany(doc).head()
      for (doc <- playerHandCollection.find()) playerHandCollection.deleteMany(doc).head()
      for (doc <- playerDeckCollection.find()) playerDeckCollection.deleteMany(doc).head()
      for (doc <- playerStackerCollection.find()) playerStackerCollection.deleteMany(doc).head()
      true
    } catch {
      case error: Error =>
        println("Database error: ", error)
        false
    }
  }
}
