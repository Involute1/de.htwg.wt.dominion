package de.htwg.sa.dominion.model.playercomponent

import de.htwg.sa.dominion.model.PlayerInterface
import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards, Cardtype}

import scala.util.Random

case class Player(name: String, value: Int, deck: List[Card], stacker: List[Card], handCards: List[Card],
                  actions: Int, buys: Int, money: Int, victoryPoint: Int) extends PlayerInterface {

  override def constructPlayerNameString(): String = {
    this.name
  }

  override def constructPlayerDeckString(): String = {
    val deckStringList: List[String] = for ((card, idx) <- this.deck.zipWithIndex) yield card.cardName + " (" + idx + ")"
    deckStringList.mkString("\n")
  }

  override def constructPlayerStackerString(): String = {
    val stackerStringList: List[String] = for ((card, idx) <- this.stacker.zipWithIndex) yield card.cardName + " (" + idx + ")"
    stackerStringList.mkString("\n")
  }

  override def constructPlayerHandString(): String = {
    val handStringList: List[String] = for ((card, idx) <- this.handCards.zipWithIndex) yield card.cardName + " (" + idx + ")"
    handStringList.mkString("\n")
  }

  override def updateActions(updatedActionValue: Int): Player = {
    this.copy(actions = updatedActionValue)
  }

  override def updateMoney(updateMoneyValue: Int): Player = {
    this.copy(money = updateMoneyValue)
  }

  override def updateBuys(updatedBuyValue: Int): Player = {
    this.copy(buys = updatedBuyValue)
  }

  override def checkForFirstSilver(): Player = {
    val hasSilver: Boolean = this.handCards.contains(Cards.silver)
    if (hasSilver) {
      val updatedMoney = this.money + 1
      this.copy(money = updatedMoney)
    } else this
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

  override def removeHandCardAddToStacker(cardIndex: Int): Player = {
    val updatedHand = this.handCards.zipWithIndex.collect { case (a, i) if i != cardIndex => a }
    val updatedStacker = List.concat(this.stacker, List(this.handCards(cardIndex)))
    this.copy(handCards = updatedHand, stacker = updatedStacker)
  }

  override def trashHandCard(cardIdx: Int): Player = {
    val updatedHand = this.handCards.zipWithIndex.collect { case (a, i) if i != cardIdx => a }
    this.copy(handCards = updatedHand)
  }

  override def discard(indexesToDiscard: List[Int]): Player = {
    val updatedStacker = List.concat(this.stacker, this.handCards.zipWithIndex.collect { case (card, idx) if indexesToDiscard.contains(idx) => card })
    val updatedHand = this.handCards.zipWithIndex.collect { case (card, idx) if !indexesToDiscard.contains(idx) => card }
    this.copy(stacker = updatedStacker, handCards = updatedHand)
  }

  override def calculatePlayerMoneyForBuy: Player = {
    val moneyValues: List[Int] = for (card <- this.handCards) yield card.moneyValue
    val finalMoneyValue = moneyValues.sum + this.money
    this.copy(money = finalMoneyValue)
  }

  override def checkForTreasure(): Boolean = {
    val booleanList: List[Boolean] = for (card <- this.handCards) yield card.cardType == Cardtype.MONEY
    booleanList.contains(true)
  }

  override def constructCellarTrashString(): String = {
    val handStringList: List[String] = for ((card, idx) <- this.handCards.zipWithIndex if card.cardType == Cardtype.MONEY) yield card.cardName + " (" + idx + ")"
    handStringList.mkString("\n")
  }

  override def removeCompleteHand(player: Player, index: Int): Player = {
    this
  }

}
