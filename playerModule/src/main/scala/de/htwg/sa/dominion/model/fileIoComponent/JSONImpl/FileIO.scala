package de.htwg.sa.dominion.model.fileIoComponent.JSONImpl

import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.fileIoComponent.IPlayerFileIO
import de.htwg.sa.dominion.model.playerComponent.IPlayer

import scala.util.Try

class FileIO extends IPlayerFileIO {
  override def load(player: IPlayer, path: String): Try[IPlayer] = ???

  override def save(player: IPlayer, path: String): Try[Boolean] = ???
}
