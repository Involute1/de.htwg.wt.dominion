package de.htwg.sa.dominion.util

case class IntListContainer(list: List[Int])

object IntListContainer {
  import play.api.libs.json._
  implicit val containerWrites: OWrites[IntListContainer] = Json.writes[IntListContainer]
  implicit val containerReads: Reads[IntListContainer] = Json.reads[IntListContainer]
}

