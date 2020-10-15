package de.htwg.wt.dominion.util

case class DatabasePlayer(name: String, value: Int, actions: Int, buys: Int, money: Int)

object DatabasePlayer {
  import play.api.libs.json._
  implicit val playerReads: Reads[DatabasePlayer] = Json.reads[DatabasePlayer]
  implicit val playerWrites: OWrites[DatabasePlayer] = Json.writes[DatabasePlayer]
}
