package de.htwg.sa.dominion.model.playerComponent.playerBaseImpl

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Card, Cards, Cardtype}
import de.htwg.sa.dominion.model.playerComponent.IPlayer
import play.api.libs.json.{JsValue, Json}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.util.Random
import scala.xml.{Elem, NodeSeq}

case class Player(name: String, value: Int, deck: List[Card], stacker: List[Card], handCards: List[Card],
                  actions: Int, buys: Int, money: Int) extends IPlayer with PlayJsonSupport  {

  override def constructPlayerNameString(player: Player): String = {
    player.name
  }

  override def constructPlayerDeckString(player: Player): String = {
    val deckStringList: List[String] = for ((card, idx) <- player.deck.zipWithIndex) yield card.cardName + " (" + idx + ")"
    deckStringList.mkString("\n")
  }

  override def constructPlayerStackerString(player: Player): String = {
    val stackerStringList: List[String] = for ((card, idx) <- player.stacker.zipWithIndex) yield card.cardName + " (" + idx + ")"
    stackerStringList.mkString("\n")
  }

  override def constructPlayerHandString(player: Player): String = {
    val handStringList: List[String] = for ((card, idx) <- player.handCards.zipWithIndex) yield card.cardName + " (" + idx + ")"
    handStringList.mkString("\n")
  }

  override def updateActions(updatedActionValue: Int, player: Player): Player = {
    player.copy(actions = updatedActionValue)
  }

  override def updateMoney(updateMoneyValue: Int, player: Player): Player = {
    player.copy(money = updateMoneyValue)
  }

  override def updateBuys(updatedBuyValue: Int, player: Player): Player = {
    player.copy(buys = updatedBuyValue)
  }

  override def checkForFirstSilver(player: Player): Player = {
    val hasSilver: Boolean = player.handCards.contains(Cards.silver)
    if (hasSilver) {
      val updatedMoney = player.money + 1
      player.copy(money = updatedMoney)
    } else player
  }

  override def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player = {
    if (cardsToDraw == 0) {
      return playerToUpdate
    }
    if (playerToUpdate.deck.isEmpty) {
      val updatedDeck = shuffle(playerToUpdate.stacker)
      val updatedStacker = List()
      val updatedHand = List.concat(playerToUpdate.handCards, List(updatedDeck.head))
      val finalDeck = updatedDeck.drop(1)
      val updatedPlayer: Player = playerToUpdate.copy(deck = finalDeck, handCards = updatedHand, stacker = updatedStacker)
      updateHand(cardsToDraw - 1, updatedPlayer)
    } else {
      val updatedHand = List.concat(playerToUpdate.handCards, List(playerToUpdate.deck.head))
      val finalDeck = playerToUpdate.deck.drop(1)
      val updatedPlayer = playerToUpdate.copy(deck = finalDeck, handCards = updatedHand)
      updateHand(cardsToDraw - 1, updatedPlayer)
    }
  }

  private def shuffle(cardListToShuffle: List[Card]): List[Card] = {
    val random = new Random
    val shuffledList: List[Card] = random.shuffle(cardListToShuffle)
    shuffledList
  }

  override def removeHandCardAddToStacker(cardIndex: Int, player: Player): Player = {
    val updatedHand = player.handCards.zipWithIndex.collect { case (a, i) if i != cardIndex => a }
    val updatedStacker = List.concat(player.stacker, List(player.handCards(cardIndex)))
    player.copy(handCards = updatedHand, stacker = updatedStacker)
  }

  override def trashHandCard(cardIdx: Int, player: Player): Player = {
    val updatedHand = player.handCards.zipWithIndex.collect { case (a, i) if i != cardIdx => a }
    player.copy(handCards = updatedHand)
  }

  override def discard(indexesToDiscard: List[Int], player: Player): Player = {
    val updatedStacker = List.concat(player.stacker, player.handCards.zipWithIndex.collect { case (card, idx) if indexesToDiscard.contains(idx) => card })
    val updatedHand = player.handCards.zipWithIndex.collect { case (card, idx) if !indexesToDiscard.contains(idx) => card }
    player.copy(stacker = updatedStacker, handCards = updatedHand)
  }

  override def calculatePlayerMoneyForBuy(player: Player): Player = {
    val moneyValues: List[Int] = for (card <- player.handCards) yield card.moneyValue
    val finalMoneyValue = moneyValues.sum + player.money
    player.copy(money = finalMoneyValue)
  }

  override def checkForTreasure(player: Player): Boolean = {
    val booleanList: List[Boolean] = for (card <- player.handCards) yield card.cardType == Cardtype.MONEY
    booleanList.contains(true)
  }

  override def constructCellarTrashString(player: Player): String = {
    val handStringList: List[String] = for ((card, idx) <- player.handCards.zipWithIndex if card.cardType == Cardtype.MONEY) yield card.cardName + " (" + idx + ")"
    handStringList.mkString("\n")
  }

  override def removeCompleteHand(player: Player, index: Int): Player = {
    if (index < 0) {
      player
    } else {
      removeCompleteHand(player.removeHandCardAddToStacker(index, player), index - 1)
    }
  }

  override def moveAllCardsToDeckForScore(player: Player): Player = {
    val updatedDeck: List[Card] = List.concat(player.deck, player.stacker, player.handCards)
    this.copy(deck = updatedDeck, handCards = Nil, stacker = Nil)
  }

  override def calculateScore(player: Player): Int = {
    val scoreList: List[Int] = for (card <- player.deck) yield card.vpValue
    val deckSizeForGarden: Int = deck.size % 10
    val gardenAmount: Int = player.deck.count(x => x.cardName == "Gardens")
    scoreList.sum + (gardenAmount * deckSizeForGarden)
  }

  override def toJson: JsValue = Json.toJson(this)

  override def fromJson(jsValue: JsValue): IPlayer = {jsValue.validate[Player].asOpt.get}

  override def fromXml(node: NodeSeq): IPlayer = {
    val name = (node \ "name").text.trim
    val value = (node \ "value").text.toInt

    // TODO call Card.listFromXml
    val deck = (node \ "deck")
    val stacker = (node \ "stacker").text
    val hand = (node \ "hand").text

    val actions = (node \ "actions").text.toInt
    val buys = (node \ "buys").text.toInt
    val money = (node \ "money").text.toInt

    Player(name, value, Nil, Nil, Nil, actions, buys, money)
  }

  override def toXml: Elem = {
    // TODO card.toxml
    <Player>
      <name>{this.name}</name>
      <value>{this.value}</value>
      <deck>{for (card <- this.deck) yield card.toXml}</deck>
      <stacker>{for (card <- this.stacker) yield card.toXml}</stacker>
      <hand>{for (card <- this.handCards) yield card.toXml}</hand>
      <actions>{this.actions}</actions>
      <buys>{this.buys}</buys>
      <money>{this.money}</money>
    </Player>
  }
}

object Player {
  import play.api.libs.json._
  implicit val playerReads: Reads[Player] = Json.reads[Player]
  implicit val playerWrites: OWrites[Player] = Json.writes[Player]
}