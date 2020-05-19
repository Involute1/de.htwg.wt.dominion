package de.htwg.sa.dominion.model.roundmanagerComponent
import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards}
import de.htwg.sa.dominion.model.playercomponent.Player
import org.scalatest.{Matchers, WordSpec}

class RoundmanagerSpec extends WordSpec with Matchers {
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val Luca8 = Player("Luca", 0, Nil, handLuca, Nil, 1, 1, 0)
  val Luca = Player("Luca", 0, Nil, Nil, handLuca, 1, 1, 0)
  val playerList: List[Player] = List(Luca,Luca8)
  val roundmanager: Roundmanager = Roundmanager (Nil, Nil, 0, 1, Nil, 0, gameEnd = false, Nil,
    RoundmanagerStatus.PLAY_CARD_PHASE, 0, Nil)
  //Hurensohn
  "A Player" when {
    "new" should {
      "have a checkifBuyLeft method" in {
       roundmanager.checkIfBuyLeft(playerList) should be (true)
      }
    }
  }
}
