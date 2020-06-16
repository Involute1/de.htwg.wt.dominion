package de.htwg.sa.dominion.util

import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.RoundmanagerStatus.RoundmanagerStatus

case class DatabaseRoundManager(controllerStateString: String, names: List[String], numberOfPlayers: Int, turn: Int,
                                emptyDeckCount: Int, gameEnd: Boolean, score: List[(String, Int)],
                                roundStatus: RoundmanagerStatus, playerTurn: Int)

object DatabaseRoundManager {
  import play.api.libs.json._
  implicit val playerReads: Reads[DatabaseRoundManager] = Json.reads[DatabaseRoundManager]
  implicit val playerWrites: OWrites[DatabaseRoundManager] = Json.writes[DatabaseRoundManager]
}