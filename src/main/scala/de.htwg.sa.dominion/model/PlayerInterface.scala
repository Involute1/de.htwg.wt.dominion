package de.htwg.sa.dominion.model

import de.htwg.sa.dominion.model.playercomponent.Player

trait PlayerInterface {

  def constructPlayerNameString(): String

  def constructPlayerDeckString(): String

  def constructPlayerStackerString(): String

  def constructPlayerHandString(): String

  def updateActions(updatedActionValue: Int): Player

  def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player

  def removeHandCard(cardIndex: Int): Player

  def updateMoney(updateMoneyValue: Int): Player

  def updateBuys(updatedBuyValue: Int): Player

  def checkForFirstSilver(): Player

  def calculatePlayerMoneyForBuy: Player
}
