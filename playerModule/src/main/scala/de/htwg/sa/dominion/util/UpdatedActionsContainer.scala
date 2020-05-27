package de.htwg.sa.dominion.util

import de.htwg.sa.dominion.model.playerComponent.IPlayer
import play.api.libs.json.JsObject

case class UpdatedActionsContainer(player: IPlayer)

object UpdatedActionsContainer {
  import play.api.libs.json.{Json, OWrites, Reads}
  implicit val containerWrites: OWrites[UpdatedActionsContainer] = Json.writes[UpdatedActionsContainer]
  implicit val containerReads: Reads[UpdatedActionsContainer] = Json.reads[UpdatedActionsContainer]
}
