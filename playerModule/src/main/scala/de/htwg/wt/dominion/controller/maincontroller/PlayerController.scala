package de.htwg.wt.dominion.controller.maincontroller

import com.google.inject.Inject
import de.htwg.wt.dominion.controller.IPlayerController
import de.htwg.wt.dominion.model.playerComponent.IPlayer
import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.wt.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.wt.dominion.model.playerFileIoComponent.IPlayerFileIO

import scala.util.{Failure, Success}

class PlayerController @Inject()(var playerInterface: IPlayer, fileIoInterface: IPlayerFileIO, playerDbInterface: IPlayerDatabase) extends IPlayerController {

  playerDbInterface.create

  override def save(playerList: List[Player]): Unit = {
    //fileIoInterface.save(playerInterface, "playerModule")
    playerDbInterface.update(playerList)
  }

  override def load(): List[Player] = {
    /*playerInterface = fileIoInterface.load(playerInterface, "playerModule") match {
      case Failure(_) => return
      case Success(value) => value
    }*/
    playerDbInterface.read()
  }

  override def constructPlayerNameString(playerToUpdate: Player): String = playerInterface.constructPlayerNameString(playerToUpdate)

  override def constructPlayerDeckString(playerToUpdate: Player): String = playerInterface.constructPlayerDeckString(playerToUpdate)

  override def constructPlayerStackerString(playerToUpdate: Player): String = playerInterface.constructPlayerStackerString(playerToUpdate)

  override def constructPlayerHandString(playerToUpdate: Player): String = playerInterface.constructPlayerHandString(playerToUpdate)

  override def updateActions(updatedActionValue: Int, playerToUpdate: Player): Player = playerInterface.updateActions(updatedActionValue, playerToUpdate)

  override def updateHand(cardsToDraw: Int, playerToUpdate: Player): Player = playerInterface.updateHand(cardsToDraw, playerToUpdate)

  override def removeHandCardAddToStacker(cardIndex: Int, playerToUpdate: Player): Player = playerInterface.removeHandCardAddToStacker(cardIndex, playerToUpdate)

  override def updateMoney(updateMoneyValue: Int, playerToUpdate: Player): Player = playerInterface.updateMoney(updateMoneyValue, playerToUpdate)

  override def updateBuys(updatedBuyValue: Int, playerToUpdate: Player): Player = playerInterface.updateBuys(updatedBuyValue, playerToUpdate)

  override def checkForFirstSilver(playerToUpdate: Player): Player = playerInterface.checkForFirstSilver(playerToUpdate)

  override def calculatePlayerMoneyForBuy(playerToUpdate: Player): Player = playerInterface.calculatePlayerMoneyForBuy(playerToUpdate)

  override def discard(indexesToDiscard: List[Int], playerToUpdate: Player): Player = playerInterface.discard(indexesToDiscard, playerToUpdate)

  override def checkForTreasure(playerToUpdate: Player): Boolean = playerInterface.checkForTreasure(playerToUpdate)

  override def trashHandCard(cardIdx: Int, playerToUpdate: Player): Player = playerInterface.trashHandCard(cardIdx, playerToUpdate)

  override def constructCellarTrashString(playerToUpdate: Player): String = playerInterface.constructCellarTrashString(playerToUpdate)

  override def removeCompleteHand(playerToUpdate: Player, index: Int): Player = playerInterface.removeCompleteHand(playerToUpdate, index)

  override def moveAllCardsToDeckForScore(playerToUpdate: Player): Player = playerInterface.moveAllCardsToDeckForScore(playerToUpdate)

  override def calculateScore(playerToUpdate: Player): Int = playerInterface.calculateScore(playerToUpdate)
}
