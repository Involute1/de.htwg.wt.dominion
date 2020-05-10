package de.htwg.sa.dominion.model.roundmanagerComponent

import de.htwg.sa.dominion.model.{RoundmanagerInterface, roundmanagerComponent}
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.cardcomponent.{Card, CardName, Cards, Deck}
import de.htwg.sa.dominion.model.playercomponent.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.RoundmanagerStatus.RoundmanagerStatus

import scala.util.Random

case class Roundmanager(players: List[Player], names: List[String], numberOfPlayers: Int, turn: Int, decks: List[List[Card]],
                        emptyDeckCount: Int, gameEnd: Boolean, score: List[(Int, String)],
                        roundStatus: RoundmanagerStatus) extends RoundmanagerInterface {

  override def turn(input: String): Roundmanager = {
    // 1) draw 5 cards
    // print liste von handkarten + idx, wenn Kingdom karten auf hand
    // -> y: welche karte möchtest du spielen -> status => CardStatus
    // -> n: Keine Action karten auf hand -> status => Start_Buyphase
    // 2) action

    // 3) buy

    // 4) next player
    this = nextPlayer()
    this
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

  private def createPlayer(players: List[Player], name: String, index: Int): List[Player] = {
    val player = Player(name, index + 1, shuffle(Deck.startDeck), Nil, Nil, 1, 1, 0, 0)
    val listPlayers = List.concat(players, List(player))
    listPlayers
  }

  override def createPlayerList(): Roundmanager = {
    for (i <- 0 until this.numberOfPlayers) {
      this.copy(players = createPlayer(this.players, this.names(i), i))
    }
    this
  }

  override def namesEqualPlayer(): Boolean = {
    if (this.numberOfPlayers == this.names.size) {
      true
    }
    else {
      false
    }
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

  override def getCard(players: List[Player], index: Int): Roundmanager = {
    val handList: List[Card] = players(index).handCards
    val deckList: List[Card] = players(index).deck
    if (deckList.size >= 1) {
      val hand1List: List[Card] = List.concat(handList, List(players(index).deck.head))
      val updateddeckList: List[Card] = deckList.drop(0)
      this.copy(players(index) = Player(players(index).name, players(index).value, players(index).deck,
        players(index).stacker, hand1List, players(index).actions, players(index).buys, players(index).money,
        players(index).victoryPoint))
    }
    this
  }

  override def checkForGameEnd(): Boolean = {
    this.gameEnd
  }
}

object RoundmanagerStatus extends Enumeration {
  type RoundmanagerStatus = Value
  val START_ACTION_PHASE, FESTIVAL_ACTION_PHASE, START_BUY_PHASE, NEXT_PLAYER_TURN = Value
}