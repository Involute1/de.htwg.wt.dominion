package de.htwg.sa.dominion.model.roundmanagerComponent

import de.htwg.sa.dominion.model.{RoundmanagerInterface, roundmanagerComponent}
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.cardcomponent.{Card, CardName, Cards, Cardtype, Deck}
import de.htwg.sa.dominion.model.playercomponent.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.RoundmanagerStatus.RoundmanagerStatus

import scala.util.Random

case class Roundmanager(players: List[Player], names: List[String], numberOfPlayers: Int, turn: Int, decks: List[List[Card]],
                        emptyDeckCount: Int, gameEnd: Boolean, score: List[(Int, String)],
                        roundStatus: RoundmanagerStatus, playerTurn: Int) extends RoundmanagerInterface {

  override def actionPhase(input: String): Roundmanager = {
    // 1) draw 5 cards
    if (this.players(this.playerTurn).handCards.isEmpty) {

    }
    this.roundStatus match {
      case RoundmanagerStatus.PLAY_CARD_PHASE | RoundmanagerStatus.VILLAGE_ACTION_PHASE
        =>
        if (checkIfHandContainsActionCard(this.players)) {
          if (validateHandSelectInput(input)) {
            val card = this.players(this.playerTurn).handCards(input.toInt)
            card.cardName match {
              case "Village" =>
                // +1 Card, +2 Actions
                val updatedPlayers = festivalAction(input.toInt)
                if (checkIfActionLeft(updatedPlayers) && checkIfHandContainsActionCard(updatedPlayers)) {
                  this.copy(players = updatedPlayers, roundStatus = RoundmanagerStatus.VILLAGE_ACTION_PHASE)
                } else {
                  this.copy(players = updatedPlayers, roundStatus = RoundmanagerStatus.VILLAGE_BUY_PHASE)
                }
              case "Festival" =>
                // +2 Actions, +1 Card, +2 Money
                this
              case "Cellar" =>
                // +1 Action, Discard any number of cards draw as many
                this
              case "Mine" =>
                // trash money card, gain a card that cost up to 3 more
                this
              case "Smithy" =>
                // + 3 cards
                this
              case "Remodel" =>
                // trash a card, gain a card that costs up to 2 more
                this
              case "Merchant" =>
                // +1 card, +1 action, the first time you play a silver +1 money
                this
              case "Workshop" =>
                // gain a card costing up to 4
                this
              case "Market" =>
                // +1 card, +1 action, +1 buy, +1 money
                this
            }
            //this.copy(roundStatus = RoundmanagerStatus.PLAY_ACTION_CARD)
          } else this.copy(roundStatus = RoundmanagerStatus.PLAY_CARD_PHASE)
        } else {
          this.copy(roundStatus = RoundmanagerStatus.START_BUY_PHASE)
        }
      /*case RoundmanagerStatus.START_ACTION_PHASE
        =>
        if (checkIfHandContainsActionCard()) {
          if (validateHandSelectInput(input)) {
            this.copy(roundStatus = RoundmanagerStatus.PLAY_ACTION_CARD)
          } else this
        } else {
          this.copy(roundStatus = RoundmanagerStatus.START_BUY_PHASE)
        }*/
    }

    // 4) next player
    //this = nextPlayer()
  }

  override def buyPhase(input: String): Roundmanager = {
    this.roundStatus match {
      case RoundmanagerStatus.START_BUY_PHASE => this
    }
  }

  private def festivalAction(input: Int): List[Player] = {
    val playerWithNewCards: List[Player] = drawXAmountOfCards(1)
    val updatedPlayerList: List[Player] = addToPlayerActions(2, playerWithNewCards)
    updatedPlayerList.patch(this.playerTurn, Seq(updatedPlayerList(this.playerTurn).removeHandCard(input)), 1)
  }

  private def addToPlayerActions(actionsToAdd: Int, playerList: List[Player]): List[Player] = {
    val updateActions = actionsToAdd + playerList(this.playerTurn).actions - 1
    val updatedPlayer: Player = playerList(this.playerTurn).updateActions(updateActions)
    val updatedPlayers = playerList.patch(this.playerTurn, Seq(updatedPlayer), 1)
    updatedPlayers
  }

  private def drawXAmountOfCards(cardDrawAmount: Int): List[Player] = {
    val updatedPlayer: Player = this.players(this.playerTurn).updateHand(cardDrawAmount, this.players(this.playerTurn))
    this.players.patch(this.playerTurn, Seq(updatedPlayer), 1)
  }

  private def validateHandSelectInput(input: String): Boolean = {
    val number = input.toInt
    if (number >= this.players(this.playerTurn).handCards.size || number < 0) {
      return isSelectedCardActionCard(number)
    }
    isSelectedCardActionCard(number)
  }

  private def isSelectedCardActionCard(input: Int): Boolean = {
    this.players(this.playerTurn).handCards(input).cardType == Cardtype.KINGDOM
  }

  private def validateYesNoInput(input: String): Boolean = {
    if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
      return true
    }
    false
  }

  override def checkIfActionPhaseDone: Boolean = {
    if (this.roundStatus == RoundmanagerStatus.START_BUY_PHASE) {
      return true
    }
    false
  }

  private def checkIfActionLeft(playerList: List[Player]): Boolean = {
    playerList(this.playerTurn).actions > 0
  }

  private def checkActionCard(): String = {
    for (i <- this.players(this.playerTurn).handCards.indices) {
      if (this.players(this.playerTurn).handCards(i).cardType == Cardtype.KINGDOM) {
        return "Welche Aktionskarte möchtest du spielen? Enter one of the numbers listed in the Brackets"
      }
    }
    "Du hast keine Aktionskarte auf der Hand zum spielen, press any key to end your action phase"
  }

  private def checkIfHandContainsActionCard(playerList: List[Player]): Boolean = {
    for (i <- playerList(this.playerTurn).handCards.indices) {
      if (playerList(this.playerTurn).handCards(i).cardType == Cardtype.KINGDOM) {
        return true
      }
    }
    false
  }

  /*override def listAvaibleCardsToBuy(): String = {
    //val avaibleStringList: List[String] =
    //val playerStackerString: String = avaibleStringList.mkString("\n")
    //playerStackerString.toString
    ""
  }*/

  override def updateMoney(index: Int, money: Int): Roundmanager = {
    val startMoney: Int = players(index).money
    val updatedMoney: Int = startMoney + money
    val updatedPlayer: Player = Player(players(index).name, players(index).value, players(index).deck,
      players(index).stacker, players(index).handCards, players(index).actions, players(index).buys, updatedMoney,
      players(index).victoryPoint)
    val updatedPlayers: List[Player] = players.updated(index, updatedPlayer)
    this.copy(players = updatedPlayers)
  }


  /*private def getMoney(): Roundmanager = {
    for(i <- this.players(this.playerTurn).handCards.indices) {
      if (this.players(this.playerTurn).handCards(i).cardType == Cardtype.MONEY) {
        this = updateMoney(playerTurn, this.players(this.playerTurn).handCards(i).moneyValue)
      }
    }
    this
  }*/

  override def constructRoundermanagerStateString: String = {
    val handDefaultString = "----HAND CARDS----\n"
    val villageActionString = "You drew 1 Card and gained 2 Actions\n"
    this.roundStatus match {
      case RoundmanagerStatus.PLAY_CARD_PHASE
      => handDefaultString + this.players(this.playerTurn).constructPlayerHandString() + "\n----ACTION PHASE----\n" + checkActionCard()
      case RoundmanagerStatus.VILLAGE_ACTION_PHASE => villageActionString + handDefaultString + this.players(this.playerTurn).constructPlayerHandString() + "\n" + checkActionCard()
      case RoundmanagerStatus.VILLAGE_BUY_PHASE => villageActionString // TODO ADD BUY PHASE STRING
      case _ => this.roundStatus.toString
    }
  }

  private def nextPlayer(): Roundmanager = {
    if (this.emptyDeckCount == 3) {
      this.copy(gameEnd = true)
    } else {
      this.copy(turn = turn + 1, roundStatus = RoundmanagerStatus.NEXT_PLAYER_TURN)
    }
  }

  override def createPlayingDecks(cardName: CardName): Roundmanager = {
    cardName match {
      case moneyCard if moneyCard == CardName.COPPER || moneyCard == CardName.SILVER || moneyCard == CardName.GOLD
      => constructPlayingCardDeck(cardName, 100)
      case victoryPointCard if victoryPointCard == CardName.ESTATE || victoryPointCard == CardName.DUCHY || victoryPointCard == CardName.PROVINCE
      => constructPlayingCardDeck(cardName, 12)
      case _ => constructPlayingCardDeck(cardName, 10)
    }
  }

  private def constructPlayingCardDeck(card: CardName, amount: Int): Roundmanager = {
    // TODO Find better way for this pattern matching
    val deck: List[Card] = List.fill(amount)(card.toString match {
      case "Copper" => Cards.copper
      case "Silver" => Cards.silver
      case "Gold" => Cards.gold
      case "Estate" => Cards.estate
      case "Duchy" => Cards.duchy
      case "Province" => Cards.province
      case "Village" => Cards.village
      case "Festival" => Cards.festival
      case "Cellar" => Cards.cellar
      case "Mine" => Cards.mine
      case "Smithy" => Cards.smithy
      case "Remodel" => Cards.remodel
      case "Merchant" => Cards.merchant
      case "Workshop" => Cards.workshop
      case "Gardens" => Cards.gardens
      case "Market" => Cards.market
    })

    val decksNew: List[List[Card]] = List(deck)
    if (this.decks.nonEmpty) {
      val decksNew2: List[List[Card]] = List.concat(this.decks, decksNew)
      this.copy(decks = decksNew2)
    } else {
      this.copy(decks = decksNew)
    }
  }

  override def getNumberOfPlayers: Int = {
    this.numberOfPlayers
  }

  override def initializePlayersList(idx: Int): Roundmanager = {
    val player = Player(this.names(idx), idx + 1, shuffle(Deck.startDeck), Nil, Nil, 1, 1, 0, 0)
    if (this.players.isEmpty) {
      this.copy(players = List(player))
    } else {
      this.copy(players = List.concat(this.players, List(player)))
    }
  }

  override def namesEqualPlayer(): Boolean = {
    this.numberOfPlayers == this.names.size
  }

  override def updateNumberOfPlayer(numberOfPlayers: Int): Roundmanager = {
    this.copy(numberOfPlayers = numberOfPlayers)
  }

  override def updateListNames(name: String): Roundmanager = {
    val listNames: List[String] = List.concat(names, List(name))
    this.copy(names = listNames)
  }

  override def constructControllerAskNameString: String = {
    val askNameString = "Player " + (this.names.size + 1) + " please enter your name:"
    askNameString
  }

  override def shuffle(deck: List[Card]): List[Card] = {
    val random = new Random
    val shuffledList: List[Card] = random.shuffle(deck)
    shuffledList
  }

  override def drawCard(index: Int): Roundmanager = {
    val handList: List[Card] = this.players(index).handCards
    val deckList: List[Card] = this.players(index).deck
    val stackList: List[Card] = this.players(index).stacker
    val stackemptyList: List[Card] = Nil
    if (deckList.isEmpty) {
      val deck1List: List[Card] = shuffle(stackList)
      val hand1List: List[Card] = List.concat(handList, List(deck1List.head))
      val minusDeck1List: List[Card] = deck1List.drop(1)
      val updatedPlayer: Player = Player(players(index).name, players(index).value, minusDeck1List,
        stackemptyList, hand1List, players(index).actions, players(index).buys, players(index).money,
        players(index).victoryPoint)
      val updatedPlayers: List[Player] = players.updated(index, updatedPlayer)
      this.copy(players = updatedPlayers)
    } else {
      val hand1List: List[Card] = List.concat(handList, List(players(index).deck.head))
      val minusDeckList: List[Card] = deckList.drop(1)
      val updatedPlayer: Player = Player(players(index).name, players(index).value, minusDeckList,
        players(index).stacker, hand1List, players(index).actions, players(index).buys, players(index).money,
        players(index).victoryPoint)
      val updatedPlayers: List[Player] = players.updated(index, updatedPlayer)
      this.copy(players = updatedPlayers)
    }
  }

  override def checkForGameEnd(): Boolean = {
    this.gameEnd
  }
}

object RoundmanagerStatus extends Enumeration {
  type RoundmanagerStatus = Value
  val PLAY_CARD_PHASE, VILLAGE_ACTION_PHASE, VILLAGE_BUY_PHASE, FESTIVAL_ACTION_PHASE, START_BUY_PHASE, NEXT_PLAYER_TURN = Value
}