package de.htwg.sa.dominion.model.roundmanagerComponent
import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards}
import de.htwg.sa.dominion.model.playercomponent.Player
import org.scalatest.{Matchers, WordSpec}

class RoundmanagerSpec extends WordSpec with Matchers {
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handLuca1: List[Card] = List(Cards.village,Cards.village,Cards.village,Cards.village,Cards.village)
  val Luca8 = Player("Luca", 0, Nil, handLuca1, Nil, 1, 1, 5)
  val Luca = Player("Luca", 0, Nil, handLuca1, Nil, 1, 1, 0)
  val copper: List[Card] = List(Cards.copper, Cards.copper)
  val silver: List[Card] = List(Cards.silver,Cards.silver)
  val decks: List[List[Card]] = List(copper,silver)
  val playerList: List[Player] = List(Luca,Luca8)
  val roundmanager: Roundmanager = Roundmanager (playerList, Nil, 2, 1, Nil, 0, gameEnd = false, Nil,
    RoundmanagerStatus.PLAY_CARD_PHASE, 1, Nil)
  val roundmanagervalidateBuySelect: Roundmanager = Roundmanager (playerList, Nil, 2, 1, decks, 0, gameEnd = false, Nil,
    RoundmanagerStatus.PLAY_CARD_PHASE, 1, Nil)
  //Hurensohn
  "A Player" when {
    "new" should {
      "have a checkifBuyLeft method" in {
       roundmanager.checkIfBuyLeft(playerList) should be (true)
      }
      "have a validateBuySelectInput method" in {
        roundmanager.validateBuySelectInput("0") should be (false)
        roundmanagervalidateBuySelect.validateBuySelectInput("1") should be (true)
      }
      "have a validateHandSelectInputActionCard method" in {
        //roundmanagervalidateBuySelect.copy(playerTurn = 0).validateHandSelectInputActionCard("4") should be (true)
        roundmanagervalidateBuySelect.validateHandSelectInputActionCard("") should be (false)
      }
      "have a validateWorkshopInputForPlayingDecks method" in {
        roundmanagervalidateBuySelect.validateWorkshopInputForPlayingDecks("1") should not be (true)
        roundmanagervalidateBuySelect.validateWorkshopInputForPlayingDecks("") should be (false)
      }
    }
  }
}
