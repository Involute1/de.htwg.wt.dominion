package de.htwg.sa.dominion.model.roundmanagerComponent
import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards}
import de.htwg.sa.dominion.model.playercomponent.Player
import org.scalatest.{Matchers, WordSpec}

class RoundmanagerSpec extends WordSpec with Matchers {
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handLuca1: List[Card] = List(Cards.village,Cards.village,Cards.village,Cards.village,Cards.village)
  val handRemodel: List[Card] = List(Cards.remodel, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val Luca8 = Player("Luca", 0, Nil, handLuca1, handLuca1, 1, 1, 5)
  val Luca = Player("Luca", 0, Nil, Nil, handLuca1, 1, 1, 0)
  val LucaRemodel = Player("Luca", 0, Nil, handRemodel, handRemodel, 1, 1, 0)
  val copper: List[Card] = List(Cards.copper, Cards.copper)
  val silver: List[Card] = List(Cards.silver,Cards.silver)
  val decks: List[List[Card]] = List(copper,silver)
  val playerList: List[Player] = List(Luca,Luca8)
  val playerListRemodel: List[Player] = List(LucaRemodel,LucaRemodel)
  val trash: List[Card] = List(Cards.copper, Cards.silver)
  val roundmanager: Roundmanager = Roundmanager (playerList, Nil, 2, 1, Nil, 0, gameEnd = false, Nil,
    RoundmanagerStatus.PLAY_CARD_PHASE, 1, Nil)
  val roundmanagervalidateBuySelect: Roundmanager = Roundmanager (playerList, Nil, 2, 1, decks, 0, gameEnd = false, Nil,
    RoundmanagerStatus.PLAY_CARD_PHASE, 1, Nil)
  val roundmanagervalidateRemodel: Roundmanager = Roundmanager (playerListRemodel, Nil, 2, 1, decks, 0, gameEnd = false, Nil,
    RoundmanagerStatus.PLAY_CARD_PHASE, 1, trash)
  "A Roundmanager" when {
    "new" should {
      "have a checkifBuyLeft method" in {
       roundmanager.checkIfBuyLeft(playerList) should be (true)
      }
      "have a validateBuySelectInput method" in {
        roundmanager.validateBuySelectInput("0") should be (false)
        roundmanagervalidateBuySelect.validateBuySelectInput("1") should be (true)
      }
      "have a validateHandSelectInputActionCard method" in {
        roundmanagervalidateBuySelect.copy(playerTurn = 0).validateHandSelectInputActionCard("1") should be (true)
        roundmanagervalidateBuySelect.validateHandSelectInputActionCard("") should be (false)
      }
      "have a validateWorkshopInputForPlayingDecks method" in {
        roundmanagervalidateBuySelect.validateWorkshopInputForPlayingDecks("0") should  be (true)
        roundmanagervalidateBuySelect.validateWorkshopInputForPlayingDecks("") should be (false)
      }
      "have a validateRemodelInputForPlayingDecks method" in {
        roundmanagervalidateRemodel.validateRemodelInputForPlayingDecks("0") should be (true)
        roundmanager.validateRemodelInputForPlayingDecks("") should be (false)
      }
    }
  }
}
