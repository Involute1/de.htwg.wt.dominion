package de.htwg.sa.dominion.controller

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

trait ICardController {

  def save(playingDecks: Option[List[List[Card]]], handCards: Option[List[Card]], stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Unit

  def load(playerId: Option[Int]): (Option[List[List[Card]]], Option[List[Card]], Option[List[Card]], Option[List[Card]])

  def constructCardNameString(): String

  def constructCardInfoString(): String
}
