package de.htwg.wt.dominion.util

import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card

case class DatabaseCard(cards :List[Card])

object DatabaseCard {
  import play.api.libs.json._
  implicit val playerReads: Reads[DatabaseCard] = Json.reads[DatabaseCard]
  implicit val playerWrites: OWrites[DatabaseCard] = Json.writes[DatabaseCard]
}
