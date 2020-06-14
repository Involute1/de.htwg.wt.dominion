package de.htwg.sa.dominion.model.cardDatabaseComponent

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

import scala.util.Try

trait ICardDatabase {
  def create: Try[Boolean]

  def read(): Unit

  def update(playingDecks: Option[List[List[Card]]], handCards: Option[List[Card]], stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Try[Boolean]

  def delete: Try[Boolean]
}
