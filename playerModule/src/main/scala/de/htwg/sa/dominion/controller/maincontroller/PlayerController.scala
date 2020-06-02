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

  override def constructPlayerNameString(playerToUpdate: Player): String = player.constructPlayerNameString(playerToUpdate)

  override def constructPlayerDeckString(playerToUpdate: Player): String = player.constructPlayerDeckString(playerToUpdate)

  override def constructPlayerStackerString(playerToUpdate: Player): String = player.constructPlayerStackerString(playerToUpdate)

  override def constructPlayerHandString(playerToUpdate: Player): String = player.constructPlayerHandString(playerToUpdate)

  override def updateActions(updatedActionValue: Int, playerToUpdate: Player): Player = player.updateActions(updatedActionValue, playerToUpdate)

  override def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player = player.updateHand(cardsToDraw, playerToUpdate)

  override def removeHandCardAddToStacker(cardIndex: Int, playerToUpdate: Player): Player = player.removeHandCardAddToStacker(cardIndex, playerToUpdate)

  override def updateMoney(updateMoneyValue: Int, playerToUpdate: Player): Player = player.updateMoney(updateMoneyValue, playerToUpdate)

  override def updateBuys(updatedBuyValue: Int, playerToUpdate: Player): Player = player.updateBuys(updatedBuyValue, playerToUpdate)

  override def checkForFirstSilver(playerToUpdate: Player): Player = player.checkForFirstSilver(playerToUpdate)

  override def calculatePlayerMoneyForBuy(playerToUpdate: Player): Player = player.calculatePlayerMoneyForBuy(playerToUpdate)

  override def discard(indexesToDiscard: List[Int], playerToUpdate: Player): Player = player.discard(indexesToDiscard, playerToUpdate)

  override def checkForTreasure(playerToUpdate: Player): Boolean = player.checkForTreasure(playerToUpdate)

  override def trashHandCard(cardIdx: Int, playerToUpdate: Player): Player = player.trashHandCard(cardIdx, playerToUpdate)

  override def constructCellarTrashString(playerToUpdate: Player): String = player.constructCellarTrashString(playerToUpdate)

  override def removeCompleteHand(playerToUpdate: Player, index: Int): Player = player.removeCompleteHand(playerToUpdate, index)

  override def moveAllCardsToDeckForScore(): Player = player.moveAllCardsToDeckForScore()

  override def calculateScore: Int = player.calculateScore
}
