package de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.CardName.CardName
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl._
import de.htwg.sa.dominion.model.playercomponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.RoundmanagerStatus.RoundmanagerStatus

import scala.util.Random
import scala.xml.Elem

case class Roundmanager(players: List[Player], names: List[String], numberOfPlayers: Int, turn: Int, decks: List[List[Card]],
                        emptyDeckCount: Int, gameEnd: Boolean, score: List[(String, Int)],
                        roundStatus: RoundmanagerStatus, playerTurn: Int, trash: List[Card]) extends IRoundmanager {

  override def toXML: Elem = {
    <RoundManager>
      <players>{for (i <- players.indices) yield playerToXml(players(i))}</players>
      <names>{for (i <- names.indices) yield <name>{names(i)}</name>}</names>
      <numberOfPlayers>{numberOfPlayers}</numberOfPlayers>
      <turn>{turn}</turn>
      <decks>{for (i <- decks.indices) yield <decks>{cardsListToXML(decks(i))}</decks>}</decks>
      <emptyDeckCount>{emptyDeckCount}</emptyDeckCount>
      <gameEnd>{gameEnd}</gameEnd>
      <score>{score}</score>
      <roundStatus>{roundStatus}</roundStatus>
      <playerTurn>{playerTurn}</playerTurn>
      <trash>{trash}</trash>
    </RoundManager>
  }

  def tupleListToXML(l: List[(Int, String)]): List[Elem] = {
    var list = List.empty[Elem]
    l.foreach(kv => list = <entry><points>{kv._1}</points><player>{kv._2}</player></entry> :: list)
    list
  }

  def cardsListToXML(l: List[Card]): List[Elem] = {
    var list = List.empty[Elem]
    l.foreach(c => list = Cards.cardsToXml(c) :: list)
    list
  }

  def playerToXml(player: Player): Elem = {
    <player>
      <name>{player.name}</name>
      <value>{player.value}</value>
      <deck>{for (i <- player.deck.indices) yield Cards.cardsToXml(player.deck(i))}</deck>
      <stacker>{for (i <- player.stacker.indices) yield Cards.cardsToXml(player.stacker(i))}</stacker>
      <handCards>{for (i <- player.handCards.indices) yield Cards.cardsToXml(player.handCards(i))}</handCards>
      <action>{player.actions}</action>
      <buys>{player.buys}</buys>
      <money>{player.money}</money>
    </player>
  }

  /*override def fromXML(node: Node): Roundmanager = {
    //var listBuffer1: ListBuffer[List[Cards]] = ListBuffer()

    val playersNode = (node \ "players").head.child
    val players = (playersNode.map(node => playerFromXML(node))).toList

    val namesNode = (node \ "names").head.child
    val names = (namesNode.map(node => (node \\ "name").text.trim)).toList

    val numberOfPlayers = (node \ "numberOfPlayers").text.toInt

    val turn = (node \ "turn").text.toInt

    val playingDecksNode = (node \ "playingDecks").head.child
    for(i <- (node \ "playingDecks" \ "playingDeck").indices) {
      val decks1: List[Card] = playingDecksNode.toList :+ Cards.ListfromXml(node \ "decks",i)
    }
    val finishedDecks = decks.toList

    val emptyDeckCount = (node \ "emptyDeckCount").text.toInt

    val gameEnd = (node \ "gameEnd").text.toBoolean

    val scoreNode = (node \ "score").head.child
    val score = scoreFromXML(scoreNode)

    val roundStatus =

    val playerTurn = (node \ "playerTurn").text.toInt

    val trash =

    Roundmanager(players, names, numberOfPlayers, turn, finishedDecks, emptyDeckCount, gameEnd, score, roundStatus, playerTurn, trash)
  }

  def scoreFromXML(node: scala.xml.NodeSeq): List[(Int, String)] = {
    var list = List.empty[(Int, String)]
    node.foreach(pp => list = ((node \ "points").text.toInt, (node \ "player").text) :: list)
    list
  }

  def playerFromXML(node: scala.xml.Node): Player = {
    val name= (node \ "name").text.trim
    val value= (node \ "value").text.toInt
    val action = (node \ "action").text.toInt
    val buys = (node \ "buys").text.toInt
    val stringValue = (node \ "stringValue").text.toInt
    val money = (node \ "money").text.toInt
    var listBuffer1: ListBuffer[Cards] = ListBuffer()

    for (i <- 0 until (node \ "deck" \ "card").length) {
      if (!(node \ "deck" \ "card" \ "costValue").text.equals("")) {
        listBuffer1 += Cards.fromXML(node \ "deck" \ "card", i)
      }
    }
    val playerdeck: List[Cards] = listBuffer1.toList
    listBuffer1 = ListBuffer()

    for (f <- 0 until (node \ "stacker" \ "card").length) {
      if (!(node \ "stacker" \ "card" \ "costValue").text.equals("")) {
        listBuffer1 += Cards.fromXML(node \ "stacker" \ "card", f)
      }
    }
    val playerstacker: List[Cards] = listBuffer1.toList
    listBuffer1 = ListBuffer()

    for (i <- 0 until (node \ "hand" \ "card").length) {
      if (!(node \ "hand" \ "card" \ "costValue").text.equals("")) {
        listBuffer1 += Cards.fromXML(node \ "hand" \ "card", i)
      }
    }
    val playerhand: List[Cards] = listBuffer1.toList
    listBuffer1 = ListBuffer()


    Player(name,value,playerdeck,playerstacker,playerhand,action,buys,stringValue,money)
  }*/


  /*override def toJson: JsValue = Json.toJson(this)

  override def fromJson(jsValue: JsValue): Roundmanager = {jsValue.validate[Roundmanager].asOpt.get}*/

  override def actionPhase(input: String): Roundmanager = {
    this.roundStatus match {
      case RoundmanagerStatus.PLAY_CARD_PHASE | RoundmanagerStatus.VILLAGE_ACTION_PHASE | RoundmanagerStatus.FESTIVAL_ACTION_PHASE
           | RoundmanagerStatus.SMITHY_ACTION_PHASE | RoundmanagerStatus.MERCHANT_ACTION_PHASE | RoundmanagerStatus.MARKET_ACTION_PHASE
           | RoundmanagerStatus.CELLAR_END_ACTION | RoundmanagerStatus.MINE_END_ACTION | RoundmanagerStatus.REMODEL_ACTION_PHASE
           | RoundmanagerStatus.WORKSHOP_ACTION_PHASE
      =>
        if (checkIfHandContainsActionCard(this.players)) {
          if (validateHandSelectInputActionCard(input)) {
            val card: Card = this.players(this.playerTurn).handCards(input.toInt)
            card.cardName match {
              case "Village" =>
                // +1 Card, +2 Actions
                val updatedPlayers = villageAction(input.toInt)
                if (checkIfActionLeft(updatedPlayers) && checkIfHandContainsActionCard(updatedPlayers)) {
                  this.copy(players = updatedPlayers, roundStatus = RoundmanagerStatus.VILLAGE_ACTION_PHASE)
                } else {
                  this.copy(players = updateMoneyForRoundmanager(updatedPlayers), roundStatus = RoundmanagerStatus.VILLAGE_BUY_PHASE)
                }
              case "Festival" =>
                // +2 Actions, +1 Card, +2 Money
                val updatedPlayerList = festivalAction(input.toInt)
                if (checkIfActionLeft(updatedPlayerList) && checkIfHandContainsActionCard(updatedPlayerList)) {
                  this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.FESTIVAL_ACTION_PHASE)
                } else {
                  this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.FESTIVAL_BUY_PHASE)
                }
              case "Cellar" =>
                // +1 Action, Discard any number of cards draw as many
                this.copy(players = cellarActionStart(input.toInt), roundStatus = RoundmanagerStatus.CELLAR_ACTION_INPUT_PHASE)
              case "Mine" =>
                // trash money card, gain a treasure card that cost up to 3 more
                val updatedPlayerList: List[Player] = mineActionStart(input.toInt)
                if (checkIfHandContainsTreasure()) {
                  this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.MINE_ACTION_INPUT_PHASE)
                } else {
                  if (checkIfActionLeft(updatedPlayerList) && checkIfHandContainsActionCard(updatedPlayerList)) {
                    this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.MINE_NO_ACTION_PHASE)
                  } else {
                    this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.MINE_NO_ACTION_BUY_PHASE)
                  }
                }
              case "Smithy" =>
                // + 3 cards
                val updatedPlayerList = smithyAction(input.toInt)
                if (checkIfActionLeft(updatedPlayerList) && checkIfHandContainsActionCard(updatedPlayerList)) {
                  this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.SMITHY_ACTION_PHASE)
                } else {
                  this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.SMITHY_BUY_PHASE)
                }
                this
              case "Remodel" =>
                // trash a card, gain a card that costs up to 2 more
                val updatedPlayerList = remodelActionStart(input.toInt)
                if (updatedPlayerList(this.playerTurn).handCards.nonEmpty) {
                  this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.REMODEL_ACTION_INPUT_PHASE)
                } else {
                  this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.REMODEL_NO_ACTION_BUY_PHASE)
                }
              case "Merchant" =>
                // +1 card, +1 action, the first time you play a silver +1 money
                val updatedPlayerList = merchantAction(input.toInt)
                if (checkIfActionLeft(updatedPlayerList) && checkIfHandContainsActionCard(updatedPlayerList)) {
                  this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.MERCHANT_ACTION_PHASE)
                } else {
                  this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.MERCHANT_BUY_PHASE)
                }
              case "Workshop" =>
                // gain a card costing up to 4
                val updatedPlayerList: List[Player] = workshopStartAction(input.toInt)
                this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.WORKSHOP_INPUT_ACTION_PHASE)
              case "Market" =>
                // +1 card, +1 action, +1 buy, +1 money
                val updatedPlayerList = marketAction(input.toInt)
                if (checkIfActionLeft(updatedPlayerList) && checkIfHandContainsActionCard(updatedPlayerList)) {
                  this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.MARKET_ACTION_PHASE)
                } else {
                  this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.MARKET_BUY_PHASE)
                }
            }
          } else this.copy(roundStatus = RoundmanagerStatus.PLAY_CARD_PHASE)
        } else {
          this.copy(players = updateMoneyForRoundmanager(this.players), roundStatus = RoundmanagerStatus.START_BUY_PHASE)
        }
      case RoundmanagerStatus.CELLAR_ACTION_INPUT_PHASE =>
        val inputAsIntList = validateMultiInputToInt(input)
        if (inputAsIntList.isDefined && checkMultiInputCorrespondToHandIdx(inputAsIntList)) {
          val updatedPlayerList = cellarActionEnd(inputAsIntList.get)
          if (checkIfActionLeft(updatedPlayerList) && checkIfHandContainsActionCard(updatedPlayerList)) {
            this.copy(players = updatedPlayerList, roundStatus = RoundmanagerStatus.CELLAR_END_ACTION)
          } else {
            this.copy(players = updateMoneyForRoundmanager(updatedPlayerList), roundStatus = RoundmanagerStatus.CELLAR_BUY_PHASE)
          }
        } else {
          this
        }
      case RoundmanagerStatus.MINE_ACTION_INPUT_PHASE =>
        if (checkIfInputIsMoneyCard(input.toInt)) {
          val updatedTupel: (List[Card], List[Player]) = addToTrash(input.toInt)
          this.copy(trash = updatedTupel._1, roundStatus = RoundmanagerStatus.MINE_END_ACTION, players = updatedTupel._2)
        } else this
      case RoundmanagerStatus.MINE_END_ACTION =>
        if (validateMineInputForPlayingDecks(input)) {
          val updatedTupel: (List[Player], List[List[Card]]) = mineActioneEnd(input.toInt)
          if (checkIfActionLeft(updatedTupel._1) && checkIfHandContainsActionCard(updatedTupel._1)) {
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.PLAY_CARD_PHASE, emptyDeckCount = emptyDeckCount + 1)
            } else this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.PLAY_CARD_PHASE)
          } else {
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.START_BUY_PHASE, emptyDeckCount = emptyDeckCount + 1)
            } else this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.START_BUY_PHASE)
          }
        } else this
      case RoundmanagerStatus.REMODEL_ACTION_INPUT_PHASE =>
        if (validateIsInputAHandCard(input)) {
          val updatedTupel: (List[Card], List[Player]) = addToTrash(input.toInt)
          this.copy(trash = updatedTupel._1, players = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_END_ACTION)
        } else this
      case RoundmanagerStatus.REMODEL_END_ACTION =>
        if (validateRemodelInputForPlayingDecks(input)) {
          if(this.decks(input.toInt).head.cardName == "Province") {
            val updatedTupel: (List[Player], List[List[Card]]) = remodelActionEnd(input.toInt)
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_ACTION_PHASE, emptyDeckCount = 3)
            } else
            if (checkIfActionLeft(updatedTupel._1) && checkIfHandContainsActionCard(updatedTupel._1)) {
              if (this.decks(input.toInt).isEmpty) {
                this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_ACTION_PHASE, emptyDeckCount = emptyDeckCount + 1)
              } else this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_ACTION_PHASE)
            } else {
              if (this.decks(input.toInt).isEmpty) {
                this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_BUY_PHASE, emptyDeckCount = emptyDeckCount + 1)
              } else this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_BUY_PHASE)
            }
          }
          val updatedTupel: (List[Player], List[List[Card]]) = remodelActionEnd(input.toInt)
          if (checkIfActionLeft(updatedTupel._1) && checkIfHandContainsActionCard(updatedTupel._1)) {
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_ACTION_PHASE, emptyDeckCount = emptyDeckCount + 1)
            } else this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_ACTION_PHASE)
          } else {
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_BUY_PHASE, emptyDeckCount = emptyDeckCount + 1)
            } else this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.REMODEL_BUY_PHASE)
          }
        } else this
      case RoundmanagerStatus.WORKSHOP_INPUT_ACTION_PHASE =>
        if (validateWorkshopInputForPlayingDecks(input)) {
          val updatedTupel: (List[Player], List[List[Card]]) = workshopEndAction(input.toInt)
          if (checkIfActionLeft(updatedTupel._1) && checkIfHandContainsActionCard(updatedTupel._1)) {
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.WORKSHOP_ACTION_PHASE, emptyDeckCount = emptyDeckCount + 1)
            } else this.copy(players = updatedTupel._1, decks = updatedTupel._2, roundStatus = RoundmanagerStatus.WORKSHOP_ACTION_PHASE)
          } else {
            if (this.decks(input.toInt).isEmpty) {
              this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.WORKSHOP_BUY_PHASE, emptyDeckCount = emptyDeckCount + 1)
            } else this.copy(players = updateMoneyForRoundmanager(updatedTupel._1), decks = updatedTupel._2, roundStatus = RoundmanagerStatus.WORKSHOP_BUY_PHASE)
          }
        } else this
    }
  }

  override def buyPhase(input: String): Roundmanager = {
    this.roundStatus match {
      case RoundmanagerStatus.START_BUY_PHASE | RoundmanagerStatus.CONTINUE_BUY_PHASE | RoundmanagerStatus.WRONG_INPUT_BUY_PHASE
           | RoundmanagerStatus.VILLAGE_BUY_PHASE | RoundmanagerStatus.FESTIVAL_BUY_PHASE | RoundmanagerStatus.SMITHY_BUY_PHASE
           | RoundmanagerStatus.MARKET_BUY_PHASE | RoundmanagerStatus.MERCHANT_BUY_PHASE | RoundmanagerStatus.CELLAR_BUY_PHASE
           | RoundmanagerStatus.MINE_NO_ACTION_BUY_PHASE | RoundmanagerStatus.REMODEL_NO_ACTION_BUY_PHASE
           | RoundmanagerStatus.REMODEL_BUY_PHASE | RoundmanagerStatus.WORKSHOP_BUY_PHASE =>
        if (checkIfBuyLeft(this.players)) {
          if (validateBuySelectInput(input) && checkIfBuyLeft(this.players)) {
            if (this.decks(input.toInt).head.cardName == "Province") {
              val updatedPlayers: List[Player] = buyCard(input)
              val updatedDecks: List[List[Card]] = dropCardFromDeck(input.toInt)
              if (updatedDecks(input.toInt).isEmpty) {
                val updatedEmptyDeckCount: Int = 3
                this.copy(emptyDeckCount = updatedEmptyDeckCount, players = updatedPlayers, decks = updatedDecks,roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)
              } else if (checkIfBuyLeft(updatedPlayers)) {
                this.copy(players = updatedPlayers, decks = updatedDecks, roundStatus = RoundmanagerStatus.BUY_AGAIN)
              } else this.copy(players = updatedPlayers, decks = updatedDecks,roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)
            }
            val updatedPlayers: List[Player] = buyCard(input)
            val updatedDecks: List[List[Card]] = dropCardFromDeck(input.toInt)
            if (checkIfBuyLeft(updatedPlayers)) {
              this.copy(players = updatedPlayers, decks = updatedDecks, roundStatus = RoundmanagerStatus.BUY_AGAIN)
            } else if (updatedDecks(input.toInt).isEmpty) {
              val updatedEmptyDeckCount: Int = this.emptyDeckCount + 1
              this.copy(emptyDeckCount = updatedEmptyDeckCount, players = updatedPlayers, decks = updatedDecks,roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)
            } else this.copy(players = updatedPlayers, decks = updatedDecks,roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)
          } else this.copy(roundStatus = RoundmanagerStatus.WRONG_INPUT_BUY_PHASE)
        } else this.copy(roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)
      case RoundmanagerStatus.BUY_AGAIN
      =>
        if (checkIfBuyLeft(this.players)) {
          if (validateYesNoInput(input)) {
            this.copy(roundStatus = RoundmanagerStatus.CONTINUE_BUY_PHASE)
          } else this.copy(roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)
        } else this.copy(roundStatus = RoundmanagerStatus.NO_BUYS_LEFT)

      case RoundmanagerStatus.NO_BUYS_LEFT
      => nextPlayer()
    }
  }

  def validateBuySelectInput(input: String): Boolean = {
    val number = input.toIntOption
    if (number.isEmpty || number.get >= this.decks.size || this.decks(input.toInt).isEmpty || this.players(this.playerTurn).money < this.decks(number.get).head.costValue) {
      return false
    }
    true
  }

  def validateHandSelectInputActionCard(input: String): Boolean = {
    val number = input.toIntOption
    if (number.isEmpty || number.get >= this.players(this.playerTurn).handCards.size || number.get < 0) {
      return false
    }
    isSelectedCardActionCard(number.get)
  }

  def validateWorkshopInputForPlayingDecks(input: String): Boolean = {
    val number = input.toIntOption
    if (number.isEmpty || number.get >= this.players(this.playerTurn).handCards.size || number.get < 0) {
      false
    } else {
      val indexList: List[Int] = for ((deck, index) <- this.decks.zipWithIndex if deck.head.costValue <= 4) yield index
      if (indexList.contains(number.get)) {
        true
      } else false
    }
  }

  def validateRemodelInputForPlayingDecks(input: String): Boolean = {
    val number = input.toIntOption
    if (number.isEmpty || number.get >= this.players(this.playerTurn).handCards.size || number.get < 0) {
      false
    } else {
      val maxCostValue = 2 + this.trash.last.costValue
      val indexList: List[Int] = for ((deck, index) <- this.decks.zipWithIndex if deck.head.costValue <= maxCostValue) yield index
      if (indexList.contains(number.get)) {
        true
      } else false
    }
  }

  def validateMineInputForPlayingDecks(input: String): Boolean = {
    val number = input.toIntOption
    if (number.isEmpty || number.get >= this.players(this.playerTurn).handCards.size || number.get < 0) {
      false
    } else {
      val maxCostValue = 3 + this.trash.last.costValue
      val indexList: List[Int] = for ((deck, index) <- this.decks.zipWithIndex if deck.head.costValue <= maxCostValue) yield index
      if (indexList.contains(number.get)) {
        true
      } else false
    }
  }

  def validateIsInputAHandCard(input: String): Boolean = {
    val number = input.toIntOption
    if (number.isEmpty || number.get >= this.players(this.playerTurn).handCards.size || number.get < 0) {
      false
    } else true
  }

  def checkIfInputIsMoneyCard(input: Int): Boolean = {
    if (input >= 0 && input <= this.players(this.playerTurn).handCards.size - 1) {
      this.players(this.playerTurn).handCards(input).cardType == Cardtype.MONEY
    } else false
  }

  def validateMultiInputToInt(input: String): Option[List[Int]] = {
    if (input.contains(",")) {
      val trimmedInput = input.replaceAll(" ", "")
      val splittedString = trimmedInput.split(",")
      try {
        Some(splittedString.flatMap(_.toIntOption).toList)
      } catch {
        case _: Exception => None
      }
    } else {
      val trimmedInput = input.trim
      try {
        Some(List(trimmedInput.toInt))
      } catch {
        case _: Exception => None
      }
    }
  }

  def checkMultiInputCorrespondToHandIdx(inputList: Option[List[Int]]): Boolean = {
    if (inputList.isDefined) {
      val filteredList = inputList.get.filter(x => (x < 0) || (x > this.players(this.playerTurn).handCards.size - 1))
      if (filteredList.nonEmpty) {
        return false
      }
      true
    } else {
      false
    }
  }

  def validateYesNoInput(input: String): Boolean = {
    if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
      return true
    }
    false
  }

  def villageAction(input: Int): List[Player] = {
    val playerWithNewCards: List[Player] = drawXAmountOfCards(1, this.players(this.playerTurn))
    val updatedPlayerList: List[Player] = addToPlayerActions(2 - 1, playerWithNewCards)
    updatedPlayerList.patch(this.playerTurn, Seq(updatedPlayerList(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def festivalAction(input: Int): List[Player] = {
    val playerWithNewCards: List[Player] = drawXAmountOfCards(1, this.players(this.playerTurn))
    val updatedPlayerList: List[Player] = addToPlayerActions(2 - 1, playerWithNewCards)
    val finalPlayerList: List[Player] = addToPlayerMoney(2, updatedPlayerList)
    finalPlayerList.patch(this.playerTurn, Seq(finalPlayerList(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def cellarActionStart(input: Int): List[Player] = {
    val playerWithNewCards: List[Player] = addToPlayerActions(1 - 1, this.players)
    playerWithNewCards.patch(this.playerTurn, Seq(playerWithNewCards(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def cellarActionEnd(input: List[Int]): List[Player] = {
    val playerWithDiscardedHand: Player = this.players(this.playerTurn).discard(input)
    drawXAmountOfCards(input.size, playerWithDiscardedHand)
  }

  def mineActionStart(input: Int): List[Player] = {
    val updatedPlayerList: List[Player] = addToPlayerActions(-1, this.players)
    updatedPlayerList.patch(this.playerTurn, Seq(updatedPlayerList(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def mineActioneEnd(input: Int): (List[Player], List[List[Card]]) = {
    addFromPlayingDecksToHand(input, this.players)
  }

  def smithyAction(input: Int): List[Player] = {
    val updatedPlayer: List[Player] = drawXAmountOfCards(3, this.players(this.playerTurn))
    val updatedPlayerList: List[Player] = updatedPlayer.patch(this.playerTurn, Seq(updatedPlayer(this.playerTurn).removeHandCardAddToStacker(input)), 1)
    addToPlayerActions(-1, updatedPlayerList)
  }

  def remodelActionStart(input: Int): List[Player] = {
    val updatedPlayer: List[Player] = addToPlayerActions(-1, this.players)
    updatedPlayer.patch(this.playerTurn, Seq(updatedPlayer(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def remodelActionEnd(input: Int): (List[Player], List[List[Card]]) = {
    addToStackerFromPlayingDecks(input)
  }

  def workshopStartAction(input: Int): List[Player] = {
    val updatedPlayerList: List[Player] = addToPlayerActions(-1, this.players)
    updatedPlayerList.patch(this.playerTurn, Seq(updatedPlayerList(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def workshopEndAction(input: Int): (List[Player], List[List[Card]]) = {
    addToStackerFromPlayingDecks(input)
  }

  def marketAction(input: Int): List[Player] = {
    val playerWithNewCards: List[Player] = drawXAmountOfCards(1, this.players(this.playerTurn))
    val updatedPlayerList: List[Player] = addToPlayerActions(1 - 1, playerWithNewCards)
    val finalPlayerList: List[Player] = addToPlayerMoney(1, updatedPlayerList)
    val finalFinalPlayerList: List[Player] = addToPlayerBuys(1, finalPlayerList)
    finalFinalPlayerList.patch(this.playerTurn, Seq(finalFinalPlayerList(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def merchantAction(input: Int): List[Player] = {
    val playerWithNewCards: List[Player] = drawXAmountOfCards(1, this.players(this.playerTurn))
    val updatedPlayerList: List[Player] = addToPlayerActions(1 - 1, playerWithNewCards)
    val finalPlayerList: List[Player] = merchantCheckForSilver(updatedPlayerList)
    finalPlayerList.patch(this.playerTurn, Seq(finalPlayerList(this.playerTurn).removeHandCardAddToStacker(input)), 1)
  }

  def merchantCheckForSilver(playerList: List[Player]): List[Player] = {
    val updatedPlayer: Player = playerList(this.playerTurn).checkForFirstSilver()
    playerList.patch(this.playerTurn, Seq(updatedPlayer), 1)
  }

  def dropCardFromDeck(input: Int): List[List[Card]] = {
    val updatedCardDeck: List[Card] = this.decks(input).drop(1)
    val updatedDeck: List[List[Card]] = this.decks.patch(input, Seq(updatedCardDeck), 1)
    updatedDeck
  }

  override def getCurrentPlayerTurn: Int = this.playerTurn

  override def getNameListSize: Int = this.names.size

  def checkIfBuyLeft(playerList: List[Player]): Boolean = {
    playerList(this.playerTurn).buys > 0
  }

  def buyCard(input: String): List[Player] = {
    val updatedPlayerList: List[Player] = this.players.patch(this.playerTurn, Seq(buyPhaseAddCardToStackerFromPlayingDecks(input.toInt)), 1)
    updatedPlayerList.patch(this.playerTurn, Seq(updatedPlayerList(this.playerTurn).updateMoney(updatedPlayerList(this.playerTurn).money - this.decks(input.toInt).head.costValue)), 1)
  }

  def buyPhaseAddCardToStackerFromPlayingDecks(index: Int): Player = {
    val updatedStacker = List.concat(this.players(this.playerTurn).stacker, List(this.decks(index).head))
    this.players(this.playerTurn).copy(stacker = updatedStacker, buys = this.players(this.playerTurn).buys - 1)
  }

  def updateMoneyForRoundmanager(playerList: List[Player]): List[Player] = {
    playerList.patch(this.playerTurn, Seq(playerList(this.playerTurn).calculatePlayerMoneyForBuy), 1)
  }

  def addToPlayerMoney(moneyToAdd: Int, playerList: List[Player]): List[Player] = {
    val updatedMoney: Int = moneyToAdd + playerList(this.playerTurn).money
    val updatedPlayer: Player = playerList(this.playerTurn).updateMoney(updatedMoney)
    playerList.patch(this.playerTurn, Seq(updatedPlayer), 1)
  }

  def addToPlayerActions(actionsToAdd: Int, playerList: List[Player]): List[Player] = {
    val updateActions = actionsToAdd + playerList(this.playerTurn).actions
    val updatedPlayer: Player = playerList(this.playerTurn).updateActions(updateActions)
    playerList.patch(this.playerTurn, Seq(updatedPlayer), 1)
  }

  def addToStackerFromPlayingDecks(input: Int): (List[Player], List[List[Card]]) = {
    val updatedStacker: List[Card] = List.concat(this.players(this.playerTurn).stacker, List(this.decks(input).head))
    val updatedDecks: List[List[Card]] = this.decks.patch(input, Seq(this.decks(input).drop(1)), 1)
    val updatedPlayerList: List[Player] = this.players.patch(this.playerTurn, Seq(this.players(this.playerTurn).copy(stacker = updatedStacker)), 1)
    (updatedPlayerList, updatedDecks)
  }

  def addToPlayerBuys(buysToAdd: Int, playerList: List[Player]): List[Player] = {
    val updatedBuys = buysToAdd + playerList(this.playerTurn).buys
    val updatedPlayer: Player = playerList(this.playerTurn).updateBuys(updatedBuys)
    playerList.patch(this.playerTurn, Seq(updatedPlayer), 1)
  }

  def addToTrash(input: Int): (List[Card], List[Player]) = {
    val updatedTrash: List[Card] = List.concat(this.trash, List(this.players(this.playerTurn).handCards(input)))
    val updatedPlayerList: List[Player] = this.players.patch(this.playerTurn, Seq(this.players(this.playerTurn).trashHandCard(input)), 1)
    (updatedTrash, updatedPlayerList)
  }

  def addFromPlayingDecksToHand(input: Int, playerList: List[Player]): (List[Player], List[List[Card]]) = {
    val updatedHand = List.concat(playerList(this.playerTurn).handCards, List(this.decks(input).head))
    val updatedPlayer = playerList(this.playerTurn).copy(handCards = updatedHand)
    (playerList.patch(this.playerTurn, Seq(updatedPlayer), 1), this.decks.patch(input, Seq(this.decks(input).drop(1)), 1))
  }

  def drawXAmountOfCards(cardDrawAmount: Int, player: Player): List[Player] = {
    val updatedPlayer: Player = player.updateHand(cardDrawAmount, player)
    this.players.patch(this.playerTurn, Seq(updatedPlayer), 1)
  }

  def checkIfHandContainsTreasure(): Boolean = {
    this.players(this.playerTurn).checkForTreasure()
  }

  def isSelectedCardActionCard(input: Int): Boolean = {
    this.players(this.playerTurn).handCards(input).cardType == Cardtype.KINGDOM
  }

  override def checkIfActionPhaseDone: Boolean = {
    if (this.roundStatus == RoundmanagerStatus.START_BUY_PHASE || this.roundStatus == RoundmanagerStatus.VILLAGE_BUY_PHASE
      || this.roundStatus == RoundmanagerStatus.FESTIVAL_BUY_PHASE || this.roundStatus == RoundmanagerStatus.SMITHY_BUY_PHASE
      || this.roundStatus == RoundmanagerStatus.MARKET_BUY_PHASE || this.roundStatus == RoundmanagerStatus.MERCHANT_BUY_PHASE
      || this.roundStatus == RoundmanagerStatus.CELLAR_BUY_PHASE || this.roundStatus == RoundmanagerStatus.MINE_NO_ACTION_BUY_PHASE
      || this.roundStatus == RoundmanagerStatus.REMODEL_NO_ACTION_BUY_PHASE || this.roundStatus == RoundmanagerStatus.REMODEL_BUY_PHASE
      || this.roundStatus == RoundmanagerStatus.WORKSHOP_BUY_PHASE) {
      return true
    }
    false
  }

  override def checkForNextPlayer: Boolean = {
    this.roundStatus == RoundmanagerStatus.PLAY_CARD_PHASE
  }

  private def checkIfActionLeft(playerList: List[Player]): Boolean = {
    playerList(this.playerTurn).actions > 0
  }

  private def checkIfHandContainsActionCard(playerList: List[Player]): Boolean = {
    for (i <- playerList(this.playerTurn).handCards.indices) {
      if (playerList(this.playerTurn).handCards(i).cardType == Cardtype.KINGDOM) {
        return true
      }
    }
    false
  }

  private def nextPlayer(): Roundmanager = {
    if (this.emptyDeckCount == 3) {
      this.copy(gameEnd = true, score = calculateScore())
    } else {
      val removeHandPlayer: Player = this.players(this.playerTurn).removeCompleteHand(this.players(this.playerTurn), this.players(this.playerTurn).handCards.size - 1)
      val handCardPlayer: Player = removeHandPlayer.updateHand(5, removeHandPlayer)
      val updatedPlayer: Player = Player(handCardPlayer.name, handCardPlayer.value, handCardPlayer.deck, handCardPlayer.stacker, handCardPlayer.handCards, 1, 1, 0)
      val updatedPlayers: List[Player] = this.players.patch(this.playerTurn, Seq(updatedPlayer), 1)
      this.copy(turn = turn + 1, playerTurn = (turn + 1) % numberOfPlayers, roundStatus = RoundmanagerStatus.PLAY_CARD_PHASE, players = updatedPlayers)
    }
  }

  private def calculateScore(): List[(String, Int)] = {
    val updatedPlayerList: List[Player] = for (player <- this.players) yield player.moveAllCardsToDeckForScore()
    val scoreList: List[Int] = for (player <- updatedPlayerList) yield player.calculateScore
    val mappedScoreList: List[(String, Int)] = for ((score, index) <- scoreList.zipWithIndex) yield ("Player " + (index + 1).toString, score)
    List(mappedScoreList.sortBy(_._2):_*)
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

  override def getCurrentPlayerActions: Int = {
    this.players(this.playerTurn).actions
  }

  override def getCurrentPlayerBuys: Int = {
    this.players(this.playerTurn).buys
  }

  override def getCurrentPlayerDeck: List[Card] = {
    this.players(this.playerTurn).deck
  }

  override def getCurrentPlayerHand: List[Card] = {
    this.players(this.playerTurn).handCards
  }

  override def getCurrentPlayerMoney: Int = {
    this.players(this.playerTurn).money
  }

  override def getCurrentPlayerName: String = {
    this.players(this.playerTurn).name
  }

  override def getPlayingDecks: List[List[Card]] = this.decks

  override def getScore: List[(String, Int)] = this.score

  override def getTurn: Int = this.turn

  override def initializePlayersList(idx: Int): Roundmanager = {
    val player = Player(this.names(idx), idx + 1, shuffle(Deck.startDeck), Nil, Nil, 1, 1, 0)
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
        stackemptyList, hand1List, players(index).actions, players(index).buys, players(index).money)
      val updatedPlayers: List[Player] = players.updated(index, updatedPlayer)
      this.copy(players = updatedPlayers)
    } else {
      val hand1List: List[Card] = List.concat(handList, List(players(index).deck.head))
      val minusDeckList: List[Card] = deckList.drop(1)
      val updatedPlayer: Player = Player(players(index).name, players(index).value, minusDeckList,
        players(index).stacker, hand1List, players(index).actions, players(index).buys, players(index).money)
      val updatedPlayers: List[Player] = players.updated(index, updatedPlayer)
      this.copy(players = updatedPlayers)
    }
  }

  override def checkForGameEnd(): Boolean = {
    this.gameEnd
  }

  override def constructRoundermanagerStateString: String = {
    val handDefaultString = "----HAND CARDS----\n"
    val actionDefaultString = handDefaultString + this.players(this.playerTurn).constructPlayerHandString() + "\n" + checkActionCard()
    val villageActionString = "You drew 1 Card and gained 2 Actions\n"
    val festivalActionString = "You drew 1 Card, gained 2 Actions and 2 Money\n"
    val smithyActionString = "You drew 3 Cards\n"
    val marketActionString = "You drew 1 Card, gained 1 Action, 1 Buy and 1 Money\n"
    val merchantActionString = "You drew 1 Card" + checkSilverOnHandMerchantAction() + "\n"
    val cellarFirstActionString = "You gained 1 Action\n"
    val cellarEndActionString = "You discarded x Cards, and drew as many\n"
    val remodelActionString = "You gained a card\n"
    val workshioActionString = "You gained a card that costs up to 4\n"
    val buyPhaseString = "You can spend (" + this.players(this.playerTurn).money + ") Gold in (" + this.players(this.playerTurn).buys + ") Buys \n----AVAILABLE CARDS----\n" + constructBuyableString() + "\nWhich Card do you wanna buy?\n"
    this.roundStatus match {
      case RoundmanagerStatus.PLAY_CARD_PHASE
      => handDefaultString + this.players(this.playerTurn).constructPlayerHandString() + "\n----ACTION PHASE----\n" + checkActionCard()
      case RoundmanagerStatus.VILLAGE_ACTION_PHASE => villageActionString + actionDefaultString
      case RoundmanagerStatus.VILLAGE_BUY_PHASE => villageActionString + buyPhaseString
      case RoundmanagerStatus.FESTIVAL_ACTION_PHASE => festivalActionString + actionDefaultString
      case RoundmanagerStatus.FESTIVAL_BUY_PHASE => festivalActionString + buyPhaseString
      case RoundmanagerStatus.SMITHY_ACTION_PHASE => smithyActionString + actionDefaultString
      case RoundmanagerStatus.SMITHY_BUY_PHASE => smithyActionString + buyPhaseString
      case RoundmanagerStatus.MARKET_ACTION_PHASE => marketActionString + actionDefaultString
      case RoundmanagerStatus.MARKET_BUY_PHASE => marketActionString + buyPhaseString
      case RoundmanagerStatus.MERCHANT_ACTION_PHASE => merchantActionString + actionDefaultString
      case RoundmanagerStatus.MERCHANT_BUY_PHASE => merchantActionString + buyPhaseString
      case RoundmanagerStatus.CELLAR_ACTION_INPUT_PHASE => cellarFirstActionString + handDefaultString +
        this.players(this.playerTurn).constructPlayerHandString() + "\nPlease enter the Cards you want to discard separated with a ','"
      case RoundmanagerStatus.CELLAR_END_ACTION => cellarEndActionString + actionDefaultString
      case RoundmanagerStatus.CELLAR_BUY_PHASE => cellarEndActionString + buyPhaseString
      case RoundmanagerStatus.MINE_ACTION_INPUT_PHASE => this.players(this.playerTurn).constructCellarTrashString() + "\nSelect which Treasure to trash:\n"
      case RoundmanagerStatus.MINE_NO_ACTION_PHASE => "You dont have any Treasure on hand\n" + actionDefaultString
      case RoundmanagerStatus.MINE_NO_ACTION_BUY_PHASE => "You dont have any Treasure on hand\n" + buyPhaseString
      case RoundmanagerStatus.MINE_END_ACTION => constructCellarTreasureString() + "\nChoose one of the treasures:\n"
      case RoundmanagerStatus.REMODEL_ACTION_INPUT_PHASE => handDefaultString + this.players(this.playerTurn).constructPlayerHandString() + "\nSelect which Card to trash:"
      case RoundmanagerStatus.REMODEL_NO_ACTION_BUY_PHASE => "You dont have any Cards to trash\n" + buyPhaseString
      case RoundmanagerStatus.REMODEL_END_ACTION => constructRemodelString() + "\nSelect which Card to add to your stacker:\n"
      case RoundmanagerStatus.REMODEL_ACTION_PHASE => remodelActionString + actionDefaultString
      case RoundmanagerStatus.REMODEL_BUY_PHASE => remodelActionString + buyPhaseString
      case RoundmanagerStatus.WORKSHOP_INPUT_ACTION_PHASE => constructWorkshopString() + "\nSelect which Card to add to your Stacker:"
      case RoundmanagerStatus.WORKSHOP_ACTION_PHASE => workshioActionString + actionDefaultString
      case RoundmanagerStatus.WORKSHOP_BUY_PHASE => workshioActionString + buyPhaseString
      case RoundmanagerStatus.START_BUY_PHASE => buyPhaseString
      case RoundmanagerStatus.WRONG_INPUT_BUY_PHASE => "Wrong input try again\n"
      case RoundmanagerStatus.NO_BUYS_LEFT => "No more buys left press a key to continue"
      case RoundmanagerStatus.CONTINUE_BUY_PHASE => "----AVAILABLE CARDS----\n" + constructBuyableString() + "\nWhich Card do you wanna buy?\n"
      case RoundmanagerStatus.BUY_AGAIN => "Do you want do buy another card?"
      case _ => this.roundStatus.toString
    }
  }

  private def checkActionCard(): String = {
    for (i <- this.players(this.playerTurn).handCards.indices) {
      if (this.players(this.playerTurn).handCards(i).cardType == Cardtype.KINGDOM) {
        return "Which Card do you want to play? Enter one of the numbers listed in the Brackets to select it"
      }
    }
    "You dont have any Card to play, press any key to end your action phase"
  }

  private def checkSilverOnHandMerchantAction(): String = {
    if (this.players(this.playerTurn).handCards.contains(Cards.silver)) {
      return ", gained 1 Action and 1 Money"
    }
    "and gained 1 Action"
  }

  override def constructScoreString: String = {
    val stringList: List[String] = for ((player, score) <- this.score) yield player + ": " + score
    stringList.mkString("\n")
  }

  private def constructBuyableString(): String = {
    val playerMoney: Int = this.players(this.playerTurn).money
    val deckList = for ((deck, index) <- this.decks.zipWithIndex if deck.nonEmpty && deck.head.costValue <= playerMoney)
      yield deck.head.cardName + "(" + index + ")"
    deckList.mkString("\n")
  }

  private def constructCellarTreasureString(): String = {
    val maxCostValue = this.trash.last.costValue + 3
    val deckList = for ((deck, index) <- this.decks.zipWithIndex if deck.nonEmpty && deck.head.cardType == Cardtype.MONEY && deck.head.costValue <= maxCostValue)
      yield deck.head.cardName + "(" + index + ")"
    deckList.mkString("\n")
  }

  private def constructWorkshopString(): String = {
    val deckList = for ((deck, index) <- this.decks.zipWithIndex if deck.nonEmpty && deck.head.costValue <= 4) yield deck.head.cardName + "(" + index + ")"
    deckList.mkString("\n")
  }

  private def constructRemodelString(): String = {
    val maxCostValue = this.trash.last.costValue + 2
    val deckList = for ((deck, index) <- this.decks.zipWithIndex if deck.nonEmpty && deck.head.costValue <= maxCostValue) yield deck.head.cardName + "(" + index + ")"
    deckList.mkString("\n")
  }

  override def constructControllerAskNameString: String = {
    val askNameString = "Player " + (this.names.size + 1) + " please enter your name:"
    askNameString
  }
}

object RoundmanagerStatus extends Enumeration {
  type RoundmanagerStatus = Value
  val PLAY_CARD_PHASE, VILLAGE_ACTION_PHASE, VILLAGE_BUY_PHASE, FESTIVAL_ACTION_PHASE, FESTIVAL_BUY_PHASE,
  SMITHY_ACTION_PHASE, SMITHY_BUY_PHASE, MARKET_ACTION_PHASE, MARKET_BUY_PHASE, MERCHANT_ACTION_PHASE, MERCHANT_BUY_PHASE,
  CELLAR_ACTION_INPUT_PHASE, CELLAR_END_ACTION, CELLAR_BUY_PHASE, MINE_ACTION_INPUT_PHASE, MINE_NO_ACTION_PHASE,
  MINE_NO_ACTION_BUY_PHASE, MINE_END_ACTION, REMODEL_NO_ACTION_BUY_PHASE, REMODEL_ACTION_INPUT_PHASE, REMODEL_END_ACTION,
  REMODEL_BUY_PHASE, REMODEL_ACTION_PHASE, WORKSHOP_INPUT_ACTION_PHASE, WORKSHOP_BUY_PHASE, WORKSHOP_ACTION_PHASE,
  START_BUY_PHASE, WRONG_INPUT_BUY_PHASE, NO_BUYS_LEFT, INIT_BUY_PHASE, CONTINUE_BUY_PHASE, BUY_AGAIN, NEXT_PLAYER_TURN = Value
}