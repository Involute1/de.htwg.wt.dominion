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
    this.roundStatus match {
      case RoundmanagerStatus.START_ACTION_PHASE => this
    }
    // print liste von handkarten + idx, wenn Kingdom karten auf hand
    // -> y: welche karte möchtest du spielen -> status => CardStatus
    // -> n: Keine Action karten auf hand -> status => Start_Buyphase
    // 2) action

    // 3) buy

    // 4) next player
    //this = nextPlayer()
  }

  override def buyPhase(input: String): Roundmanager = {
    this.roundStatus match {
      case RoundmanagerStatus.START_BUY_PHASE => this
    }
  }


  private def checkActionCard(): String = {
    for(i <- this.players(this.playerTurn).handCards.indices) {
      if (this.players(this.playerTurn).handCards(i).cardType == Cardtype.KINGDOM) {
        "Welche Aktionskarte möchtest du spielen?"
      }
    }
    "Du hast keine Aktionskarte auf der Hand zum spielen"
  }



  override def updateMoney(index: Int, money: Int): Roundmanager = {
    val startMoney: Int = players(index).money
    val updatedMoney: Int = startMoney + money
    val updatedPlayer: Player = Player(players(index).name, players(index).value, players(index).deck,
      players(index).stacker, players(index).handCards, players(index).actions, players(index).buys, updatedMoney,
      players(index).victoryPoint)
    val updatedPlayers: List[Player] = players.updated(index, updatedPlayer)
    this.copy(players = updatedPlayers)
  }



  override def constructRoundermanagerStateString: String = {
    this.roundStatus match {
      case RoundmanagerStatus.INIT_PHASE
      => "----ACTION PHASE----\n" + this.players(this.playerTurn).constructPlayerHandString() + "\n" + checkActionCard()
      //case RoundmanagerStatus.START_ACTION_PHASE => "----ACTION PHASE----\n" + this.players(this.playerTurn).constructPlayerHandString() + checkActionCard()
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
  val INIT_PHASE, START_ACTION_PHASE, FESTIVAL_ACTION_PHASE, START_BUY_PHASE, NEXT_PLAYER_TURN = Value
}