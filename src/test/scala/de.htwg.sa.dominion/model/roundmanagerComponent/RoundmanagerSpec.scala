package de.htwg.sa.dominion.model.roundmanagerComponent
import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards}
import de.htwg.sa.dominion.model.playercomponent.Player
import org.scalatest.{Matchers, WordSpec}

class RoundmanagerSpec extends WordSpec with Matchers {
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handLuca1: List[Card] = List(Cards.village,Cards.village,Cards.village,Cards.village,Cards.village)
  val handRemodel: List[Card] = List(Cards.remodel, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handVillage: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handVillage1: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handmine: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val Luca8: Player = Player("Luca", 0, Nil, handLuca1, handLuca1, 1, 1, 5)
  val Luca: Player = Player("Luca", 0, Nil, Nil, handLuca1, 1, 1, 0)
  val LucaRemodel: Player = Player("Luca", 0, Nil, handRemodel, handRemodel, 1, 1, 0)
  val LucaVillage: Player = Player("Luca", 0, List(Cards.copper), Nil, handVillage1, 1, 1, 0)
  val LucaVillageupdated: Player = Player("Luca", 0, Nil, List(Cards.copper), handVillage1, 2, 1, 0)
  val LucaFestivalupdated: Player = Player("Luca", 0, Nil, List(Cards.copper), handVillage1, 2, 1, 2)
  val LucaCellar: Player = Player("Luca", 0, List(Cards.copper), List(Cards.copper), handVillage, 1, 1, 0)
  val LucaMine: Player = Player("Luca", 0, List(Cards.copper), List(Cards.copper), handVillage, 0, 1, 0)
  val LucaMineEnd: Player = Player("Luca", 0, List(Cards.copper), Nil, handmine, 1, 1, 0)
  val copper: List[Card] = List(Cards.copper, Cards.copper)
  val silver: List[Card] = List(Cards.silver,Cards.silver)
  val copperupdated: List[Card] = List(Cards.copper)
  val decks: List[List[Card]] = List(copper,silver)
  val updateddecks: List[List[Card]] = List(copperupdated,silver)
  val playerList: List[Player] = List(Luca,Luca8)
  val playerListRemodel: List[Player] = List(LucaRemodel,LucaRemodel)
  val playerListVillage: List[Player] = List(LucaVillage, LucaVillage)
  val playerListVillageupdated: List[Player] = List(LucaVillage, LucaVillageupdated)
  val playerListFestival: List[Player] = List(LucaVillage,LucaFestivalupdated)
  val playerListcellar: List[Player] = List(LucaVillage,LucaCellar)
  val playerListMine: List[Player] = List(LucaVillage,LucaMine)
  val playerListMineupdated: List[Player] = List(LucaVillage,LucaMineEnd)
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
      "have a validateMineInputForPlayingDecks method" in {
        roundmanagervalidateRemodel.validateRemodelInputForPlayingDecks("1") should be (true)
        roundmanager.validateMineInputForPlayingDecks("") should be (false)
      }
      "have a validateIsInputHandCard method" in {
        roundmanager.validateIsInputAHandCard("") should be (false)
        roundmanager.validateIsInputAHandCard("1") should be (true)
      }
      "have a checkIfInputIsMoneyCard method" in {
        roundmanagervalidateRemodel.checkIfInputIsMoneyCard(-1) should be (false)
        roundmanagervalidateRemodel.checkIfInputIsMoneyCard(1) should be (true)
      }
      "have a validateMultiInputToInt method" in {
        roundmanager.validateMultiInputToInt("1,1") should be (Some(List(1,1)))
        roundmanager.validateMultiInputToInt("1,a") should be (Some(List(1)))
        roundmanager.validateMultiInputToInt("a,a") should be (Some(List()))
        roundmanager.validateMultiInputToInt("a a") should be (None)
      }
      "have a checkMultiInputCorrespondToHandIdx method" in {
        roundmanagervalidateBuySelect.checkMultiInputCorrespondToHandIdx(Some(List(0,1))) should be (true)
        roundmanagervalidateBuySelect.checkMultiInputCorrespondToHandIdx(None) should be (false)
      }
      "have a validtaeYesNoInput method" in {
        roundmanager.validateYesNoInput("yes") should be (true)
        roundmanager.validateYesNoInput("no") should be (false)
      }
      "have a villageAction method" in {
        roundmanagervalidateRemodel.copy(players = playerListVillage).villageAction(1) should be (playerListVillageupdated)
      }
      "have a festivalAction method" in {
        roundmanagervalidateRemodel.copy(players = playerListVillage).festivalAction(1) should be (playerListFestival)
      }
      "have a cellarActionStart method" in {
        roundmanagervalidateRemodel.copy(players = playerListVillage).cellarActionStart(1) should be (playerListcellar)
      }
      "have a cellarActionEnd method" in {

      }
      "havea mineActionStart method" in {
        roundmanagervalidateRemodel.copy(players = playerListVillage).mineActionStart(1) should be (playerListMine)
      }
      "have a minceActionEnd method" in {
        roundmanagervalidateRemodel.copy(players = playerListVillage).mineActioneEnd(0) should be (playerListMineupdated, updateddecks)
      }
      "have a smithyAction method" in {
        val handLucaSmithy: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
        val LucaSmtihy: Player = Player("Luca", 0,List(Cards.copper,Cards.copper,Cards.copper,Cards.copper), Nil, handLuca, 1, 1, 0)
        val LucaSmtihyupdated: Player = Player("Luca", 0,List(Cards.copper), List(Cards.copper), handLucaSmithy, 0, 1, 0)
        val playerListSmithy: List[Player] = List(LucaSmtihy,LucaSmtihy)
        val playerListSmithyupdated: List[Player] = List(LucaSmtihy,LucaSmtihyupdated)
        val roundmanagersmithyAction: Roundmanager = Roundmanager (playerListSmithy, Nil, 2, 1, decks, 0, gameEnd = false, Nil,
          RoundmanagerStatus.PLAY_CARD_PHASE, 1, trash)
        roundmanagersmithyAction.smithyAction(0) should be (playerListSmithyupdated)
      }
      "have a remodelActionStart method" in {

      }
      "have a remodelActionEnd method" in {

      }
      "have a workshopStartAction method" in {

      }
      "have a workshopEndAction method" in {

      }
      "have a marketAction method" in {
        val LucaSmtihy: Player = Player("Luca", 0,List(Cards.copper,Cards.copper,Cards.copper,Cards.copper), Nil, handLuca, 1, 1, 0)
        val LucaMarket: Player = Player("Luca", 0,List(Cards.copper,Cards.copper,Cards.copper), List(Cards.copper), handLuca, 1, 2, 1)
        val playerListSmithy: List[Player] = List(LucaSmtihy,LucaSmtihy)
        val playListMarket: List[Player] = List(LucaSmtihy,LucaMarket)
        val roundmanagermarketAction: Roundmanager = Roundmanager (playerListSmithy, Nil, 2, 1, decks, 0, gameEnd = false, Nil,
          RoundmanagerStatus.PLAY_CARD_PHASE, 1, trash)
        roundmanagermarketAction.marketAction(0) should be (playListMarket)
      }
      "have a merchantAction method" in {
        val LucaSmtihy: Player = Player("Luca", 0,List(Cards.copper,Cards.copper,Cards.copper,Cards.copper), Nil, handLuca, 1, 1, 0)
        val LucaMarket: Player = Player("Luca", 0,List(Cards.copper,Cards.copper,Cards.copper), List(Cards.copper), handLuca, 1, 1, 0)
        val playerListSmithy: List[Player] = List(LucaSmtihy,LucaSmtihy)
        val playListMarket: List[Player] = List(LucaSmtihy,LucaMarket)
        val roundmanagermarketAction: Roundmanager = Roundmanager (playerListSmithy, Nil, 2, 1, decks, 0, gameEnd = false, Nil,
          RoundmanagerStatus.PLAY_CARD_PHASE, 1, trash)
        roundmanagermarketAction.merchantAction(0) should be (playListMarket)
      }
      "have a merchantCheckForSilver method" in {

      }
      "have a dropCardFromDeck method" in {

      }
      "have a buyCard method" in {

      }
      "have buyPhaseAddCardToStackerFromPlayingDecks method" in {

      }
      " have a updateMoneyForRoundmanager method" in {

      }
      "have a addToStackerFromPlayingDecks method" in {
        val copper: List[Card] = List(Cards.copper)
        val silver: List[Card] = List(Cards.silver,Cards.silver)
        val decksupd: List[List[Card]] = List(copper,silver)
        val LucaaddTOstacker: Player = Player("Luca", 0, Nil, Nil, handLuca1, 1, 1, 0)
        val LucaaddTOstacker1: Player = Player("Luca", 0, Nil, List(Cards.copper), handLuca1, 1, 1, 0)
        val playerlistaddTo : List[Player] = List(LucaaddTOstacker,LucaaddTOstacker)
        val playerlistaddTo1 : List[Player] = List(LucaaddTOstacker,LucaaddTOstacker1)
        roundmanagervalidateBuySelect.copy(players = playerlistaddTo).addToStackerFromPlayingDecks(0) should be (playerlistaddTo1, decksupd)
      }
      "have a addToTrash method" in {
        val LucaaddTOTrash: Player = Player("Luca", 0, Nil, Nil, handLuca, 1, 1, 0)
        val LucaaddTOTrash1: Player = Player("Luca", 0, Nil, Nil, List(Cards.copper,Cards.copper,Cards.copper,Cards.copper), 1, 1, 0)
        val playerlistaddToTrash: List[Player] = List(LucaaddTOTrash,LucaaddTOTrash)
        val playerlistaddToTrashupdated: List[Player] = List(LucaaddTOTrash,LucaaddTOTrash1)
        roundmanagervalidateBuySelect.copy(players = playerlistaddToTrash).addToTrash(0) should be (List(Cards.copper), playerlistaddToTrashupdated)
      }
    }
  }
}
