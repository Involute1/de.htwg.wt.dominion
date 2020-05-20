package de.htwg.sa.dominion.model.roundmanagerComponent
import de.htwg.sa.dominion.model.cardcomponent.{Card, CardName, Cards}
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
      "have a checkIfActionPhaseDone method" in {
        roundmanagervalidateBuySelect.checkIfActionPhaseDone should be (false)
        roundmanagervalidateBuySelect.copy(roundStatus = RoundmanagerStatus.START_BUY_PHASE).checkIfActionPhaseDone should be (true)
      }
      "have a createPlayingDecks method" in {
        val copper100: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
        val copperdeck: List[List[Card]] = List(copper100)
        val silver100: List[Card] = List(Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver, Cards.silver)
        val silverdeck: List[List[Card]] = List(silver100)
        val gold100: List[Card] = List(Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold , Cards.gold,Cards.gold, Cards.gold, Cards.gold, Cards.gold, Cards.gold)
        val golddeck: List[List[Card]] = List(gold100)
        val estate12: List[Card] = List(Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate, Cards.estate)
        val estatedeck: List[List[Card]] = List(estate12)
        val duchy12: List[Card] = List(Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy, Cards.duchy)
        val duchydeck: List[List[Card]] = List(duchy12)
        val province12: List[Card] = List(Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province, Cards.province)
        val provincedeck: List[List[Card]] = List(province12)
        val village10: List[Card] = List(Cards.village, Cards.village, Cards.village, Cards.village, Cards.village, Cards.village, Cards.village, Cards.village, Cards.village, Cards.village)
        val villagedeck: List[List[Card]] = List(village10)
        val festival10: List[Card] = List(Cards.festival, Cards.festival, Cards.festival, Cards.festival, Cards.festival, Cards.festival, Cards.festival, Cards.festival, Cards.festival, Cards.festival)
        val festivaldeck: List[List[Card]] = List(festival10)
        val cellar10: List[Card] = List(Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar, Cards.cellar)
        val cellardeck: List[List[Card]] = List(cellar10)
        val mine10: List[Card] = List(Cards.mine, Cards.mine, Cards.mine, Cards.mine, Cards.mine, Cards.mine, Cards.mine, Cards.mine, Cards.mine, Cards.mine)
        val minedeck: List[List[Card]] = List(mine10)
        val smithy10: List[Card] = List(Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy, Cards.smithy)
        val smithydeck: List[List[Card]] = List(smithy10)
        val remodel10: List[Card] = List(Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel, Cards.remodel)
        val remodeldeck: List[List[Card]] = List(remodel10)
        val merchant10: List[Card] = List(Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant, Cards.merchant)
        val merchantdeck: List[List[Card]] = List(merchant10)
        val workshop10: List[Card] = List(Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop, Cards.workshop)
        val workshopdeck: List[List[Card]] = List(workshop10)
        val garden10: List[Card] = List(Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens, Cards.gardens)
        val gardendeck: List[List[Card]] = List(garden10)
        val market10: List[Card] = List(Cards.market, Cards.market, Cards.market, Cards.market, Cards.market, Cards.market, Cards.market, Cards.market, Cards.market, Cards.market)
        val marketdeck: List[List[Card]] = List(market10)
        val roundmanagercreatePLayingDeck: Roundmanager = Roundmanager (playerList, Nil, 2, 1, copperdeck, 0, gameEnd = false, Nil,
          RoundmanagerStatus.PLAY_CARD_PHASE, 1, Nil)
        roundmanager.createPlayingDecks(CardName.COPPER) should be (roundmanagercreatePLayingDeck)
        roundmanager.createPlayingDecks(CardName.SILVER) should be (roundmanagercreatePLayingDeck.copy(decks = silverdeck))
        roundmanager.createPlayingDecks(CardName.GOLD) should be (roundmanagercreatePLayingDeck.copy(decks = golddeck))
        roundmanager.createPlayingDecks(CardName.ESTATE) should be (roundmanagercreatePLayingDeck.copy(decks = estatedeck))
        roundmanager.createPlayingDecks(CardName.DUCHY) should be (roundmanagercreatePLayingDeck.copy(decks = duchydeck))
        roundmanager.createPlayingDecks(CardName.PROVINCE) should be (roundmanagercreatePLayingDeck.copy(decks = provincedeck))
        roundmanager.createPlayingDecks(CardName.VILLAGE) should be (roundmanagercreatePLayingDeck.copy(decks = villagedeck))
        roundmanager.createPlayingDecks(CardName.FESTIVAL) should be (roundmanagercreatePLayingDeck.copy(decks = festivaldeck))
        roundmanager.createPlayingDecks(CardName.CELLAR) should be (roundmanagercreatePLayingDeck.copy(decks = cellardeck))
        roundmanager.createPlayingDecks(CardName.MINE) should be (roundmanagercreatePLayingDeck.copy(decks = minedeck))
        roundmanager.createPlayingDecks(CardName.SMITHY) should be (roundmanagercreatePLayingDeck.copy(decks = smithydeck))
        roundmanager.createPlayingDecks(CardName.REMODEL) should be (roundmanagercreatePLayingDeck.copy(decks = remodeldeck))
        roundmanager.createPlayingDecks(CardName.MERCHANT) should be (roundmanagercreatePLayingDeck.copy(decks = merchantdeck))
        roundmanager.createPlayingDecks(CardName.WORKSHOP) should be (roundmanagercreatePLayingDeck.copy(decks = workshopdeck))
        roundmanager.createPlayingDecks(CardName.GARDENS) should be (roundmanagercreatePLayingDeck.copy(decks = gardendeck))
        roundmanager.createPlayingDecks(CardName.MARKET) should be (roundmanagercreatePLayingDeck.copy(decks = marketdeck))
      }
    }
  }
}
