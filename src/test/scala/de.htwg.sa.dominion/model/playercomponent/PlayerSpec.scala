package de.htwg.sa.dominion.model.playercomponent

import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards}
import org.scalatest.{Matchers, WordSpec}


class PlayerSpec extends WordSpec with Matchers {
  val deckLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val stackerLuca: List[Card] = Nil
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val Luca = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 1, 1, 0,0)
}
