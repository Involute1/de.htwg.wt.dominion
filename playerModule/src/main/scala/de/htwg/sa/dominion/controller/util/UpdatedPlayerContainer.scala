package de.htwg.sa.dominion.controller.util

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

case class UpdatedPlayerContainer (name: String, value: Int, deck: List[Card], stacker: List[Card], handCards: List[Card],
                                   actions: Int, buys: Int, money: Int)
  object UpdatedPlayerContainer {
    import play.api.libs.json._
    implicit  val containerWrites: OWrites[UpdatedPlayerContainer] = Json.writes[UpdatedPlayerContainer]
    implicit val constainerReads: Reads[UpdatedPlayerContainer] = Json.reads[UpdatedPlayerContainer]
  }
