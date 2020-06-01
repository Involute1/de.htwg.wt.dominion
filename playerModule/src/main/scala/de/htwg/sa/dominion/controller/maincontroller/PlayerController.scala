package de.htwg.sa.dominion.controller.maincontroller

import com.google.inject.Inject
import de.htwg.sa.dominion.controller.IPlayerController
import de.htwg.sa.dominion.model.playerFileIoComponent.IPlayerFileIO
import de.htwg.sa.dominion.model.playerComponent.IPlayer
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player

import scala.util.{Failure, Success}

class PlayerController @Inject()(var player: IPlayer, fileIo: IPlayerFileIO) extends IPlayerController {

  override def save(): Unit = {
    fileIo.save(player, "playerModule")
  }

  override def load(): Unit = {
    player = fileIo.load(player, "playerModule") match {
      case Failure(_) => return
      case Success(value) => value
    }
  }

  override def constructPlayerNameString(): String = player.constructPlayerNameString()

  override def constructPlayerDeckString(): String = player.constructPlayerDeckString()

  override def constructPlayerStackerString(): String = player.constructPlayerStackerString()

  override def constructPlayerHandString(playerToUpdate: Player): String = player.constructPlayerHandString(playerToUpdate)

  override def updateActions(updatedActionValue: Int): Player = player.updateActions(updatedActionValue)

  override def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player = player.updateHand(cardsToDraw, playerToUpdate)

  override def removeHandCardAddToStacker(cardIndex: Int): Player = player.removeHandCardAddToStacker(cardIndex)

  override def updateMoney(updateMoneyValue: Int): Player = player.updateMoney(updateMoneyValue)

  override def updateBuys(updatedBuyValue: Int): Player = player.updateBuys(updatedBuyValue)

  override def checkForFirstSilver(): Player = player.checkForFirstSilver()

  override def calculatePlayerMoneyForBuy: Player = player.calculatePlayerMoneyForBuy

  override def discard(indexesToDiscard: List[Int]): Player = player.discard(indexesToDiscard)

  override def checkForTreasure(): Boolean = player.checkForTreasure()

  override def trashHandCard(cardIdx: Int): Player = player.trashHandCard(cardIdx)

  override def constructCellarTrashString(): String = player.constructCellarTrashString()

  override def removeCompleteHand(playerToUpdate: Player, index: Int): Player = player.removeCompleteHand(playerToUpdate, index)

  override def moveAllCardsToDeckForScore(): Player = player.moveAllCardsToDeckForScore()

  override def calculateScore: Int = player.calculateScore

  override def test(): String = player.test()
}
