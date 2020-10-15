package de.htwg.wt.dominion.model.cardDatabaseComponent

import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card

import scala.util.Try

trait ICardDatabase {
  def create: Boolean

  def read(playerId: Option[Int]): (List[List[Card]], List[Card], List[Card], List[Card], List[Card])

  def update(playingDecks: Option[List[List[Card]]], trash: Option[List[Card]], handCards: Option[List[Card]], stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Boolean

  def delete: Boolean
}
