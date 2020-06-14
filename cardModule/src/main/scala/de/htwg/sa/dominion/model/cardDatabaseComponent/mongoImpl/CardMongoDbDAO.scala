package de.htwg.sa.dominion.model.cardDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase

import scala.util.Try

class CardMongoDbDAO extends ICardDatabase {
  override def create: Try[Boolean] = ???

  override def read(): Unit = ???

  override def update(playingDecks: Option[List[List[Card]]], handCards: Option[List[Card]], stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
