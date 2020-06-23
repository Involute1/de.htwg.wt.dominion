package de.htwg.sa.dominion.util

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

case class DatabaseCard(cards :List[Card])

object DatabaseCard {
  import play.api.libs.json._
  implicit val playerReads: Reads[DatabaseCard] = Json.reads[DatabaseCard]
  implicit val playerWrites: OWrites[DatabaseCard] = Json.writes[DatabaseCard]
}
