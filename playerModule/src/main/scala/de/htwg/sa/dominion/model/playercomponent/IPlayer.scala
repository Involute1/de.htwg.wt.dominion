package de.htwg.sa.dominion.model.playercomponent

import de.htwg.sa.dominion.model.playercomponent.playerBaseImpl.Player

trait IPlayer {

  def constructPlayerNameString(): String

  def constructPlayerDeckString(): String

  def constructPlayerStackerString(): String

  def constructPlayerHandString(): String

  def updateActions(updatedActionValue: Int): Player

  def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player

  def removeHandCardAddToStacker(cardIndex: Int): Player

  def updateMoney(updateMoneyValue: Int): Player

  def updateBuys(updatedBuyValue: Int): Player

  def checkForFirstSilver(): Player

  def calculatePlayerMoneyForBuy: Player

  def discard(indexesToDiscard: List[Int]): Player

  def checkForTreasure(): Boolean

  def trashHandCard(cardIdx: Int): Player

  def constructCellarTrashString(): String

  def removeCompleteHand(player: Player, index: Int): Player

  def moveAllCardsToDeckForScore(): Player

  def calculateScore: Int
}
