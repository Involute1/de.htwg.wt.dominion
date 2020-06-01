package de.htwg.sa.dominion.util

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player

case class PlayerHandStringContainer(player: Player)

object PlayerHandStringContainer {
  import play.api.libs.json._
  implicit val containerWrites: OWrites[PlayerHandStringContainer] = Json.writes[PlayerHandStringContainer]
  implicit val containerReads: Reads[PlayerHandStringContainer] = Json.reads[PlayerHandStringContainer]
}
