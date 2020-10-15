package de.htwg.wt.dominion.controller

import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card

trait ICardController {

  def save(playingDecks: Option[List[List[Card]]], trash: Option[List[Card]], handCards: Option[List[Card]], stackerCards: Option[List[Card]], deckCards: Option[List[Card]], playerId: Option[Int]): Unit

  def load(playerId: Option[Int]): (List[List[Card]], List[Card], List[Card], List[Card], List[Card])

  def constructCardNameString(): String

  def constructCardInfoString(): String
}
