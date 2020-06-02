package de.htwg.sa.dominion.model.playerComponent

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import play.api.libs.json.JsValue

import scala.xml.Elem

trait IPlayer {

  def constructPlayerNameString(player: Player): String

  def constructPlayerDeckString(player: Player): String

  def constructPlayerStackerString(player: Player): String

  def constructPlayerHandString(player: Player): String

  def updateActions(updatedActionValue: Int, player: Player): Player

  def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player

  def removeHandCardAddToStacker(cardIndex: Int, player: Player): Player

  def updateMoney(updateMoneyValue: Int, player: Player): Player

  def updateBuys(updatedBuyValue: Int, player: Player): Player

  def checkForFirstSilver(player: Player): Player

  def calculatePlayerMoneyForBuy(player: Player): Player

  def discard(indexesToDiscard: List[Int], player: Player): Player

  def checkForTreasure(player: Player): Boolean

  def trashHandCard(cardIdx: Int, player: Player): Player

  def constructCellarTrashString(player: Player): String

  def removeCompleteHand(player: Player, index: Int): Player

  def moveAllCardsToDeckForScore(): Player

  def calculateScore: Int

  def toJson: JsValue

  def fromJson(jsValue: JsValue): IPlayer

  def toXml: Elem

  def fromXml(node: scala.xml.NodeSeq): IPlayer

  def test(string: String): String
}
