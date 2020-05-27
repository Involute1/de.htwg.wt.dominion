package de.htwg.sa.dominion.controller.util

case class UpdatedPlayerBuys(buys: Int)
object UpdatedPlayerBuys {
  import play.api.libs.json._
  implicit  val containerWrites: OWrites[UpdatedPlayerBuys] = Json.writes[UpdatedPlayerBuys]
  implicit val containerReads: Reads[UpdatedPlayerBuys] = Json.reads[UpdatedPlayerBuys]
}
