package de.htwg.sa.dominion.controller

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player

trait IPlayerController {

  def save(playerList: List[Player]): Unit

  def load(): List[Player]

  def constructPlayerNameString(playerToUpdate: Player): String

  def constructPlayerDeckString(playerToUpdate: Player): String

  def constructPlayerStackerString(playerToUpdate: Player): String

  def constructPlayerHandString(playerToUpdate: Player): String

  def updateActions(updatedActionValue: Int, playerToUpdate: Player): Player

  def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player

  def removeHandCardAddToStacker(cardIndex: Int, playerToUpdate: Player): Player

  def updateMoney(updateMoneyValue: Int, playerToUpdate: Player): Player

  def updateBuys(updatedBuyValue: Int, playerToUpdate: Player): Player

  def checkForFirstSilver(playerToUpdate: Player): Player

  def calculatePlayerMoneyForBuy(playerToUpdate: Player): Player

  def discard(indexesToDiscard: List[Int], playerToUpdate: Player): Player

  def checkForTreasure(playerToUpdate: Player): Boolean

  def trashHandCard(cardIdx: Int, playerToUpdate: Player): Player

  def constructCellarTrashString(player: Player): String

  def removeCompleteHand(player: Player, index: Int): Player

  def moveAllCardsToDeckForScore(player: Player): Player

  def calculateScore(player: Player): Int
}
