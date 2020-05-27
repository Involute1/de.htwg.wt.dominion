package de.htwg.sa.dominion.util

import de.htwg.sa.dominion.model.playerComponent.IPlayer
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import play.api.libs.json
import play.api.libs.json.{JsObject, JsPath, JsValue, Writes}

case class UpdatedActionsContainer(player: IPlayer)

object UpdatedActionsContainer {
  import play.api.libs.json.{Json, OWrites, Reads}
  implicit val containerWrites: Writes[UpdatedActionsContainer] = new Writes[UpdatedActionsContainer] {
    def writes(p: UpdatedActionsContainer): JsValue = {
      Json.obj("player" -> p.player.toJson)
    }
  }
  implicit val containerReads: Reads[Player] = Json.reads[Player]
}
