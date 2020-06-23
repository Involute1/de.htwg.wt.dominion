package de.htwg.sa.dominion.model.cardDatabaseComponent.mongoImpl

import java.util.concurrent.TimeUnit

import com.mongodb.BasicDBObject
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase
import de.htwg.sa.dominion.util.{DatabaseCard, DatabaseList}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.duration.Duration
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

  override def read(playerId: Option[Int]): (List[List[Card]], List[Card], List[Card], List[Card], List[Card]) = {
    if (playerId.isDefined) {
      val playerHandDoc = Await.result(playerHandCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
      val jsonPlayerHandDoc = Json.parse(playerHandDoc.toJson())
      val loadedPlayerHandCollection = jsonPlayerHandDoc.validate[List[Card]].get

      val playerDeckDoc = Await.result(playerDeckCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
      val jsonPlayerDeckDoc = Json.parse(playerDeckDoc.toJson())
      val loadedPlayerDeckCollection = jsonPlayerDeckDoc.validate[List[Card]].get

      val playerStackerDoc = Await.result(playerStackerCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
      val jsonPlayerStackerDoc = Json.parse(playerStackerDoc.toJson())
      val loadedPlayerStackCollection = jsonPlayerStackerDoc.validate[List[Card]].get
      (Nil, Nil, loadedPlayerHandCollection, loadedPlayerDeckCollection, loadedPlayerStackCollection)
    } else {
      val playingDeckDoc = Await.result(playerDeckCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
      val jsonPlayingDeckDoc = Json.parse(playingDeckDoc.toJson())
      val loadedPlayingDecksCollection = jsonPlayingDeckDoc.validate[List[List[Card]]].get

      val trashDoc = Await.result(trashCollection.find().first().head(), Duration(1, TimeUnit.SECONDS))
      val jsonTrashDoc = Json.parse(trashDoc.toJson())
      val loadedTrashCollection = jsonTrashDoc.validate[List[Card]].get
      (loadedPlayingDecksCollection, loadedTrashCollection, Nil, Nil, Nil)
    }
  }

  override def update(playingDecks: Option[List[List[Card]]], trashList: Option[List[Card]], handCards: Option[List[Card]],
                      stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Boolean = {
    try {
      if (playingDecks.isDefined) {
        for (doc <- playingDecksCollection.find()) playingDecksCollection.deleteMany(doc).head()
        for (doc <- trashCollection.find()) trashCollection.deleteMany(doc).head()

        val dbPlayingDecks = DatabaseList(playingDecks.get)
        val dbTrash = DatabaseCard(trashList.get)
        val playingDecksDoc: Document = Document(Json.prettyPrint(Json.toJson(dbPlayingDecks)))
        val trashDoc: Document = Document(Json.prettyPrint(Json.toJson(dbTrash)))

        playingDecksCollection.insertOne(playingDecksDoc).head()
        trashCollection.insertOne(trashDoc).head()
      } else {
        for (doc <- playerHandCollection.find()) playerHandCollection.deleteMany(doc).head()
        for (doc <- playerDeckCollection.find()) playerDeckCollection.deleteMany(doc).head()
        for (doc <- playerStackerCollection.find()) playerStackerCollection.deleteMany(doc).head()

        val dbHandcard = DatabaseCard(handCards.get)
        val dbStackercard = DatabaseCard(stackerCards.get)
        val dbDeckcard = DatabaseCard(deckCards.get)
        val playerHandDoc: Document = Document(Json.prettyPrint(Json.toJson(dbHandcard)))
        val playerStackerDoc: Document = Document(Json.prettyPrint(Json.toJson(dbStackercard)))
        val playerDeckDoc: Document = Document(Json.prettyPrint(Json.toJson(dbDeckcard)))

        playerHandCollection.insertOne(playerHandDoc).head()
        playerDeckCollection.insertOne(playerDeckDoc).head()
        playerStackerCollection.insertOne(playerStackerDoc).head()
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
