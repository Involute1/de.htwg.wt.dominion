package de.htwg.sa.dominion.model.cardDatabaseComponent

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

import scala.util.Try

trait ICardDatabase {
  def create: Boolean

  def read(playerId: Option[Int]): (Option[List[List[Card]]], Option[List[Card]], Option[List[Card]], Option[List[Card]])

  def update(playingDecks: Option[List[List[Card]]], handCards: Option[List[Card]], stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Boolean

  def delete: Boolean
}
