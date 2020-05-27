package de.htwg.sa.dominion.controller.util

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

case class UpdatedPlayerActions(actions: Int)
  object UpdatedPlayerActions {
    import play.api.libs.json._
    implicit  val containerWrites: OWrites[UpdatedPlayerActions] = Json.writes[UpdatedPlayerActions]
    implicit val containerReads: Reads[UpdatedPlayerActions] = Json.reads[UpdatedPlayerActions]
  }
