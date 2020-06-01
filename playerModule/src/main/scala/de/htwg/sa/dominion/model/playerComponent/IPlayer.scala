package de.htwg.sa.dominion.model.playerComponent

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import play.api.libs.json.JsValue

import scala.xml.Elem

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

  def toJson: JsValue

  def fromJson(jsValue: JsValue): IPlayer

  def toXml: Elem

  def fromXml(node: scala.xml.NodeSeq): IPlayer

  def test(): String
}
