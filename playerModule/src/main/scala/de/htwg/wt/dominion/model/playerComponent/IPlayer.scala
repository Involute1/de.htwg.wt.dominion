package de.htwg.wt.dominion.model.playerComponent

import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player
import play.api.libs.json.JsValue

import scala.xml.Elem

trait IPlayer {

  def constructPlayerNameString(playerToUpdate: Player): String

  def constructPlayerDeckString(playerToUpdate: Player): String

  def constructPlayerStackerString(playerToUpdate: Player): String

  def constructPlayerHandString(player: Player): String

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

  def constructCellarTrashString(playerToUpdate: Player): String

  def removeCompleteHand(player: Player, index: Int): Player

  def moveAllCardsToDeckForScore(player: Player): Player

  def calculateScore(player: Player): Int

  def toJson: JsValue

  def fromJson(jsValue: JsValue): IPlayer

  def toXml: Elem

  def fromXml(node: scala.xml.NodeSeq): IPlayer
}
